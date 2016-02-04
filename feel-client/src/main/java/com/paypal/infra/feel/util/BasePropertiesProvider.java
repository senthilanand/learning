package com.paypal.infra.feel.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.paypal.infra.feel.FeelClientConfigException;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.paypal.infra.util.exception.ExceptionUtils;

public class BasePropertiesProvider {
    private static final Logger logger = Logger.getInstance(BasePropertiesProvider.class);

	protected final Properties config;
	
	protected BasePropertiesProvider(URL url) {
		ExceptionUtils.throwIfNull(url, "URL");
		this.config = new Properties();
		try {
			this.config.load(url.openStream());
		} catch (IOException e) {
			logger.log(LogLevel.ERROR, 
					"Unable to read the config properties file", e);
			throw new FeelClientConfigException(
					"Unable to read the config properties file", e);
		}
	}
	
	/**
	 *  Constructs the default properties provider.
	 *  
	 *  @param config properties
	 */
	protected BasePropertiesProvider(Properties config) {
		this.config = config;
	}
	
	protected final Integer getIntProperty(final String key, int defaultValue) {
		String sval = config.getProperty(key);
		int ival = defaultValue;
		if (sval != null) {
			try {
				ival = Integer.parseInt(sval.trim());
			} 
			catch(Exception e) {
				throw new FeelClientConfigException("Integer property not valid - Value = " + sval, e);
			}
		}
		return ival;
	}


	protected final Long getLongProperty(final String key, long defaultValue) {
		String sval = config.getProperty(key);
		long ival = defaultValue;
		if (sval != null) {
			try {
				ival = Long.parseLong(sval.trim());
			}
			catch(Exception e) {
				throw new FeelClientConfigException("Long property not valid - Value = " + sval, e);
			}
		}
		return ival;
	}
	
	protected final Boolean getBooleanProperty(final String key, boolean defaultValue) {
		String sval = config.getProperty(key);
		if (sval == null) {
			return defaultValue;
		}
		try {
			return Boolean.valueOf(sval);
		}
		catch(Exception e) {
			throw new FeelClientConfigException("Boolean property not valid - Value = " + sval, e);
		}
	}
	
	protected final String getStringProperty(String key, String defaultValue) {
		String sval = config.getProperty(key);
		return (sval != null ? sval : defaultValue);
	}
}
