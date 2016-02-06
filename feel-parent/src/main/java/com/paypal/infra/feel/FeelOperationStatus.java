package com.paypal.infra.feel;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of error codes that can be returned from Feel on response to
 * api calls.
 *
 * Values 100 + are client side only error codes
 */
public enum FeelOperationStatus {

	// Client side only codes
	/** 100 - Action failed and io error occurred */
	FEEL_CLIENT_CONFIG_ERROR(100, "Configuration Error Occurred in the Java Feel Client."),

	/** 101 - Action failed and unknown error occurred */
	FEEL_CLIENT_UNKNOWN_ERROR(101, "Unknown Error"),

    /** 102 - Action failed and there was no body message returned */
    FEEL_ERROR_NO_BODY_IN_RESPONSE(102,
            "Action failed and there was no body message returned"),

	;
	private final int code;
	private final String errorText;

	private static final Map<Integer, FeelOperationStatus> lookup = new HashMap<Integer, FeelOperationStatus>();

	static {
		for (FeelOperationStatus s : EnumSet
				.allOf(FeelOperationStatus.class))
			lookup.put(s.getCode(), s);
	}

	/**
	 * Constructor
	 * 
	 * @param code
	 * @param errorText
	 */
	FeelOperationStatus(int code, String errorText) {
		this.code = code;
		this.errorText = errorText;
	}

	public int getCode() {
		return this.code;
	}

	public String getErrorText() {
		return this.errorText;
	}

	public static FeelOperationStatus get(int code) {
		return lookup.get(code);
	}
}
