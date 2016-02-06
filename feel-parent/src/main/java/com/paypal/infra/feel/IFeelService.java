package com.paypal.infra.feel;

import java.io.IOException;

import com.paypal.infra.feel.domain.IFeelLogEntry;

/**
 * IFeelService is the service to allow app to write feel error or DB log to feelserv.
 * Please refer to initial feelserv java client design doc: <br>
 *  <a href='https://dev.paypal.com/wiki/Sparta/SpartaFeelservIntegration'>https://dev.paypal.com/wiki/Sparta/SpartaFeelservIntegration </a><br>
 *     
 * This version of FeelService provides following abilities:
 * <ol>
 *   <li> Async write with the method write(logEntry). This method uses a interal taskExecutor to manage a fixed-size thread pool
 *   to send log entries asynchronously with other client logic. The method returns once the log entry is en-queue to the thread pool.
 *   Any error happens during the network sending is logged in CAL.  
 *   <li> sync write with the method writeWithWait(logEntry). The original sparta client could use this method
 *   to maintain the existing feel log writing behavior. This method will execute all the network connecting, communication running while keep
 *   client waiting until connection is closed. Any error will be thrown out as IOException. This method also supports Async write.
 * </ol>
 * 
 *  Due to the potential resource allocation of threadpool to support async-write, this service has a few life cycle methods: start, write, stop, etc.
 *  
 *  The best practice is always to invoke start before writing, and to <b>invoke stop before app server shuts down to release thread pool's resources properly</b>.
 *   
 */
public interface IFeelService {

	/**
	 * Start service.
	 * @return boolean to indicate service initialization success or failure
	 */
	public boolean start();
	
	/**
	 * Write the log entry no wait.
	 * @param entry
	 * @throws IOException - problem writing entry; typically cannot connect to feelserv 
	 */
	public abstract void write(IFeelLogEntry entry) throws IOException;

	/**
	 * Write the log entry and wait for the network transmission to complete.
	 * @param entry
	 * @throws IOException - problem writing entry; typically cannot connect to feelserv 
	 */
	public abstract void writeWithWait(IFeelLogEntry entry) throws IOException;

	/**
	 * stop the service with a specified waiting time period
	 * @param timeoutInSecs - the maximum waiting time before the service is shutdown by force. If no log entry is in queue to send out, the method should 
	 * return much earlier than the timeout period.
	 */
	public void stop(long timeoutInSecs);
	
	/**
	 * stop the service with a default waiting time period
	 */
	public void stop();
}