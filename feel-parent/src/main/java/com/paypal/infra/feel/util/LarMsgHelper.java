package com.paypal.infra.feel.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.paypal.feel.ArrayElementVO;
import com.paypal.feel.DBLogEntryVO;
import com.paypal.feel.DBLoggingDataHashValueVO;
import com.paypal.feel.DBLoggingDataTypeEnum;
import com.paypal.feel.DBLoggingEventTypeEnum;
import com.paypal.feel.DBLoggingLookupItemVO;
import com.paypal.feel.DBLoggingLookupTypeEnum;
import com.paypal.feel.DBLoggingMasterCorrelationTypeEnum;
import com.paypal.feel.ErrorLogEntryVO;
import com.paypal.feel.ErrorLogPageInfoVO;
import com.paypal.feel.FeelDBMessage;
import com.paypal.feel.MessageEntryVO;
import com.paypal.feel.MessageTypeEnum;
import com.paypal.feel.ObjectTypeEnum;


import com.paypal.infra.feel.domain.DBLogDataHashValue;
import com.paypal.infra.feel.domain.ErrorLogPageInfo;
import com.paypal.infra.feel.domain.FeelDBLogEntry;
import com.paypal.infra.feel.domain.FeelErrorLogEntry;
import com.paypal.infra.feel.domain.FeelServConstants;
import com.paypal.infra.feel.domain.IFeelLogEntry;
import com.paypal.infra.feel.domain.MessageEntry;
import com.paypal.infra.feel.domain.FeelServConstants.DBLogLookupType;
import com.paypal.infra.lar.LarClient;

public class LarMsgHelper {

		/**
		 * Write out a FeelLogEntry as an ASFRequest by using Lar Client
		 * @param entry - FeelLog entry to write out
		 * @param larClient - larClient used to send the FeelLog entry to the Lar message broker  
		 * @throws IOException
		 */
		public static void writeAsLarRequest(IFeelLogEntry entry, LarClient larClient) throws IOException {

			if (entry instanceof FeelErrorLogEntry) {
				FeelDBMessage errorLogMsg = convertErrorLogEntryToVO((FeelErrorLogEntry)entry);
				larClient.sendLARRequest("feeleventservice", errorLogMsg);
			}else if (entry instanceof FeelDBLogEntry) {
				FeelDBMessage dbLogMsg = convertDBLogEntryToVO((FeelDBLogEntry)entry);
				larClient.sendLARRequest("feeleventservice", dbLogMsg);
			} else {
				throw new IOException("Unsupported object type!");
			}
		}

		/*
		 * Convert a FeelErrorLogEntry to a FeelDBMessage
		 */
		static FeelDBMessage convertErrorLogEntryToVO(FeelErrorLogEntry entry) {
			ErrorLogEntryVO errorLogEntryVO = new ErrorLogEntryVO();
			
			errorLogEntryVO.setMAcctNum(BigInteger.valueOf(entry.getAcctNum()));
			errorLogEntryVO.setMFlags(BigInteger.valueOf(entry.getFlags()));
			errorLogEntryVO.setMVisitorId(BigInteger.valueOf(entry.getVisitorId()));
		
			// Set the Current Page Info
			ErrorLogPageInfo errorLogPageInfo = entry.getPage();
			if (null != errorLogPageInfo) {
				ErrorLogPageInfoVO errorLogPageInfoVO = new ErrorLogPageInfoVO();
				errorLogPageInfoVO.setMLanguage(errorLogPageInfo.getLanguage());
				errorLogPageInfoVO.setMMaxcodeTempl(errorLogPageInfo.getMaxcodeTemplate());
				errorLogPageInfoVO.setMXslTempl(errorLogPageInfo.getXslTemplate());
				errorLogPageInfoVO.setMTitle(errorLogPageInfo.getTitle());
				
				errorLogEntryVO.setMPage(errorLogPageInfoVO);
			}
			
			errorLogEntryVO.setMCmd(entry.getCmd());
			errorLogEntryVO.setMScript(entry.getScript());
			errorLogEntryVO.setMPrevCmd(entry.getPrevCmd());
			errorLogEntryVO.setMPrevScript(entry.getPrevScript());
			
			// Set the Previous Page Info
			ErrorLogPageInfo prevErrorLogPageInfo = entry.getPrevPage();
			if (null != prevErrorLogPageInfo) {
				ErrorLogPageInfoVO prevErrorLogPageInfoVO = new ErrorLogPageInfoVO();
				prevErrorLogPageInfoVO.setMLanguage(prevErrorLogPageInfo.getLanguage());
				prevErrorLogPageInfoVO.setMMaxcodeTempl(prevErrorLogPageInfo.getMaxcodeTemplate());
				prevErrorLogPageInfoVO.setMXslTempl(prevErrorLogPageInfo.getXslTemplate());
				prevErrorLogPageInfoVO.setMTitle(prevErrorLogPageInfo.getTitle());
				
				errorLogEntryVO.setMPrevPage(prevErrorLogPageInfoVO);
			}
			errorLogEntryVO.setMIp(entry.getIp());
			errorLogEntryVO.setMErrorFlag(BigInteger.valueOf(entry.getErrorFlag()));
			
			
			List<MessageEntry> list = entry.getMsgs();
			List<MessageEntryVO> messageEntryVOList = new ArrayList<MessageEntryVO>(list.size());
			for (MessageEntry m : list) {
				MessageEntryVO messageEntryVO = new MessageEntryVO();
				messageEntryVO.setMType(MessageTypeEnum.getEnumByValue(m.getType().getValue()));
				messageEntryVO.setMVerbosity(m.getVerbosity().getValue());
				messageEntryVO.setMErrorType(BigInteger.valueOf(m.getErrorType().getValue()));
				messageEntryVO.setMMsg(m.getMsg());
				messageEntryVO.setMLocaleType(m.getLocaleType());
				
				messageEntryVOList.add(messageEntryVO);
			}
			errorLogEntryVO.setMMsgs(messageEntryVOList);
			
			errorLogEntryVO.setMBacktrace(entry.getBacktrace());
			errorLogEntryVO.setMSystemInfo(entry.getSystemInfo());
			
			FeelDBMessage feelDBMessage = new FeelDBMessage();
			feelDBMessage.setMCmd(FeelServConstants.Command.CmdLog.getValue());
			feelDBMessage.setMErrorLog(errorLogEntryVO);
			return feelDBMessage;
		}
		
