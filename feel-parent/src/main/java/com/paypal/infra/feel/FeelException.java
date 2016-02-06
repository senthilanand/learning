package com.paypal.infra.feel;

import org.apache.commons.lang.StringUtils;

import com.paypal.infra.util.exception.ThrowableWrapper;

/**
 * The base exception type thrown by the FeelClient. Clients can access a
 * {@link ThrowableWrapper} that will conveniently allow {@link FeelException}
 * s to be wrapped around other {@link Throwable}s.
 */
public class FeelException extends RuntimeException {

	private static final ThrowableWrapper<FeelException> THROWABLE_WRAPPER = new ThrowableWrapper<FeelException>(FeelException.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -8438146622156870928L;

	/**
	 * {@link FeelOperationStatus}, for use by error mapper.
	 */
	private final FeelOperationStatus feelOperationStatus;

	/**
	 * Construct a FeelException.
	 */
	public FeelException() {
		super();
		feelOperationStatus = FeelOperationStatus.FEEL_CLIENT_UNKNOWN_ERROR;
	}

	/**
	 * Construct an FeelException given a message.
	 * 
	 * @param message
	 *            the error text
	 */
	public FeelException(String message) {
		// Guard against null messages, since we have seen those occur
		super(StringUtils.trimToEmpty(message));
		feelOperationStatus = FeelOperationStatus.FEEL_CLIENT_UNKNOWN_ERROR;
	}

	/**
	 * Construct an FeelException given a message, and return code
	 * 
	 * @param message
	 *            the error text
	 * @param feelOperationStatus
	 *            the return status
	 */
	public FeelException(String message,
			FeelOperationStatus feelOperationStatus) {
		// Guard against null messages, since we have seen those occur
		super(StringUtils.trimToEmpty(message));
		this.feelOperationStatus = feelOperationStatus;
	}

	/**
	 * Construct an FeelException given a message, and return code
	 * 
	 * @param feelOperationStatus the return status
	 */
	public FeelException(FeelOperationStatus feelOperationStatus) {
		this.feelOperationStatus = feelOperationStatus;
	}

	/**
	 * Construct an FeelException given a message and a cause.
	 * 
	 * @param message
	 *            the error text
	 * @param cause
	 *            the {@link Throwable} cause
	 */
	public FeelException(String message, Throwable cause) {
		// Guard against null messages, since we have seen those occur
		super(StringUtils.trimToEmpty(message));
		initCause(cause);
		feelOperationStatus = FeelOperationStatus.FEEL_CLIENT_UNKNOWN_ERROR;
	}

	/**
	 * Construct an FeelException given a message, return code, and cause.
	 * 
	 * @param message
	 *            the error text
	 * @param feelOperationStatus
	 *            the operation status
	 * @param cause
	 *            the {@link Throwable} cause
	 */
	public FeelException(String message,
			FeelOperationStatus feelOperationStatus, Throwable cause) {
		// Guard against null messages, since we have seen those occur
		super(StringUtils.trimToEmpty(message));
		this.feelOperationStatus = feelOperationStatus;
		initCause(cause);
	}

	/**
	 * Construct an FeelException given a cause.
	 * 
	 * @param cause
	 *            the {@link Throwable} cause
	 */
	public FeelException(Throwable cause) {
		super(cause.getMessage());
		initCause(cause);
		feelOperationStatus = FeelOperationStatus.FEEL_CLIENT_UNKNOWN_ERROR;
	}

	/**
	 * Construct an FeelException given a return code and a cause.
	 * 
	 * @param feelOperationStatus
	 *            the return code
	 * @param cause
	 *            the {@link Throwable} cause
	 */
	public FeelException(FeelOperationStatus feelOperationStatus,
			Throwable cause) {
		super(cause.getMessage());
		this.feelOperationStatus = feelOperationStatus;
		initCause(cause);
	}

	/**
	 * Return a {@link ThrowableWrapper} that can be used to wrap Throwables of
	 * arbitrary type with FeelExceptions.
	 * 
	 * @return the ThrowableWrapper for this class
	 */
	public static ThrowableWrapper<FeelException> getWrapper() {
		return THROWABLE_WRAPPER;
	}

	/**
	 * @return the feelOperationStatus
	 */
	public FeelOperationStatus getOperationStatus() {
		return feelOperationStatus;
	}

	/**
	 * @return the feelOperationStatus as an int
	 */
	public int getOperationStatusInt() {
		return feelOperationStatus.ordinal();
	}

	public String toString() {
		return "FeelException: Operation Status = " + getOperationStatusInt()
				+ ", '" + getOperationStatus().getErrorText() + "'";
	}
}
