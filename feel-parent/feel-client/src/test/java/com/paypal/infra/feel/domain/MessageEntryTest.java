package com.paypal.infra.feel.domain;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import com.paypal.infra.feel.domain.MessageEntry;
import com.paypal.infra.feel.domain.FeelServConstants.ErrorVerbosity;
import com.paypal.infra.feel.domain.FeelServConstants.FeelError;
import com.paypal.infra.feel.domain.MessageEntry.MessageType;

public class MessageEntryTest {
	private static MessageType[] messageTypes = {MessageType.CInvalid, MessageType.CLocaleMsg, MessageType.CRawMsg};
	private static String[] messages = {"Invalid message", "Local message", "Raw message"};
	private static ErrorVerbosity[] errorVerbosities = {ErrorVerbosity.Invalid, ErrorVerbosity.ErrorDetails, ErrorVerbosity.ErrorSummary};
	private static FeelError[] feelErrors = {FeelError.LAction_FEEL_System_Error, FeelError.LAction_FEEL_User_Error, FeelError.LAction_FEEL_System_Error};
	
	@Test
	public void testMessageEntry(){
		for(int i=0; i<messageTypes.length; i++){
			MessageEntry me  = new MessageEntry(messageTypes[i], messages[i], errorVerbosities[i], feelErrors[i]);
			AssertJUnit.assertTrue(feelErrors[i].getValue() == me.getErrorType().getValue());
			AssertJUnit.assertTrue(0 == me.getLocaleType());
			AssertJUnit.assertEquals(messages[i], me.getMsg());
			AssertJUnit.assertEquals(messageTypes[i].getName() + messageTypes[i].getValue(), me.getType().getName() + me.getType().getValue());
			AssertJUnit.assertEquals(errorVerbosities[i].getName(), me.getVerbosity().getName());
		}
	}
	
	@Test 
	public void testMessageEntryToString(){
		for(int i=0; i<messageTypes.length; i++){
			MessageEntry me  = new MessageEntry(messageTypes[i], messages[i], errorVerbosities[i], feelErrors[i]);
			AssertJUnit.assertEquals("MessageType: " + messageTypes[i].getName() + " msg: " + messages[i] + " localeType: " 
				+ me.getLocaleType() + " errorVerbosity: " + errorVerbosities[i].getName(), me.toString());
		}
	}
	
	@Test 
	public void testMessageEntrySetAndGet(){
		for(int i=0; i<messageTypes.length; i++){
			
			MessageType type = messageTypes[i];
			String msg = messages[i];
			ErrorVerbosity verbosity = errorVerbosities[i];
			FeelError errorType = feelErrors[i];
			
			MessageEntry me  = new MessageEntry(type, msg, verbosity, errorType);
			
			AssertJUnit.assertEquals("MessageType: " + messageTypes[i].getName() + " msg: " + messages[i] + " localeType: " 
				+ me.getLocaleType() + " errorVerbosity: " + errorVerbosities[i].getName(), me.toString());
		}
	}
}