package com.paypal.infra.lar;

import com.paypal.infra.feel.config.FeelPropertiesProvider;

/**
 * Factory class for instantiating the LAR client
 * <p>
 * For Development guide, refer to: https://dev.paypal.com/wiki/JavaInfrastructure/JavaLARDevelopmentGuide
 * </p>
 */
public final class LarClientFactory {
	/**
	 * Factory method to create a LAR client implementation Object
	 * 
	 * @param config - LAR configuration
	 * @return LarClient
	 * @exception Exception
	 */
	public static LarClient createLarClient( FeelPropertiesProvider config ) throws Exception{
		return new LarClientImpl(config );
	}
}
