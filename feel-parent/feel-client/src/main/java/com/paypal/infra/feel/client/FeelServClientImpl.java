package com.paypal.infra.feel.client;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.ebay.kernel.bean.configuration.BeanConfigCategoryInfo;
import com.ebay.kernel.bean.configuration.ConfigCategoryCreateException;
import com.ebay.kernel.calwrapper.CalTransaction;
import com.ebay.kernel.calwrapper.CalTransactionFactory;
import com.ebay.kernel.calwrapper.CalTransactionHelper;
import com.ebay.kernel.executor.ExecutorPropertyBean;
import com.ebay.kernel.executor.TaskExecutor;
import com.ebay.kernel.logger.LogLevel;
import com.paypal.infra.feel.IFeelService;
import com.paypal.infra.feel.config.FeelPropertiesProvider;
import com.paypal.infra.feel.domain.IFeelLogEntry;
import com.paypal.infra.feel.util.CalStatusCode;
import com.paypal.infra.lar.LarClient;
import com.paypal.infra.lar.LarClientFactory;
import com.paypal.infra.util.log.LoggerWrapper;
import com.paypal.infra.feel.util.LarMsgHelper;

/**
 * Implementation that writes entry to feelserv server. </p> Uses NetStringOutputStream
 * to format messages and write them to feelserv.
 * 
 * @see com.paypal.infra.feel.domain.FeelErrorLogEntry
 * @see com.paypal.infra.feel.domain.FeelDBLogEntry
 */
public class FeelServClientImpl implements FeelServClient {
	
	private static final LoggerWrapper LOGGER = new LoggerWrapper(FeelServClientImpl.class);
	private static final int DEFAULT_THREAD_POOL_SIZE = 2;

	private volatile TaskExecutor mExecutor = null;
	
	private final int threadPoolSize;
	//TODO
	private final LarClient larClient;
		
	/**
	 * Construct a FeelServClientImpl 
	 * @param provider - {@link IConfigurationProvider} from which to get feel configuration properties
	 * @throws Exception
	 */
	public FeelServClientImpl (FeelPropertiesProvider feelConfig) throws Exception {
		
		if(feelConfig.getThreadPoolSize() != null) {
			threadPoolSize = Integer.parseInt(feelConfig.getThreadPoolSize());
		}
		else {
			threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
		}
		getExecutor();
		
		//TODO
		// Create lar client 
		Properties properties = new Properties();
		//properties.put(LarPropertyDef.PORT.getKey(), feelConfigBeanProvider.getLarProxyPort());
		larClient = LarClientFactory.createLarClient(feelConfig);
	}

	/**
	 *  see interface doc {@link IFeelService} for usage details
	 */
	public void writeWithWait(IFeelLogEntry entry) throws IOException {
		CalTransaction calTrans = CalTransactionFactory.create("FEELSERV_CLIENT");
		calTrans.setName("WRITE");
		calTrans.setStatus(CalStatusCode.TRANS_OK.getCode());
		String correlationId = getCorrelationId();
		calTrans.setCorrelationId(correlationId);
        try{
        	
        	System.out.println("SWALLOWING FEEL MESSAGE");
        	LarMsgHelper.writeAsLarRequest(entry, larClient);
        	calTrans.setStatus(CalStatusCode.TRANS_OK.getCode());
        }
        catch (IOException ioe) {
        	calTrans.setStatus(ioe);
			LOGGER.getLogger().log(LogLevel.ERROR, "Exception writing to LAR broker:", ioe);
			throw ioe;
        } 
        catch (Throwable th) {
			calTrans.setStatus(th);
			LOGGER.getLogger().log(LogLevel.ERROR, "Exception writing to LAR broker:", th);
			throw new IOException("Exception writing to LAR broker", th);
		} 
        finally {
        	// Complete the CAL transaction
			calTrans.completed();
        }
	}
	
	protected String getCorrelationId() {
		CalTransaction topTransaction = CalTransactionHelper.getTopTransaction();
		if (topTransaction == null) {
			return null;
		}
		return topTransaction.getCorrelationId();
	}

	//@Override
	public void write(IFeelLogEntry entry) throws IOException {
		FeelServWriteTask task = new FeelServWriteTask(this, entry);
		getExecutor().add(task, null);
	}

	private TaskExecutor getExecutor() throws IOException {
		TaskExecutor localExecutor = mExecutor;
		if (localExecutor == null) {
			synchronized (this) {
				if (mExecutor == null) {
					mExecutor = initExecutor();
				}
				localExecutor = mExecutor;
			}
		}
		return localExecutor;
	}

	//TODO 
	private TaskExecutor initExecutor() throws IOException {
		BeanConfigCategoryInfo info = null;
		if( (info = BeanConfigCategoryInfo.getBeanConfigCategoryInfo("feelservJavaClient")) == null) {
			try{
				info = BeanConfigCategoryInfo.createBeanConfigCategoryInfo(
					"feelservJavaClient", "fsjcAlias", "com.paypal.infra"
					, false /* no persistence */
					, false /* no ops managable */
					, null /* no persistence url */
					, "feel java client configuration");
				}catch(ConfigCategoryCreateException  e) {
					throw new IOException("Fail to create a BeanConfigCategoryInfo object with category id: feelservJavaClient");
				}
		}
		
		ExecutorPropertyBean config = ExecutorPropertyBean
				.defaultFixedExecutorProperties("feelservJavaClientExecutor",
						info);
		boolean succeed = config.setCoreSize(threadPoolSize);
		if (!succeed) {
			throw new IOException(
					"Feelserv Java client Thread Pool initialization failure with size of "
							+ threadPoolSize
							+ ". Please change to smaller thread pool size and try again");
		}
		return TaskExecutor.newExecutor(config);
	}

	public void stop(long timeoutInSecs) {
		TaskExecutor localExecutor = mExecutor;
		if (localExecutor == null) {
			return;
		}
		synchronized (this) {
			if (mExecutor == null) {
				return;
			}
			mExecutor = null;
		}
		
		localExecutor.shutdown();

		try {
			localExecutor.awaitTermination(timeoutInSecs, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// ignore this exception if shutdown fails
		}
	}
}