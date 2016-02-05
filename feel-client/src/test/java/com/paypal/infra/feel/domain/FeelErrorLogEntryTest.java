package com.paypal.infra.feel.domain;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertNotNull;
import org.testng.AssertJUnit;
import java.util.ArrayList;
import java.util.List;

import com.paypal.infra.feel.domain.ErrorLogPageInfo;
import com.paypal.infra.feel.domain.FeelErrorLogEntry;
import com.paypal.infra.feel.domain.MessageEntry;
import com.paypal.infra.feel.domain.FeelServConstants.ErrorVerbosity;
import com.paypal.infra.feel.domain.FeelServConstants.FeelError;
import com.paypal.infra.feel.domain.MessageEntry.MessageType;

public class FeelErrorLogEntryTest {

	@Test 
	public void testFeelLogEntry(){
		FeelErrorLogEntry fle = new FeelErrorLogEntry();

		List<MessageEntry> list = new ArrayList<MessageEntry>();
		MessageEntry me = new MessageEntry(MessageType.CInvalid, "Invalid message", ErrorVerbosity.Invalid, FeelError.LAction_FEEL_System_Error);
		list.add(me);
		
		String xslTemplate = "curr page xsl templ";
		String maxcodeTemplate = "curr page maxcode templ";
		String title = "curr page title";
		String language = "curr page lang"; 
		ErrorLogPageInfo page = new ErrorLogPageInfo(xslTemplate, maxcodeTemplate, title, language);
		
		xslTemplate = "prev page xsl templ";
		maxcodeTemplate = "prev page maxcode templ";
		title = "prev page title";
		language = "prev page lang";
		
		ErrorLogPageInfo ppage = new ErrorLogPageInfo(xslTemplate, maxcodeTemplate, title, language);
		
		long acctNum = 1660703379469126825L;
		String backTrace = "I love this backtrace"; 
		String cmd = "curr page cmd";
		long errorFlag = 2L;
		long flags = 17L;
		String ip = "10.24.1.2";
		String prevCmd = "prev page cmd";
		String prevScript = "prev page script"; 
		String script = "curr page script";
		String systemInfo = "hype";
		long timeCreated = 577L;
		long visitorId = 45L;
		
		fle.setAcctNum(acctNum);
		fle.setBacktrace(backTrace);
		fle.setCmd(cmd);
		fle.setErrorFlag(errorFlag);
		fle.setFlags(flags);
		fle.setIp(ip);
		fle.setMsgs(list);
		fle.setPage(page);
		fle.setPrevCmd(prevCmd);
		fle.setPrevPage(ppage);
		fle.setPrevScript(prevScript);
		fle.setScript(script);
		fle.setSystemInfo(systemInfo);
		fle.setVisitorId(visitorId);
		AssertJUnit.assertTrue(acctNum == fle.getAcctNum());
		AssertJUnit.assertTrue(backTrace.equals(fle.getBacktrace()));
		AssertJUnit.assertTrue(cmd.equals(fle.getCmd()));
		AssertJUnit.assertTrue(errorFlag == fle.getErrorFlag());
		AssertJUnit.assertTrue(flags == fle.getFlags());
		AssertJUnit.assertTrue(ip.equals(fle.getIp()));
		AssertJUnit.assertTrue(list.toString().equals(fle.getMsgs().toString()));
		AssertJUnit.assertTrue(page.equals(fle.getPage()));
		AssertJUnit.assertTrue(prevCmd.equals(fle.getPrevCmd()));
		AssertJUnit.assertTrue(ppage.equals(fle.getPrevPage()));
		AssertJUnit.assertTrue(prevScript.equals(fle.getPrevScript()));
		AssertJUnit.assertTrue(script.equals(fle.getScript()));
		AssertJUnit.assertTrue(systemInfo.equals(fle.getSystemInfo()));
		AssertJUnit.assertTrue(visitorId == fle.getVisitorId());
		AssertJUnit.assertTrue(fle.equals(fle));
		AssertJUnit.assertFalse(fle.equals(null));
	}
	
	@Test 
	public void testFeelLogEntry2(){
		String msg = "Lovely Error Msg from Sparta Framework test"; 
		FeelErrorLogEntry fle = new FeelErrorLogEntry(msg);
		MessageEntry messageEntry = new MessageEntry(MessageEntry.MessageType.CRawMsg, msg, ErrorVerbosity.ErrorDetails, FeelError.LAction_FEEL_System_Error);
		AssertJUnit.assertTrue(messageEntry.toString().equals(fle.getMsgs().get(0).toString()));
	}
	
	@Test 
	public void testFeelLogEntry3(){
		FeelErrorLogEntry fle = new FeelErrorLogEntry(null);
		AssertJUnit.assertEquals("[]", fle.getMsgs().toString());
	}
	
	@Test 
	public void testFeelLogEntry4(){
		FeelErrorLogEntry fle = new FeelErrorLogEntry();
		assertNotNull(fle.hashCode());
	}

}