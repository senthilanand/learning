package com.paypal.infra.feel.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.paypal.infra.feel.domain.FeelServConstants.DBLogDataObjectType;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogDataType;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogEventType;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogLookupType;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogMasterCorrelationType;
import com.paypal.types.Currency;
/**
 * Encapsulates data to be written to "Front-End Error Logging" (FEEL) database wevent_data and wevent_lookup table. </p> 
 */
public class FeelDBLogEntry implements IFeelLogEntry{
	
	public static final String DBLOGGING_AMOUNT = "amt";                    // used as inner key for currency data
	public static final String DBLOGGING_CURRENCY_CODE = "currency_code";   // used as inner key for currency data
	
	private String masterCorrelationId;
	private DBLogMasterCorrelationType masterCorrelationType;
	private long eventId;
	private DBLogEventType eventType;
	private DBLogDataType dataType;
	
	private final Map<String, DBLogLookupType>  lookupMap = new HashMap<String, DBLogLookupType>();
	private final Map<String, DBLogDataHashValue> dataMap = new HashMap<String, DBLogDataHashValue>();
	
	private static final Map<String, Long> ACTION_EVENTID_MAP;
	static {
		Map<String, Long> action_eventid_map = new HashMap<String, Long>();

		action_eventid_map.put("AddressVerify",1L);
		action_eventid_map.put("BillAgreementUpdate",2L);
		action_eventid_map.put("BillUser",3L);
		action_eventid_map.put("DoAuthorization",4L);
		action_eventid_map.put("DoCapture",5L);
		action_eventid_map.put("DoDirectPayment",6L);
		action_eventid_map.put("DoExpressCheckoutPayment",7L);
		action_eventid_map.put("DoReauthorization",8L);
		action_eventid_map.put("DoVoid",9L);
		action_eventid_map.put("GetExpressCheckoutDetails",10L);
		action_eventid_map.put("GetTransactionDetails",11L);
		action_eventid_map.put("MassPay",12L);
		action_eventid_map.put("RefundTransaction",13L);
		action_eventid_map.put("SetExpressCheckout",14L);
		action_eventid_map.put("SetPaymentStatus",15L);
		action_eventid_map.put("TransactionSearch",16L);
		action_eventid_map.put("DoUATPExpressCheckoutPayment",17L);
		action_eventid_map.put("DoUATPAuthorization",18L);
		action_eventid_map.put("DoDirectPaymentInternal",19L);
		action_eventid_map.put("webscr",50L);
		action_eventid_map.put("AuthSettle::AuthSettleReauthFlow", 51L);
		action_eventid_map.put("AuthSettle::AuthSettleCaptureFlow", 52L);
		action_eventid_map.put("AuthSettle::AuthSettleVoidFlow", 53L);
		action_eventid_map.put("Merchants::VtDccFlow", 54L);
		action_eventid_map.put("_account-refund-submit", 55L);
		action_eventid_map.put("HostedPayments::ExpressCheckoutFlow", 56L);
		action_eventid_map.put("HostedPayments::WebAcceptFlow", 57L);
		action_eventid_map.put("SetExpressCheckoutForETL",58L);
		action_eventid_map.put("DoExpressCheckoutPaymentForETL",59L);
		action_eventid_map.put("CreateBillingAgreementForETL",60L);
		action_eventid_map.put("CreateRecurringPaymentsProfileForETL",61L);
		action_eventid_map.put("SetCustomerBillingAgreementForETL",62L);
		
		action_eventid_map.put("IVRAuthenticateUser", 100L);
		action_eventid_map.put("GMCreatePayment", 101L);
		action_eventid_map.put("GMGetFundingSources", 102L);
		action_eventid_map.put("GMUpdatePayment", 103L);
		action_eventid_map.put("GMCompletePayment", 104L);
		action_eventid_map.put("GMGetUserDetails", 105L);
		action_eventid_map.put("GMGetBalance", 106L);
		action_eventid_map.put("GMGetAddresses", 107L);
		action_eventid_map.put("GMQuickPayment", 108L);
		action_eventid_map.put("GMRecentHistory", 109L);
		
		action_eventid_map.put("IVRCreatePayment", 191L);
		action_eventid_map.put("IVRGetFundingSources", 192L);
		action_eventid_map.put("IVRUpdatePayment", 193L);
		action_eventid_map.put("IVRCompletePayment", 194L);
		action_eventid_map.put("IVRGetUserDetails", 195L);
		action_eventid_map.put("IVRGetBalance", 196L);
		action_eventid_map.put("IVRGetAddresses", 197L);
		action_eventid_map.put("IVRGetNextCall", 198L);
		action_eventid_map.put("IVRUpdateNotificationTask", 199L);
		action_eventid_map.put("IVRConfirmPhone", 200L);
		
		ACTION_EVENTID_MAP = Collections.unmodifiableMap(action_eventid_map);
	}
	
	/**
	 * Constructor
	 * @param masterCorrelationId - defined at the level of flow that needs to bind together events
	 * @throws NullPointerException if masterCorrelationId is null
	 */
	public FeelDBLogEntry(String masterCorrelationId) {
		if(masterCorrelationId == null) {
			throw new NullPointerException("MasterCorrelationId cannot be null");
		}
		this.masterCorrelationId = masterCorrelationId;
	}
	
