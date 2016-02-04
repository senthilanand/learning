package com.paypal.infra.feel;

import java.io.IOException;

import com.ebay.kernel.initialization.InitializationException;
import com.paypal.infra.feel.config.FeelPropertiesProvider;
import com.paypal.infra.feel.domain.IFeelLogEntry;

public class FeelServiceFactory {
	private static final int EXECUTOR_SHUTDOWN_DEFAULT_TIMEOUT_IN_SECONDS = 5;
	public static final NullFeelService NULL_SERVICE = new NullFeelService();
	
	private static volatile IFeelService s_instance = FeelServiceFactory.NULL_SERVICE;

	public static boolean isLoaded() {
		return (s_instance != FeelServiceFactory.NULL_SERVICE);
	}

	/**
	 * Get current feelservice 
	 * By default the Null Feel Service will be used.
	 * 
	 * @return an instance of feelservice
	 */
	public static IFeelService getFeelService() {
		return s_instance;
	}

	/**
	 * Initialize feelservice with FeelConfigurationProvider as input
	 * 
	 * @param feelConfig
	 * @return void
	 * @throws Exception
	 */
	public static synchronized void initFeelService(FeelPropertiesProvider feelConfig) throws Exception {
		if (s_instance != FeelServiceFactory.NULL_SERVICE) {
			throw new InitializationException("The FeelServiceFactory is already initialized.");
		}
		IFeelService feelService = new JavaFeelService(feelConfig);
		setFeelService(feelService);
	}

	private static void setFeelService(final IFeelService feelService) {
		if (s_instance != FeelServiceFactory.NULL_SERVICE) {
			try {
				s_instance.stop(EXECUTOR_SHUTDOWN_DEFAULT_TIMEOUT_IN_SECONDS);
			} catch (Exception e) {
				// ignore
			}
		}
		s_instance = feelService;
	}

	/**
	 * This is intended to return it to a previous state.
	 */
	public static synchronized void reset() {
		if (s_instance != FeelServiceFactory.NULL_SERVICE) {
			try {
				s_instance.stop(EXECUTOR_SHUTDOWN_DEFAULT_TIMEOUT_IN_SECONDS);
			} catch (Exception e) {
				// ignore it
			}
			s_instance = FeelServiceFactory.NULL_SERVICE;
		}
	}

	public static class NullFeelService implements IFeelService {
		//@Override
		public boolean start() {
			return true;
		}

		//@Override
		public void stop() {

		}

		//@Override
		public void stop(long timeoutInSecs) {

		}

		//@Override
		public void write(IFeelLogEntry entry) throws IOException {

		}

		//@Override
		public void writeWithWait(IFeelLogEntry entry) throws IOException {
		}
	}
}