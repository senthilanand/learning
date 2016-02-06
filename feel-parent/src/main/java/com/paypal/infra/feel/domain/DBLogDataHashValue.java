package com.paypal.infra.feel.domain;

import java.util.HashMap;
import java.util.Map;

import com.paypal.infra.feel.domain.FeelServConstants.DBLogDataObjectType;

/**
 * Data structure for DBLogEntry data map element. The data value can be a String or a Map which contains other DBLogDataHashValue objects
 */
public class DBLogDataHashValue {
	
	private final String valueString;                
	private final Map<String, DBLogDataHashValue> valueMap;
	
	/**
	 * Construct a DBLogDataHashValue object with a string
	 * @param valueString 
	 */
	public DBLogDataHashValue(String valueString){
		this.valueString = valueString;
		valueMap = new HashMap<String, DBLogDataHashValue>();
	}
		
	/**
	 * Get the type of the data object
	 * @return 0 - DBLogDataObjectType.OBJECT_TYPE_STRING
	 *	       1 - DBLogDataObjectType.OBJECT_TYPE_MAP
	 */
	public DBLogDataObjectType getType() {
		return (valueMap.size() > 1)?DBLogDataObjectType.OBJECT_TYPE_MAP:DBLogDataObjectType.OBJECT_TYPE_STRING;
	}
	
	/**
	 * Get the data value String
	 * @return a string representing the data value 
	 */
	public String getDataValueString() {
		return valueString;
	}
	
	/**
	 * Get the data value Map
	 * @return a map which contains other DBLogDataHashValue objects 
	 */
	public Map<String, DBLogDataHashValue> getDataValueMap() {
		return valueMap;
	}
	
}