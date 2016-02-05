package com.paypal.infra.feel.domain;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.HashMap;
import java.util.Map;

import com.paypal.infra.feel.domain.FeelServConstants.DBLogDataType;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogEventType;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogLookupType;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogMasterCorrelationType;
import com.paypal.types.Currency;
import com.paypal.types.Currency.Info;

public class FeelDBLogEntryTest {
	@Test 
	public void testFeelLogEntry(){
		String masterCorrelationId = "1234fbn";
		FeelDBLogEntry feelDBLogEntry = new FeelDBLogEntry("1234fbn");
		
		DBLogMasterCorrelationType masterCorrelationType = DBLogMasterCorrelationType.DBLogMCTAccountAndSession;
		long eventId = 123;
		DBLogEventType eventType = DBLogEventType.DBLog_Event_API;
		DBLogDataType dataType = DBLogDataType.DBLog_Data_Blob;
		
		String lookupKey1 = "lookupKey1";
		DBLogLookupType lookupType1 = DBLogLookupType.DBLog_Lookup_AccountNumber; 
		
		String lookupKey2 = "lookupKey2";
		DBLogLookupType lookupType2 = DBLogLookupType.DBLog_Lookup_ActivityId;
		
		Map<String, DBLogLookupType> lookupTable = new HashMap<String, DBLogLookupType>();
		lookupTable.put(lookupKey1, lookupType1);
		lookupTable.put(lookupKey2, lookupType2);
		
		String dataKey = "cost";
		
		feelDBLogEntry.setDataType(dataType);
		feelDBLogEntry.setEventId(eventId);
		feelDBLogEntry.setEventType(eventType);
		feelDBLogEntry.setMasterCorrelationType(masterCorrelationType);
		feelDBLogEntry.addLookupItem(lookupKey1, lookupType1);
		feelDBLogEntry.addLookupItem(lookupKey2, lookupType2);
		feelDBLogEntry.addCurrencyData(dataKey, new Currency(Info.getInstance("USD"), 100));
		
		AssertJUnit.assertTrue(dataType == feelDBLogEntry.getDataType());
		AssertJUnit.assertTrue(eventId == feelDBLogEntry.getEventId());
		AssertJUnit.assertTrue(eventType == feelDBLogEntry.getEventType());
		AssertJUnit.assertTrue(masterCorrelationId.equals(feelDBLogEntry.getMasterCorrelationId()));
		AssertJUnit.assertTrue(masterCorrelationType == feelDBLogEntry.getMasterCorrelationType());
		AssertJUnit.assertTrue(lookupTable.equals(feelDBLogEntry.getLookupMap()));
		System.out.println(feelDBLogEntry.toString());
		
		AssertJUnit.assertTrue(feelDBLogEntry.getDataMap().containsKey(dataKey));
		AssertJUnit.assertTrue(feelDBLogEntry.getDataMap().get(dataKey).getDataValueMap().containsKey(FeelDBLogEntry.DBLOGGING_AMOUNT));
		AssertJUnit.assertTrue(feelDBLogEntry.getDataMap().get(dataKey).getDataValueMap().containsKey(FeelDBLogEntry.DBLOGGING_CURRENCY_CODE));
		AssertJUnit.assertTrue("logMasterCorrelationId: 1234fbn logMasterCorrelationType: DBLogMCTAccountAndSession logEventType: DBLog_Event_API logEventId: 123 dataType: DBLog_Data_Blob lookupMap: <lookupKey2,DBLog_Lookup_ActivityId><lookupKey1,DBLog_Lookup_AccountNumber> dataMap: <cost,<amt,100><currency_code,USD>>".equals(feelDBLogEntry.toString()));
		
	}
}