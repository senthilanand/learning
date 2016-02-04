package com.paypal.infra.feel;

import java.io.IOException;

import com.ebay.kernel.logger.LogLevel;
import com.paypal.infra.feel.client.FeelServClientImpl;
import com.paypal.infra.feel.config.FeelPropertiesProvider;
import com.paypal.infra.feel.domain.IFeelLogEntry;
import com.paypal.infra.util.log.LoggerWrapper;

/**
 * This is the java implementation of feel log service. Please see doc for IFeelService for usage info.
 * 
 * The internal of this implementation is to delegate most of work to FeelServClient class. This implementation could be instantiated through 
 * the method FeelServiceFactory.initFeelService(IConfigurationProvider<FeelPropertyDef> feelConfig).
 * 
 * @author Java infra team. phan
 *
 */
public class JavaFeelService implements IFeelService {

	private final FeelServClientImpl feelClientImpl;
	private static final LoggerWrapper logger = new LoggerWrapper(JavaFeelService.class);
	
	public JavaFeelService(FeelPropertiesProvider feelConfig) throws Exception{
		feelClientImpl = new FeelServClientImpl(feelConfig);
	}

	//@Override
	public boolean start() {
		return true;
	}

	/**
	 * Stop the client sending messages and shutdown in seconds of
	 * timeoutInSecs.
	 * 
	 * @param timeoutInSecs
	 */
	//@Override
	public void stop(long timeoutInSecs) {		
		if (timeoutInSecs < 0) {
			throw new IllegalArgumentException("Timeout must be a positive number.");
		}
		if (logger.getLogger().isDebugEnabled()) {
			logger.getLogger().log(LogLevel.DEBUG, "Java Feel Service stops in " + timeoutInSecs + " seconds.");
		}
		feelClientImpl.stop(timeoutInSecs);
	}
	
	/**
	 * stop the service with a default waiting time period (5 minutes)
	 */
	//@Override
	public void stop() {
		stop(300L);
	}

	//@Override
	public void write(IFeelLogEntry entry) throws IOException {
		if (entry == null) {
			logger.getLogger().log(LogLevel.DEBUG, "Ignore null log entry.");
			return;
		}
			feelClientImpl.write(entry);
	}

	//@Override
	public void writeWithWait(IFeelLogEntry entry) throws IOException {
		if (entry == null) {
			logger.getLogger().log(LogLevel.DEBUG, "Ignore null log entry.");
			return;
		}
			feelClientImpl.writeWithWait(entry);
	}
}