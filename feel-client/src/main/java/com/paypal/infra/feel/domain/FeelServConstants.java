package com.paypal.infra.feel.domain;

/**
 * Front end error logging constants
 * 
 * @see com.paypal.infra.feel.FeelServClient
 *
 */
public class FeelServConstants {

	/*------------------------- Constants for FeelServClient ---------------------------*/
	public enum Command
	{
		CmdLog(1),
		CmdDBLog(2);
		
		private final int value;
		
		private Command(int value)
		{
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		
		public String toString()
		{
			return Integer.toString(value);
		}
	} //end Command

	public enum DBLogEntry
	{
		LogMasterCorrelationId(100),
		LogMasterCorrelationType(101),
		LogEventType(102),
		LogEventId(103),
		LogLookupKey(104),
		LogLookupType(105),
		LogDataType(106),
		LogDataValue(107),
		BufferEnd(108);		 //Marks the End of the Buffer
		  
		private final int value;
		private DBLogEntry(int value)
		{
			this.value = value;
		}
		public int getValue() {
			return value;
		}
		public String toString()
		{
			return Integer.toString(value);
		}
	} //end DBLogEntry
	
	public enum FeelEntry
	{
		LogId(100,"LogId"),
		LogAcctNo(101, "LogAcctNo"),
		LogErrorFlag(102, "LogErrorFlag"),
		LogFlags(103, "LogFlags"),
		LogIP(104, "LogIP"),
		LogTimeCreated(105, "LogTimeCreated"),
		LogVisitorId(106, "LogVisitorId"),
		LogMonth(107, "LogMonth"),

		//Current Page Info
		LogPageId(108, "LogPageId"),
		LogCmd(109, "LogCmd"),
		LogScript(110, "LogScript"),
		LogLang(111, "LogLang"),
		LogPageTitle(112, "LogPageTitle"),
		LogXslTmpl(113, "LogXslTmpl"),
		LogMaxcodeTmpl(114, "LogMaxcodeTmpl"),

		//Previous Page Info
		LogPrevPageId(115, "LogPrevPageId"),
		LogPrevCmd(116, "LogPrevCmd"),
		LogPrevScript(117, "LogPrevScript"),
		LogPrevLang(118, "LogPrevLang"),
		LogPrevPageTitle(119, "LogPrevPageTitle"),
		LogPrevXslTmpl(120, "LogPrevXslTmpl"),
		LogPrevMaxcodeTmpl(121, "LogPrevMaxcodeTmpl"),

		LogMsgType(122, "LogMsgType"),	//Could be a raw error message or it's localized version
		LogMsgData(123, "LogMsgData"),
		LogMsgVerbosity(124, "LogMsgVerbosity"),
		LogMsgErrorType(125, "LogMsgErrorType"),

		LogBacktrace(126, "LogBacktrace"),
		LogSystemInfo(127, "LogSystemInfo"),

		BufferEnd(128, "BufferEnd");	  //Marks the End of the Buffer

		private final int value;
		private final String name;
		private FeelEntry(int value, String name)
		{
			this.value = value;
			this.name = name;
		}
		public int getValue() {
			return value;
		}
		
		public String toString()
		{
			return name + ":" + Integer.toString(value);
		}
	}
	
	/*--------------------- Constants for FeelErrorLogEntry ------------------------*/
	
	public enum FeelError
	{	
		LAction_FEEL_User_Error (2701),
	    LAction_FEEL_System_Error(2702);
		
		private final long value;
		private FeelError(long value)
		{
			this.value = value;
		}
		public long getValue() {
			return value;
		}
		public String toString()
		{
			return Long.toString(value);
		}
	}

	/** Verbosity of the error message */
	public enum ErrorVerbosity
	{
		/*
		 * Should be used in case of system errors, as they don't have summary
		 * and detail information
		 */
		Invalid(-1,"Invalid"),
		/**
		 * Summary of the error message
		 */
		ErrorSummary(0,"ErrorSummary"),
		/**
		 * Details of the error message
		 */
		ErrorDetails(1,"ErrorDetails"),
		ErrorFormFields(2,"ErrorFormFields"); // F-22862: some new flows use this instead of ErrorDetails
		
		private final int value;
		private final String name;
		private ErrorVerbosity(int value, String name)
		{
			this.value = value;
			this.name = name;
		}
		public int getValue()
		{
			return value;
		}
		public String getName()
		{
			return name;
		}
		
	};
	
	/*--------------------- Constants for FeelDBLogEntry --------------------------*/
	
	public enum DBLogMasterCorrelationType {
		DBLogMCTAccountAndSession(0),   
	    DBLogMCTCalCorrelationId(1),
	    DBLogMCTMarketToken(2),
	    DBLogMCTOnboarding(3),    //Merchant onboarding
		DBLogMCTMAX(4); 
	    
	    private final int value;
		private DBLogMasterCorrelationType(int value)
		{
			this.value = value;
		}
		public int getValue() 
		{
			return value;
		}
		public String toString()
		{
			return Integer.toString(value);
		}
	}
	
