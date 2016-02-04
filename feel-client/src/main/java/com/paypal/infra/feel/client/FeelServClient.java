package com.paypal.infra.feel.client;

import java.io.IOException;

import com.paypal.infra.feel.domain.IFeelLogEntry;

/**
 * Interface to write to Front-End Error Log (FEEL)
 * 
 * @see com.paypal.infra.feel.FeelServClient
 * @see com.paypal.infra.feel.domain.FeelServConstants
 */
public interface FeelServClient
{

	/**
	 * Write the log entry no wait.
	 * @param entry - the feel log entry written to feelserv
	 * @throws IOException - problem writing entry; typically cannot connect to feelserv 
	 */
	public abstract void write(IFeelLogEntry entry) throws IOException;

	/**
	 * Write the log entry and wait for the network transmission to complete.
	 * @param entry - the feel log entry written to feelserv
	 * @throws IOException - problem writing entry; typically cannot connect to feelserv 
	 */
	public abstract void writeWithWait(IFeelLogEntry entry) throws IOException;

}