		/*
		 * Convert a FeelDBLogEntry to a FeelDBMessage
		 */
		static FeelDBMessage convertDBLogEntryToVO(FeelDBLogEntry entry) {
			
			DBLogEntryVO dbLogEntryVO = new DBLogEntryVO();
			dbLogEntryVO.setMasterCorrelationId(entry.getMasterCorrelationId());
			dbLogEntryVO.setMasterCorrelationType(DBLoggingMasterCorrelationTypeEnum.getEnumByValue(entry.getMasterCorrelationType().getValue()));
			dbLogEntryVO.setEventId(BigInteger.valueOf(entry.getEventId()));
			dbLogEntryVO.setEventType(DBLoggingEventTypeEnum.getEnumByValue(entry.getEventType().getValue()));
			dbLogEntryVO.setDataType(DBLoggingDataTypeEnum.getEnumByValue(entry.getDataType().getValue()));
			
			Map<String, DBLogLookupType> lookupMap = entry.getLookupMap();
			List<DBLoggingLookupItemVO> lookupList = new ArrayList<DBLoggingLookupItemVO>(lookupMap.size());
			for(Map.Entry<String, DBLogLookupType> mapEntry : lookupMap.entrySet()) {
				DBLoggingLookupItemVO lookupItem = new DBLoggingLookupItemVO();
				lookupItem.setLookupKey(mapEntry.getKey());
				lookupItem.setLookupType(DBLoggingLookupTypeEnum.getEnumByValue(mapEntry.getValue().getValue()));
				
				lookupList.add(lookupItem);
			}
			dbLogEntryVO.setLookupHash(lookupList);
			
			dbLogEntryVO.setDataHash(convertDBLogDataHashMapToVOList(entry.getDataMap()));
			FeelDBMessage feelDBMessage = new FeelDBMessage();
			feelDBMessage.setMCmd(FeelServConstants.Command.CmdDBLog.getValue());
			feelDBMessage.setMDbLog(dbLogEntryVO);
			return feelDBMessage;
		}
		
		/*
		 * Convert a dataHashMap to an ArrayElementVO list
		 */
		static List<ArrayElementVO> convertDBLogDataHashMapToVOList(Map<String, DBLogDataHashValue> dataHashMap) {
			
			List<ArrayElementVO>  arrayElementVOList = new ArrayList<ArrayElementVO>(dataHashMap.size());
			
			for(Map.Entry<String, DBLogDataHashValue> mapEntry : dataHashMap.entrySet()) {
				ArrayElementVO dataItem = new ArrayElementVO();
				dataItem.setKey(mapEntry.getKey());
				
				DBLoggingDataHashValueVO dataHashValueVO = new DBLoggingDataHashValueVO();
				DBLogDataHashValue dataHashValue = mapEntry.getValue();
				dataHashValueVO.setType(ObjectTypeEnum.getEnumByValue(dataHashValue.getType().getValue()));
				dataHashValueVO.setValueString(dataHashValue.getDataValueString());
				
				Map<String, DBLogDataHashValue> internalDataHashMap = dataHashValue.getDataValueMap();
				dataHashValueVO.setValueTable(convertDBLogDataHashMapToVOList(internalDataHashMap));
				
				dataItem.setVal(dataHashValueVO);
				
				arrayElementVOList.add(dataItem);
			}
			return arrayElementVOList;
		}
}