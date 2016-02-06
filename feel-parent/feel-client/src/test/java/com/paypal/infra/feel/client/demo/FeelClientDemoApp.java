package com.paypal.infra.feel.client.demo;

import java.util.Properties;
import java.util.Random;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.paypal.infra.feel.FeelClientTestUtil;
import com.paypal.infra.feel.FeelServiceFactory;
import com.paypal.infra.feel.IFeelService;
import com.paypal.infra.feel.config.FeelPropertiesProvider;
import com.paypal.infra.util.log.LoggerWrapper;

public class FeelClientDemoApp {

	public static LoggerWrapper log = new LoggerWrapper(FeelClientDemoApp.class);

	public static final String THREADPOOL_SIZE = "2";
	public static final Integer PORT = ((new Random()).nextInt(3000)) + 27300;
	public static final String LARPROXY_PORT = String.valueOf(PORT);

	IFeelService feelServiceImpl = null;
	

	public static void main(String[] args) throws Throwable {
		
		// start local Java LAR proxy
		LocalJavaProxy qljp = new LocalJavaProxy(PORT, "stage2ms3105.ccg21.dev.paypalcorp.com", 
				10983, 3);
		qljp.setDebug(1, System.out);
		qljp.start();
		Thread.sleep(3000);
		
		enableConsoleLogging(log.getLogger());
		
		try{
			//asynch write
			feelwriteNoWait();
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}

	public static void enableConsoleLogging(Logger logger) {
		Logger.initLogProperties("console-logging.properties");
		Logger.getInstance("com.paypal.infra.feel").setLevel(LogLevel.ALL);
		Logger.getInstance("com.paypal.infra.protectedpkg").setLevel(LogLevel.ERROR);
	}

	private static void logMsg(String msg) {
		if(log.getLogger().isDebugEnabled()) {
			log.getLogger().debug(msg);
		}
	}
	
	private void setUp() throws Exception {

		Properties props = new Properties();
		FeelPropertiesProvider feelConfigProvider = new FeelPropertiesProvider(props);
		
		/*
		props.setProperty(FeelPropertyDef.THREADPOOLSIZE.getKey(), THREADPOOL_SIZE);
		props.setProperty(FeelPropertyDef.LARPROXYPORT.getKey(), LARPROXY_PORT);
		
		FeelPropertyProvider feelPropProvider = new FeelPropertyProvider("feel", props);
		
		FallbackConfigurationProvider<FeelPropertyDef> feelConfigProviderWithDefault = new FallbackConfigurationProvider<FeelPropertyDef>(feelPropProvider, FeelDefaultPropertyProvider.getInstance());
		feelConfigProvider = new FeelConfigBeanProvider(feelConfigProviderWithDefault);*/
		
		//initialize feel service instance
		FeelServiceFactory.initFeelService(feelConfigProvider);
	    
	}

	public static void  feelwriteNoWait() throws Exception {
		logMsg("New Async Write log starts");

		FeelClientDemoApp app = new FeelClientDemoApp();
		try {
			logMsg("Setup starts...");
			app.setUp();
		    app.feelServiceImpl =  FeelServiceFactory.getFeelService();
		    
			logMsg("Write starts ...");
			app.feelServiceImpl.write(FeelClientTestUtil.getErrorLogSample());
			app.feelServiceImpl.write(FeelClientTestUtil.getDBLogSample());
			app.feelServiceImpl.write(FeelClientTestUtil.getDBLogSample1());
			logMsg("Write succeed ...");
			logMsg(" client impl wait to shutdown ...");
			app.feelServiceImpl.stop(3000);
			logMsg("client impl shutdown completely ...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(app.feelServiceImpl != null) {
				app.feelServiceImpl.stop(3000);
				app.feelServiceImpl = null;
			}
		}
	}

}