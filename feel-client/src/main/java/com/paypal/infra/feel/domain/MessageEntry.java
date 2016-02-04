package com.paypal.infra.feel.domain;

import com.paypal.infra.feel.domain.FeelServConstants.ErrorVerbosity;
import com.paypal.infra.feel.domain.FeelServConstants.FeelError;


public class MessageEntry 
{
	public enum MessageType
    {
    	CInvalid (-1, "CInvalid"),
    	CLocaleMsg (0, "CLocaleMsg"),
    	CRawMsg (1, "CRawMsg");    	
    	
    	private final int value;
    	private final String name;
		private MessageType(int value, String name)
		{
			this.value = value;
			this.name = name;
		}
		public int getValue() {
			return value;
		}
		
		public String getName()
		{
			return name;
		}
		public String toString()
		{
			return name + Integer.toString(value);
		}
    };
      
    
    private final MessageType messageType;
    
    /**
    * Either raw text that contains the message or locale-message key
    */
    private final String msg;
   
    /**
    * Type of the locale message, this is applicable only if type is
    * CLocaleMsg. Current defaults to MessageSetWeb
    */
    private volatile int localeType; // NOTE: really a MessageSetType, but LocaleMessage is a very costly header to include just for that
    
    /** Verbosity of the error message */
    private final ErrorVerbosity errorVerbosity;
    
    /**
    * Type of the error, specifically refers to following DBLog entry actions
    * at the time of this writing.
    * <pre>
    * LAction_FEEL_User_Error
    * LAction_FEEL_System_Error
    */
    private final FeelError errorType;

    public FeelError getErrorType() {
		return errorType;
	}

	public MessageType getType() {
		return messageType;
	}

	public String getMsg() {
		return msg;
	}


	public ErrorVerbosity getVerbosity() {
		return errorVerbosity;
	}

	public int getLocaleType() {
		return localeType;
	}

	public void setLocaleType(int localeType) {
		this.localeType = localeType;
	}

	//type is (ErrorLogEntry::CInvalid) Status
	//verbosity (LAction_FEEL_System_Error) FeelError
	//error type User Error = 2701 FeelError LAction_FEEL_System_Error

	public MessageEntry(MessageType type, String msg, ErrorVerbosity verbosity,
			FeelError errorType) 
	{
		this.messageType = type; //raw or localized msg key
		this.msg = msg;
		this.errorVerbosity = verbosity;
		this.errorType = errorType;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MessageType: ").append(messageType.getName());
		sb.append(" msg: ").append(msg);
		sb.append(" localeType: ").append(localeType);
		sb.append(" errorVerbosity: ").append(errorVerbosity.getName());
		return sb.toString();

	}
}