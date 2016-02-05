package com.paypal.infra.feel.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ebay.kernel.logger.LogLevel;
import com.paypal.infra.feel.domain.FeelErrorLogEntry;
import com.paypal.infra.feel.domain.IFeelLogEntry;
import com.paypal.infra.util.exception.ExceptionUtils;
import com.paypal.infra.util.log.LoggerWrapper;

/**
 * Mock feelserv client.
 * <p>
 * Can be used to mimic functionality of FeelServClientImpl. This allows a developer to see feelserv messages without requiring a feelserv server
 * running. The output may be sent to a file, to the console or kept in memory only.
 * <p>
 * FeelServClientMock is intended to allow developers to write unit tests in which feel entries can be programmatically examined. The client can be
 * configured to write to the console by invoking the constructor with CONSOLE as the file name. Using MEMORY_ONLY will only store maxEntries number
 * of entries, discarding the oldest entry when required.
 * 
 */
public class FeelServClientMock implements FeelServClient
{
	private LoggerWrapper logger = new LoggerWrapper(FeelServClientMock.class);
	private Map<Integer, FeelErrorLogEntry> map = null;
	private String filename = "mockfeel.log";
	public static final String CONSOLE = "CONSOLE";
	public static final String MEMORY_ONLY = "MEMORY_ONLY";
	private int defaultMaxEntries = 10;
	public int maxEntries = defaultMaxEntries;
	private long maxLogFileSizeInBytes = 100000;

	
	public FeelServClientMock(String filename)
	{
		super();
		this.filename = filename;
		init();
	}

	public FeelServClientMock()
	{
		super();
		init();
	}

	public FeelServClientMock(String filename, int maxEntries)
	{
		super();
		this.filename = filename;
		this.maxEntries = maxEntries;
		init();
	}

	private void init()
	{
		LnkdHshMap<Integer, FeelErrorLogEntry> lnkdHshMap = new LnkdHshMap<Integer, FeelErrorLogEntry>(maxEntries);
		map = lnkdHshMap;
		logger.getLogger().log(LogLevel.WARN, "Mock FEEL has been configured");

		if (filename.equals(MEMORY_ONLY))
		{
			return;
		}

		try
		{
			BufferedWriter out = getBufferedWriter(false); // append is false
			out.write("FeelServClientMock\n");
			out.flush();
			releaseBufferedWriter(out);
		}
		catch (Exception e)
		{// Catch exception if any
			logger.getLogger().log(LogLevel.ERROR, "FeelServClientMock initialization error", e);
		}
	}

	public long getMaxLogFileSizeInBytes()
	{
		return maxLogFileSizeInBytes;
	}

	public void setMaxLogFileSizeInBytes(long maxLogFileSizeInBytes)
	{
		this.maxLogFileSizeInBytes = maxLogFileSizeInBytes;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		ExceptionUtils.throwIfNull(filename, "filename");
		this.filename = filename;
	}

	public int getMaxEntries()
	{
		return maxEntries;
	}

	public void setMaxEntries(int maxEntries)
	{
		this.maxEntries = maxEntries;
	}
	//TODO
	//@Override
	public void write(IFeelLogEntry feelLogEntry) throws IOException
	{

		FeelErrorLogEntry feelErrLogEntry = (FeelErrorLogEntry) feelLogEntry;
		map.put(feelErrLogEntry.hashCode(), feelErrLogEntry);

		if (!filename.equals(MEMORY_ONLY))
		{
			BufferedWriter out = getBufferedWriter(true);
			out.write(feelLogEntry.toString() + "\n");
			releaseBufferedWriter(out);
		}
	}

	//Will return null if filename is MEMORY_ONLY
	private BufferedWriter getBufferedWriter(boolean append) throws IOException
	{
		BufferedWriter out = null;
		if (filename.equals(CONSOLE))
		{
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 512);
		}
		else if (!filename.equals(MEMORY_ONLY))
		{
			// Create file
			out = new BufferedWriter(new FileWriter(filename, append));

			//truncate the file if it gets too big
			File file = new File(filename);
			if (file.isFile())
			{
				long filesize = file.length();
				if (filesize > maxLogFileSizeInBytes)
				{
					out = new BufferedWriter(new FileWriter(filename, false)); //truncate file
					out.write("FeelServClientMock wrapped\n");
					out.flush();
				}
			}
		}

		return out;
	}

	private void releaseBufferedWriter(BufferedWriter out) throws IOException
	{
		out.flush();
		if (!filename.equals(CONSOLE) && (!filename.equals(MEMORY_ONLY)))
		{
			// Close the output stream
			out.close();
		}

	}

	public FeelErrorLogEntry getEntry(FeelErrorLogEntry feelLogEntry)
	{
		if (null == feelLogEntry)
		{
			return null;
		}
		return map.get(feelLogEntry.hashCode());
	}

	public int getNumberOfEntries()
	{
		return map.size();
	}

	public FeelErrorLogEntry[] getEntries(long acctNum)
	{
		ArrayList<FeelErrorLogEntry> list = new ArrayList<FeelErrorLogEntry>();
		for (FeelErrorLogEntry feelLogEntry : map.values())
		{
			if (null != feelLogEntry && feelLogEntry.getAcctNum() == acctNum)
			{
				list.add(feelLogEntry);
			}
		}
		FeelErrorLogEntry entries[] = new FeelErrorLogEntry[list.size()];
		list.toArray(entries);
		return entries;
	}

	
	/**
	 * Contains the mock log entries in a bounded memory size defined by maxSize
	 *
	 * @param <K>
	 * @param <V>
	 */
	public class LnkdHshMap<K, V> extends LinkedHashMap<K, V>
	{
		private static final long serialVersionUID = 1L;
		private int maxSize = 10;

		public LnkdHshMap()
		{
			super(defaultMaxEntries);

		}

		public LnkdHshMap(int maxSize)
		{
			super(maxSize);
			this.maxSize = maxSize;
		}

		protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
		{
			return size() > getMaxSize();
		}

		public int getMaxSize()
		{
			return maxSize;
		}

		public void setMaxSize(int maxSize)
		{
			this.maxSize = maxSize;
		}
	}
        //TODO
		//@Override
	public void writeWithWait(IFeelLogEntry entry) throws IOException {
		// TODO Auto-generated method stub
		
	}
}