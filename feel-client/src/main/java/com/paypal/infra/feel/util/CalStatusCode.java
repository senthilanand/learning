package com.paypal.infra.feel.util;
/**
 * Values for CalTransaction.setStatus(CalStatusCode)
 * 
 * @author <a href="mailto:cellis@paypal.com">Chad Ellis</a>
 * @version $Revision: $
 */
public enum CalStatusCode {
	TRANS_OK("0"), TRANS_FATAL("1"), TRANS_ERROR("2"), TRANS_WARNING("3");

	private final String statusCode;

	private CalStatusCode(String code) {
		this.statusCode = code;
	}

	public String getCode() {
		return this.statusCode;
	}
}
