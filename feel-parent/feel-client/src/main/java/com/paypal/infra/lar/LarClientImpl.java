package com.paypal.infra.lar;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.ebay.kernel.cal.CalMessageHelper;
import com.ebay.kernel.calwrapper.CalEventHelper;
import com.ebay.kernel.calwrapper.CalTransaction;
import com.ebay.kernel.calwrapper.CalTransactionFactory;
import com.ebay.kernel.calwrapper.CalTransactionHelper;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.paypal.asynapins.SetAsynEventRequest;
import com.paypal.infra.feel.config.FeelPropertiesProvider;
import com.paypal.vo.ValueObject;
import com.paypal.vo.serialization.Formats;
import com.paypal.vo.serialization.Serializer;
import com.paypal.vo.serialization.UniversalSerializer;

/**
* This class has been customized for LAR publishing.
* It extends Client.java and implements LarClient interface for LAR customizations. 
* This custom class uses NCLarClientTransport implementation for issuing service calls to LarProxy.
* This class reuses utility methods and configuration framework from Client.java.
* 
* <p>
* This is a thread safe class. Thread safety is achieved by making the class
* immutable, and using local variables in the methods, in order to prevent
* side affects. This is a lock free class - No explicit or implicit locking is
* used.
* 
* @author DL-PP-JavaInfra
*/


final class LarClientImpl implements LarClient {
	
	private final Socket m_sock = new Socket();
	/* this will go to config */
	private  int timeout=3000;
	private String configuredHost="127.0.0.1";
	private int configuredPort=10983;
	private static final Logger LOGGER = Logger.getInstance(LarClientImpl.class);
	
	protected LarClientImpl(FeelPropertiesProvider provider)	throws Exception {
		//initialize the parameters  - TODO
	}