	public enum DBLogEventType {
		DBLog_Event_UNKNOWN(0),
	    DBLog_Event_API(1),
	    DBLog_Event_Flow(2),
	    DBLog_Event_State_Anonymous(3),
	    DBLog_Event_RapidsSubflowTransition(4),
	    DBLog_Event_State_MarketAnalyzeTransaction(5),
	    DBLog_Event_State_MarketRegister(6),
	    DBLog_Event_State_MarketInit(7),
	    DBLog_Event_State_MarketLogin(8),
	    DBLog_Event_State_MarketRecordFundingOptions(9),
	    DBLog_Event_State_BenignError(10),
	    DBLog_Event_State_MoreFundingOptions(11),
	    DBLog_Event_State_MarketRecordNewCCOption(12),
	    DBLog_Event_State_MarketReturnToEBay(13),
	    DBLog_Event_API_SetCheckoutInfo(14),
	    DBLog_Event_API_VerifyGuest(15),
	    DBLog_Event_API_GetCheckoutDetails(16),
	    DBLog_Event_API_GuestBeginMultiplePayment(17),
	    DBLog_Event_API_BeginMultiplePayment(18),
	    DBLog_Event_API_CompletePaymentRequest(19),
	    DBLog_Event_API_GuestCheckAmount(20),
	    DBLog_Event_MTOnboarding_OrganicSignup(21),
	    DBLog_Event_MTOnboarding_PartnerReferredSignup(22),
	    DBLog_Event_State_ExpressCheckoutAddAddress(23),
	    DBLog_Event_State_ExpressCheckoutAddCreditCard(24),
	    DBLog_Event_State_ExpressCheckoutAnalyzeTransaction(25),
	    DBLog_Event_State_ExpressCheckoutBilling(26),
	    DBLog_Event_State_ExpressCheckoutDetermineBillingPage(27),
	    DBLog_Event_State_ExpressCheckoutEditShipping(28),
	    DBLog_Event_State_ExpressCheckoutFatalError(29),
	    DBLog_Event_State_ExpressCheckoutLogin(30),
	    DBLog_Event_State_ExpressCheckoutReview(31),
	    DBLog_Event_State_ExpressCheckoutShipping(32),
	    DBLog_Event_State_ExpressCheckoutShippingCheck(33),
	    DBLog_Event_State_ExpressCheckoutSignup(34),
	    DBLog_Event_State_ExpressCheckoutStart(35),
	    DBLog_Event_State_WAXEditBilling(36),
	    DBLog_Event_State_WAXEditShipping(37),
	    DBLog_Event_State_WAXReview(38),
	    DBLog_Event_State_WAXThanksAndOfferSignup(39),
	    DBLog_Event_State_WebAcceptAddAddress(40),
	    DBLog_Event_State_WebAcceptAddCreditCard(41),
	    DBLog_Event_State_WebAcceptAnalyzeTransaction(42),
	    DBLog_Event_State_WebAcceptBilling(43),
	    DBLog_Event_State_WebAcceptCartStart(44),
	    DBLog_Event_State_WebAcceptDetermineBillingPage(45),
	    DBLog_Event_State_WebAcceptDone(46),
	    DBLog_Event_State_WebAcceptEditShipping(47),
	    DBLog_Event_State_WebAcceptFatalError(48),
	    DBLog_Event_State_WebAcceptLogin(49),
	    DBLog_Event_State_WebAcceptPrintReceipt(50),
	    DBLog_Event_State_WebAcceptReview(51),
	    DBLog_Event_State_WebAcceptShipping(52),
	    DBLog_Event_State_WebAcceptShippingCheck(53),
	    DBLog_Event_State_WebAcceptSignup(54),
	    DBLog_Event_State_WebAcceptSignupReqCheck(55),
	    DBLog_Event_State_WebAcceptStart(56),
	    DBLog_Event_API_DoCancel(57),
	    DBLog_Event_IPNSimulator(58),
	    DBLog_Event_IPNPostback(59),
		DBLog_Event_State_HandleRrmBuyerOptin(60),
		DBLog_Event_State_NewLogin(61),
		DBLog_Event_State_ExpressCheckoutCare(62),
		DBLog_Event_State_DecideAuthFlow(63),
		DBLog_Event_State_DecideCvvRequired(64),
		DBLog_Event_State_DecideUpsell(65),
		DBLog_Event_State_CheckoutBrowserCompatability(66),
		DBLog_Event_State_OnExCheckPee(67),
		DBLog_Event_State_OnExecStart(68),
		DBLog_Event_State_OnExecChooseLandingPage(69),
		DBLog_Event_Batch(70);
		
	    private final int value;
		private DBLogEventType(int value)
		{
			this.value = value;
		}
		public int getValue() 
		{
			return value;
		}
		public String toString()
		{
			return Integer.toString(value);
		}
	}
	