	/**
	 * Constructor
	 * @param masterCorrelationId - defined at the level of flow that needs to bind together events
	 * @param masterCorrelationType - identifies the name space of the master correlation id is on
	 * @param eventId - uniquely identifies a particular API call or state execution
	 * @param eventType - indicates which particular API, state etc. 
	 * @throws NullPointerException if masterCorrelationId is null
	 */
	public FeelDBLogEntry(String masterCorrelationId, DBLogMasterCorrelationType masterCorrelationType, long eventId, DBLogEventType eventType) 
	{
		if(masterCorrelationId == null) {
			throw new NullPointerException("MasterCorrelationId cannot be null");
		}
		this.masterCorrelationId = masterCorrelationId;
		this.masterCorrelationType = masterCorrelationType;
		this.eventId = eventId;
		this.eventType = eventType;
	}
	
	public String getMasterCorrelationId() {
		return this.masterCorrelationId;
	}
	
	public void setMasterCorrelationType(DBLogMasterCorrelationType masterCorrelationType) {
		this.masterCorrelationType = masterCorrelationType;
	}
	
	public DBLogMasterCorrelationType getMasterCorrelationType() {
		return this.masterCorrelationType;
	}
	
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
	public long getEventId() {
		return this.eventId;
	}
	
	public void setEventType(DBLogEventType eventType) {
		this.eventType = eventType;
	}
	
	public DBLogEventType getEventType() {
		return this.eventType;
	}
	
	public void setDataType(DBLogDataType dataType) {
		this.dataType = dataType;
	}
	
	public DBLogDataType getDataType() {
		return this.dataType;
	}
	
	public void addLookupItem(String lookupKey, DBLogLookupType lookupType){
		if(lookupKey == null || lookupKey.isEmpty()) {
			return;
		}
		if(!lookupMap.containsKey(lookupKey)) {
			lookupMap.put(lookupKey, lookupType);
		}
	}
	
	public void addLookupItems(Map<String, DBLogLookupType> lookups) {
		lookupMap.putAll(lookups);
	}

	public Map<String, DBLogLookupType> getLookupMap() {
		return Collections.unmodifiableMap(lookupMap);
	}
	
	public long getEventIdFromAction(String action) {
		Long eventId = ACTION_EVENTID_MAP.get(action);
		return (eventId == null)?0:eventId;
	}
	
	public void addData(String dataKey, String dataValue) {
		if(dataKey == null || dataKey.isEmpty() 
				|| dataValue == null || dataValue.isEmpty()) {
			return;
		}
		dataMap.put(dataKey, new DBLogDataHashValue(dataValue));
	}
	
	public void addCurrencyData(String dataKey, Currency currency) {
		if(dataKey == null || dataKey.isEmpty() || currency == null) {
			return;
		}
		addDataItem(dataKey, DBLOGGING_AMOUNT, String.valueOf(currency.getAmount()));
		addDataItem(dataKey, DBLOGGING_CURRENCY_CODE, currency.getCurrencyCode());
	}
	
	public void addDataItem(String outerKey, String innerKey, String innerValue) {
		if(outerKey == null || outerKey.isEmpty() 
				|| innerKey == null || innerKey.isEmpty() 
				|| innerValue == null || innerValue.isEmpty()) {
			return;		}
		DBLogDataHashValue item = dataMap.get(outerKey);
		if (item == null) {
			item = new DBLogDataHashValue(innerValue);
			dataMap.put(outerKey, item);
		}
		item.getDataValueMap().put(innerKey, new DBLogDataHashValue(innerValue));
	}
	
	/**
	 * Get data map
	 */
	public Map<String, DBLogDataHashValue> getDataMap() {
		return dataMap;
	}
	
	/**
	 * Clear lookup map and data map
	 */
	public void clearLookupData() {
		lookupMap.clear();
		dataMap.clear();
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("logMasterCorrelationId: ").append(masterCorrelationId);
		if(masterCorrelationType != null) {
			sb.append(" logMasterCorrelationType: ").append(masterCorrelationType.name());
		}
		if(eventType != null) {
			sb.append(" logEventType: ").append(eventType.name());
		}
		sb.append(" logEventId: ").append(eventId);
		if(dataType != null){
			sb.append(" dataType: ").append(this.dataType.name());
		}
		if(lookupMap != null) {
			sb.append(" lookupMap: ");
		
			for(Map.Entry<String, DBLogLookupType> mapEntry : lookupMap.entrySet()) {
				sb.append('<');
				sb.append(mapEntry.getKey());
				sb.append(',');
				sb.append(mapEntry.getValue().name());
				sb.append('>');
			}
		
			sb.append(" dataMap: ");
			sb.append(internalHashMapToString(dataMap));
		}
		return sb.toString();
	}
	
	public String internalHashMapToString(Map<String, DBLogDataHashValue> dataMap) {
		
		if(dataMap ==  null) return "";
		
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, DBLogDataHashValue> mapEntry : dataMap.entrySet()) {
			String dataKey = mapEntry.getKey();
			DBLogDataHashValue dataValue = mapEntry.getValue();
			if(dataValue.getType() == DBLogDataObjectType.OBJECT_TYPE_STRING) {
				sb.append('<');
				sb.append(dataKey);
				sb.append(',');
				sb.append(dataValue.getDataValueString());
				sb.append('>');
			}else {
				sb.append('<');
				sb.append(dataKey);
				sb.append(',');
				sb.append(internalHashMapToString(dataValue.getDataValueMap()));
				sb.append('>');
			}
		}
		return sb.toString();
	}
}