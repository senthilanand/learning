package com.paypal.infra.feel.client;

import com.ebay.kernel.executor.BaseNamedTask;
import com.ebay.kernel.executor.CallableTask;
import com.paypal.infra.feel.domain.IFeelLogEntry;
import com.paypal.infra.util.log.LoggerWrapper;

/**
 * The writeTask is used by FeelServClientImpl to submit a write task to internal executor. It implemented CallableTask
 * interface. It invokes FeelServClientImpl's write method to perform actual write.
 *  
 * @author Java infra team, phan
 *
 */
public class FeelServWriteTask extends BaseNamedTask implements CallableTask<Long> {
	private final FeelServClientImpl clientImpl;
	private final IFeelLogEntry logEntry;
    private static final LoggerWrapper LOGGER = new LoggerWrapper(FeelServWriteTask.class);

	/**
	 * Construct a task with the feelservClientImpl and feelLogEntry.
	 * 
	 * @param feelServClientImpl - the feelclient impl which holds the ssl config and other config info.
	 * @param feelLogEntry  - the log entry to write to feelserv server
	 */
    public FeelServWriteTask(FeelServClientImpl feelServClientImpl, IFeelLogEntry feelLogEntry) {
		clientImpl = feelServClientImpl;
		logEntry = feelLogEntry;
	}

	/**
	 * The overwritten call method to invoke feelClientImpl's writeWithWait() method.
	 */
    public Long call() throws Exception {
		long threadId = Thread.currentThread().getId();
		if (LOGGER.getLogger().isDebugEnabled()) {
			LOGGER.getLogger().debug("TASK feelsrv client thread("+ threadId+") started to write ...");
		}
		
		long startTime = System.currentTimeMillis();
		clientImpl.writeWithWait(logEntry);
		long duration = System.currentTimeMillis() - startTime;

		if (LOGGER.getLogger().isDebugEnabled()) {
			LOGGER.getLogger().debug("TASK feelsrv client thread("+ threadId+") finished writing in "+ duration + " milisecs...");
		}
		return duration;
	}
}