	public enum DBLogLookupType {
		 DBLog_Lookup_UNKNOWN(0),
	     DBLog_Lookup_AccountNumber(1),
	     DBLog_Lookup_AuthorizationId(2),
	     DBLog_Lookup_BillingAgreementId(3),
	     DBLog_Lookup_BlimpRc(4),                                  // DO NOT USE (not an appropriate lookup type!)
	     DBLog_Lookup_BuyerAccount(5),
	     DBLog_Lookup_CalCorrelationId(6),
	     DBLog_Lookup_Email(7),
	     DBLog_Lookup_InvoiceId(8),
	     DBLog_Lookup_MasspayId(9),
	     DBLog_Lookup_MerchantPullLogId_DO_NOT_REUSE(10),	        //DO NOT USE (not an appropriate lookup type!)
	     DBLog_Lookup_OrderId(11),                                  // GMF order id, not EFT "bank order id"!
	     DBLog_Lookup_PayerId(12),
	     DBLog_Lookup_PaymentFlowId_DO_NOT_REUSE(13),	            //DO NOT USE (not an appropriate lookup type!)
	     DBLog_Lookup_PaymentStatusId_DO_NOT_REUSE(14),	            //DO NOT USE (not an appropriate lookup type!)
	     DBLog_Lookup_PaymentTransId(15),
	     DBLog_Lookup_UnencryAuthId(16),
	     DBLog_Lookup_PpapiRc(17),                                  // DO NOT USE (not an appropriate lookup type!)
	     DBLog_Lookup_ReceiptId(18),
	     DBLog_Lookup_ReceiverEmail(19),
	     DBLog_Lookup_RefundTransactionId(20),
	     DBLog_Lookup_ResultingTxnId(21),
	     DBLog_Lookup_SellerAccount(22),
	     DBLog_Lookup_StartDate(23),                                // DO NOT USE (not an appropriate lookup type!)
	     DBLog_Lookup_Token(24),
	     DBLog_Lookup_TransactionId(25),
	     DBLog_Lookup_CartId_DO_NOT_REUSE(26),			            //DO NOT USE (not an appropriate lookup type!)
	     DBLog_Lookup_EBay_EIAS(27),
	     DBLog_Lookup_EBay_Login(28),
	     DBLog_Lookup_PimpRc(29),                                    // DO NOT USE (not an appropriate lookup type!)
	     DBLog_Lookup_SubjectAccount(30),
	     DBLog_Lookup_BuyerEmail(31),
	     DBLog_Lookup_CCNumHash(32),
	     DBLog_Lookup_CCTransId(33),
	     DBLog_Lookup_ParentTransId_DO_NOT_REUSE(34),		          //DO NOT USE (not an appropriate lookup type!)
	     DBLog_Lookup_AttackCaseId_DO_NOT_REUSE(35),		          //DO NOT USE (not an appropriate lookup type!)
	     DBLog_Lookup_BuyerCaptureTxnId(36),
	     DBLog_Lookup_CounterPartyTransactionId(37),
	     DBLog_Lookup_CounterParty(38),
	     DBLog_Lookup_WalletId(39),			                          // Using it WalletID lookup type
	     DBLog_Lookup_WaxTransactionId_DO_NOT_REUSE(40),		      //DO NOT USE (not an appropriate lookup type!)
	     //Merchant onboarding
	     DBLog_Lookup_Organic_PageId_Email(41),
	     DBLog_Lookup_Partner_PageId_Email(42),
	     
	     DBLog_Lookup_Ebay_BuyerId(43),
	     DBLog_Lookup_DevCentral_EmailId(44),
	     DBLog_Lookup_ActivityId(45),
	     DBLog_Lookup_Ebay_SellerId(46),
	     DBLog_Lookup_MsgSubId(47),
	     DBLog_Lookup_Encrypted_PaymentId(48),
	    
	     DBLog_Lookup_Flowlogging_Id(49),
	     DBLog_Lookup_StoreId(51),
	     DBLog_Lookup_TerminalId(52),
	     DBLog_Lookup_StoreIdAndTerminalId(53),
	     DBLog_Lookup_MerchantId(54),
	     DBLog_Lookup_AcquirerId(55),
	     DBLog_Lookup_MerchantIdandAcquirerId(56);
	     
		private final int value;
		private DBLogLookupType(int value)
		{
			this.value = value;
		}
		public int getValue() 
		{
			return value;
		}
		public String toString()
		{
			return Integer.toString(value);
		}
	}
	
	public enum DBLogDataObjectType {
		OBJECT_TYPE_STRING(0),
		OBJECT_TYPE_MAP(1);
		
		private final int value;
		private DBLogDataObjectType(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}
		public String toString()
		{
			return Integer.toString(value);
		}
	}
	
	public enum DBLogDataType {
		DBLog_Data_UNKNOWN(0),
	    DBLog_Data_Blob(1),
	    DBLog_Data_EventSpecific(2);

		private final int value;
		private DBLogDataType(int value)
		{
			this.value = value;
		}
		public int getValue() 
		{
			return value;
		}
		public String toString()
		{
			return Integer.toString(value);
		}
	}

}