package com.paypal.infra.lar;

import com.paypal.vo.ValueObject;

/**
 * 
 * Interface for LAR Client
 *
 */
public interface LarClient {
	/**
	 * Implementation class has to provide implementation for this method specific to LAR Proxy
	 * 
	 * @param eventName - Event name/Operation name
	 * @param asfRequestVO - ASF Request VO
	 */
	public void sendLARRequest(String eventName, ValueObject valueObject);
}