package com.paypal.infra.feel;

import java.util.ArrayList;

import com.paypal.infra.feel.domain.ErrorLogPageInfo;
import com.paypal.infra.feel.domain.FeelDBLogEntry;
import com.paypal.infra.feel.domain.FeelErrorLogEntry;
import com.paypal.infra.feel.domain.FeelServConstants;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogDataType;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogEventType;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogLookupType;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogMasterCorrelationType;
import com.paypal.infra.feel.domain.FeelServConstants.ErrorVerbosity;
import com.paypal.infra.feel.domain.MessageEntry;
import com.paypal.types.Currency;
import com.paypal.types.Currency.Info;

public class FeelClientTestUtil {
	public static FeelErrorLogEntry getErrorLogSample()
	{
		FeelErrorLogEntry entry = new FeelErrorLogEntry();

		// Set the input_entry
		entry.setAcctNum(1660703379469126825L);
		entry.setErrorFlag(FeelErrorLogEntry.FEEL_SYSTEM_ERROR_FLAG);
		entry.setFlags(17);
		entry.setIp("10.24.1.2");
		entry.setVisitorId(45);
		entry.setBacktrace("I love this backtrace");
		entry.setSystemInfo("hype");

		// set the current page info -
		String language = "curr page lang";
		String maxcodeTemplate = "curr page maxcode templ";
		String title = "curr page title";
		String xslTemplate = "curr page xsl templ";
		
		ErrorLogPageInfo page = new ErrorLogPageInfo(xslTemplate, maxcodeTemplate, title, language);
		entry.setPage(page);
		entry.setCmd("curr page cmd");
		entry.setScript("curr page script");

		// set the previous page info -
		language = "prev page lang";
		maxcodeTemplate = "prev page maxcode templ";
		title = "prev page title";
		xslTemplate = "prev page xsl templ";
		
		ErrorLogPageInfo ppage = new ErrorLogPageInfo(xslTemplate, maxcodeTemplate, title, language);
		entry.setPrevPage(ppage);
		entry.setPrevCmd("prev page cmd");
		entry.setPrevScript("prev page script");

		// set the actual error message info -
		MessageEntry msg = new MessageEntry(MessageEntry.MessageType.CRawMsg,
											"Lovely Error Msg from Sparta Framework test",
											ErrorVerbosity.ErrorDetails,
											FeelServConstants.FeelError.LAction_FEEL_System_Error);
		msg.setLocaleType(1);
		
		ArrayList<MessageEntry> msgs = new ArrayList<MessageEntry>();
		msgs.add(msg);
		entry.setMsgs(msgs);

		return entry;

	}
	
	public static FeelDBLogEntry getDBLogSample() {
		FeelDBLogEntry entry = new FeelDBLogEntry("2033877");
		entry.setMasterCorrelationType(DBLogMasterCorrelationType.DBLogMCTAccountAndSession);
		entry.setEventId(20120123);
		entry.setEventType(DBLogEventType.DBLog_Event_API);
		entry.setDataType(DBLogDataType.DBLog_Data_EventSpecific);
		entry.addLookupItem("EC-7YV83544V81079615", DBLogLookupType.DBLog_Lookup_AccountNumber);
		entry.addData("machine", "10.11.1.127");
		entry.addCurrencyData("cost", new Currency(Info.getInstance("USD"), 100));
		return entry;
	}
	
	public static FeelDBLogEntry getDBLogSample1() {
		FeelDBLogEntry entry = new FeelDBLogEntry("2033877");
		entry.setMasterCorrelationType(DBLogMasterCorrelationType.DBLogMCTAccountAndSession);
		entry.setEventId(20120123);
		entry.setEventType(DBLogEventType.DBLog_Event_API);
		entry.setDataType(DBLogDataType.DBLog_Data_EventSpecific);
		entry.addLookupItem("EC-7YV83544V81079615", DBLogLookupType.DBLog_Lookup_StoreId);
		entry.addData("machine", "10.11.1.127");
		entry.addCurrencyData("cost", new Currency(Info.getInstance("USD"), 100));
		return entry;
	}
}