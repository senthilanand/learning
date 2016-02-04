package com.paypal.infra.feel;

/**
 * Exception to represent a Client configuration error.
 */
public final class FeelClientConfigException extends FeelException {
	private static final long serialVersionUID = -2773624072814891564L;

	public FeelClientConfigException() {
		super(FeelOperationStatus.FEEL_CLIENT_CONFIG_ERROR);
	}

	public FeelClientConfigException(String message) {
		super(message, FeelOperationStatus.FEEL_CLIENT_CONFIG_ERROR);
	}

	public FeelClientConfigException(String message, Throwable cause) {
		super(message, FeelOperationStatus.FEEL_CLIENT_CONFIG_ERROR, cause);
	}

	public FeelClientConfigException(Throwable cause) {
		super(FeelOperationStatus.FEEL_CLIENT_CONFIG_ERROR, cause);
	}
}