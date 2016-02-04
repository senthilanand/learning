package com.paypal.infra.feel.config;

import com.paypal.infra.feel.util.BasePropertiesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Properties;

public final class FeelPropertiesProvider extends BasePropertiesProvider {
	
	final Logger LOGGER = LoggerFactory.getLogger(FeelPropertiesProvider.class);

	// Property holders.
	private String instance;
	private String larProxyPort;
	private String threadPoolSize;

	/**
	 *  Constructs the default properties provider.
	 *  
	 *  @param props Feel properties.
	 */
	public FeelPropertiesProvider(Properties props) {
		super(props);
		validateAndFillAll();
	}
	
	/**
	 *  Constructs the default properties provider.
	 *  
	 *  @param url location of the Feel properties file.
	 */
	public FeelPropertiesProvider(URL url) {
		super(url);
		validateAndFillAll();
	}

	/**
	 *  Validate and populate the feel proprties
	 *  
	 */
	private void validateAndFillAll() {
        this.instance = getStringProperty(FeelPropertiesKeyDefinition.instance, FeelPropertyDefaultValue.instance);
		this.larProxyPort = getStringProperty(FeelPropertiesKeyDefinition.larProxyPort, FeelPropertyDefaultValue.larProxyPort);
		this.threadPoolSize = getStringProperty(FeelPropertiesKeyDefinition.threadPoolSize, FeelPropertyDefaultValue.threadPoolSize);
	}

    @Override
    public String toString() {
        return "FeelPropertiesProvider{" +
                "instance=" + instance +
                ", larProxyPort=" + larProxyPort +
                ", threadpool_size=" + threadPoolSize +
                '}';
    }

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getLarProxyPort() {
		return larProxyPort;
	}

	public void setLarProxyPort(String larProxyPort) {
		this.larProxyPort = larProxyPort;
	}

	public String getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(String threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

}