	/**
	 * This method will do the following:
  	 * Create SetAsynEventRequest object (class auto-generated from Asyn.sdl).
	 * Fill the member values (correlation id, event name and input payload from application).
	 * Get an instance of NCLarClientTransportFactory.
	 * Connects to LAR Proxy using 'NCHttpClient' (HTTP) and transports the serialized data.
	 * 
	 * @param eventName - Event name/Operation name
	 * @param asfRequestVO - ASF Request VO
	 * , ASFRequest<? extends ValueObject> asfRequestVO
	 */
	public void sendLARRequest(String eventName , ValueObject valueObj) {
		if(valueObj == null)
			throw new IllegalArgumentException("VO cannot be null");
		if(eventName == null || eventName.trim().length() == 0)
			throw new IllegalArgumentException("eventName cannot be null or empty string");
		byte[] requestBytes = null;
		final String calTrxName = eventName ;  // like c++
		final CalTransaction calTransCall = CalTransactionFactory.create("ASYN_DISPATCH");
		calTransCall.setName(calTrxName);

		Throwable exception = null;
		Socket socket = new Socket();
	
		try {
			String correlationId = getCorrelationId();
			SetAsynEventRequest asynEventRequest = new SetAsynEventRequest();

			asynEventRequest.setCorrelationID(correlationId);
			asynEventRequest.setOperation(eventName);
			asynEventRequest.setDataVO(valueObj); 
			

			calTransCall.addData("corr_id_", correlationId);
			
//			RequestVO requestVO = new RequestVO();
//			requestVO.setClientVersion(getVersion());
//			requestVO.setOperation(eventName);
//			requestVO.setService(getService());
//			requestVO.setRequestedVersion("1.0");
			
			if(LOGGER.isDebugEnabled()){
				LOGGER.log(LogLevel.DEBUG ,"correlationId:"+correlationId);
				LOGGER.log(LogLevel.DEBUG, "getOperationName():"+eventName);
			}
			
			Serializer ser = buildSerializer();  /* get inside the method  I have hard -coded compressed binary , are we using other encoding std , please look at the serialize method of universal serializer */
			Map<String, String> requestProperties = new HashMap<String, String>();
			requestProperties.put("Content-Encoding", ser.getContentType()); //COMPRESSEDBINARY
			
			
			if(LOGGER.isDebugEnabled()){
				LOGGER.log(LogLevel.DEBUG ,"Content-Encoding:"+ser.getContentType());
			}

			OutputStream os =socket.getOutputStream();
			ser.serialize(asynEventRequest, os);
			
			//----------------- TEST CODE
			String serviceName = eventName;
			//String contentLength = 

			try {

				socket.connect(new InetSocketAddress(configuredHost, configuredPort));

				BufferedOutputStream bf = new BufferedOutputStream(socket.getOutputStream());
				String path = "POST /relaydasf/1.0/" + serviceName
						+ " HTTP/1.1\r\n";
				// send headers
				byte[] data;//TODO
				bf.write(path.getBytes());
				bf.write("Correlation-Id: 1454442053931-19551\r\n".getBytes());
				bf.write("Content-Encoding: COMPRESSEDBINARY\r\n".getBytes());
				bf.write("Content-Length: 389\r\n".getBytes());
				bf.write("\r\n".getBytes());
				// set body
				//bf.write(data);

				bf.flush();
				bf.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//-----------------

			
			//RequestContext reqCtx = asfRequestVO.getRequestContext();//??
			//TransportRequestContext ctx = new TransportRequestContext(requestProperties, this.composeURL(requestVO));
			//transport = getTransportFactory().acquireTransport();
			//OutputStream os = transport.startRequest(ctx);
			 // if ( ! m_sock.isConnected() ){
			/* 1)To do set Http headers 2) and serialize */
			
			 socket.connect(new InetSocketAddress(configuredHost ,configuredPort));
			                 //
			 // }
			
			calTransCall.setStatus(CalMessageHelper.ZERO);			
		} 
		catch (Throwable larClientException) {
//			exception = larClientException;
//			LOGGER.log(LogLevel.ERROR, "Exception for Lar Request:"
//					+ getService() + ":" + eventName,
//					exception);
//			CalEventHelper.writeException("SENDLAR", larClientException);
//			calTransCall.setStatus("2.LARCLIENT.INTERNAL.ERR_SENDLARREQUEST_FAILED");
//			throwClientException(eventName, exception, null);
		} 
		finally {
				try {
					//getTransportFactory().releaseTransport(transport, exception);
					socket.close();
				} catch (Exception e) {
					// There is not much we can do here.
					CalEventHelper.writeException("LAR_TRANSPORT_RELEASE", e);
				}
			calTransCall.completed();
		}
	}
	
	/**
	 * Constructing the POST URI as per LAR protocol
	 */
//	protected  String composeURL(RequestVO req) {
//		StringBuilder urlBuffer = new StringBuilder();
//		urlBuffer.append(URL_SEPARATOR);
//		urlBuffer.append(req.getService());
//		urlBuffer.append(URL_SEPARATOR);
//		urlBuffer.append(req.getClientVersion());
//		urlBuffer.append(URL_SEPARATOR);
//		urlBuffer.append(req.getOperation());
//		return urlBuffer.toString();
//	}
	public static String getCorrelationId()
	{
		CalTransaction topTransaction = CalTransactionHelper.getTopTransaction();
		if ( topTransaction == null ) return null;
		return topTransaction.getCorrelationId();
	}
	
	protected UniversalSerializer buildSerializer() {
		Formats format = Formats.BINARY;
		boolean compression = false;
		boolean encoding = false;
		boolean universalHeader = false;
		//String serialization = "format|compression|encoding|universalHeader"; // format|compression|encoding|universalHeader
		String serialization = Formats.COMPRESSEDBINARY.name(); // format|compression|encoding|universalHeader
		StringTokenizer tokens = new StringTokenizer(serialization, "|");
		if (tokens.hasMoreTokens()) {
			String t = tokens.nextToken();
			format = Formats.valueOf(t.toUpperCase());
			if (tokens.hasMoreTokens()) {
				t = tokens.nextToken();
				compression = Boolean.valueOf(t);
			}
			if (tokens.hasMoreTokens()) {
				t = tokens.nextToken();
				encoding = Boolean.valueOf(t);
			}
			if (tokens.hasMoreTokens()) {
				t = tokens.nextToken();
				universalHeader = Boolean.valueOf(t);
			}
		}
		return new UniversalSerializer(format, encoding, compression, universalHeader);
	}
	

//private void write(OutputStream os, byte[] bytes) throws IOException {
//    // Write out our bytes
//    os.write(bytes);
//    os.flush();
//}

}
