package com.paypal.infra.feel.client;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.paypal.infra.feel.domain.ErrorLogPageInfo;
import com.paypal.infra.feel.domain.FeelErrorLogEntry;
import com.paypal.infra.feel.domain.FeelServConstants;
import com.paypal.infra.feel.domain.MessageEntry;
import com.paypal.infra.feel.domain.FeelServConstants.ErrorVerbosity;

public class FeelServClientMockTest
{
	private static long acctNum = 123456789;

	private FeelServClientMock feelServClientMock = null;

	@BeforeMethod
	public void setup() throws IOException
	{
		feelServClientMock = new FeelServClientMock();
	}

	@Test
	public void testMessageWrite() throws IOException
	{
		FeelErrorLogEntry feelLogEntry = new FeelErrorLogEntry();
		feelLogEntry.setAcctNum(acctNum);
		feelServClientMock.write(feelLogEntry);
		AssertJUnit.assertEquals("Log message not retrieved", feelLogEntry, feelServClientMock.getEntry(feelLogEntry));
	}

	@Test
	public void testConsoleWrite() throws IOException
	{
		FeelServClientMock feelServClientMock = new FeelServClientMock(FeelServClientMock.CONSOLE);
		FeelErrorLogEntry feelLogEntry = new FeelErrorLogEntry();
		feelLogEntry.setAcctNum(acctNum);
		feelServClientMock.write(feelLogEntry);
		AssertJUnit.assertEquals("Log message not retrieved", feelLogEntry, feelServClientMock.getEntry(feelLogEntry));
	}

	@Test
	public void testDuplicateMessageWrite() throws IOException
	{
		FeelErrorLogEntry feelLogEntry = new FeelErrorLogEntry();
		feelLogEntry.setAcctNum(acctNum);
		feelServClientMock.write(feelLogEntry);
		AssertJUnit.assertEquals("Log message not retrieved", feelLogEntry, feelServClientMock.getEntry(feelLogEntry));

		feelServClientMock.write(feelLogEntry);
		AssertJUnit.assertEquals("Log message not retrieved", feelLogEntry, feelServClientMock.getEntry(feelLogEntry));
	}

	
	/**
	 * Make sure maxEntry number of entries are stored.
	 * @throws IOException
	 */
	@Test
	public void testMaxEntries() throws IOException
	{
		testMultipleMessageWrite(feelServClientMock.getMaxEntries());
	}
	
	/**
	 * Make sure oldest entry is discarded when maxEntries hit
	 * @throws IOException
	 */
	@Test(expectedExceptions=java.lang.RuntimeException.class)
	public void testMaxEntriesOverflow() throws IOException
	{
		testMultipleMessageWrite(feelServClientMock.getMaxEntries()+1);
	}
	
	@Test(enabled = false)
	private void testMultipleMessageWrite(int maxEntries) throws IOException
	{
		int numberLogMessagesToGenerate = maxEntries;
		int numberOfMessagesPerLogEntry = 3;

		FeelErrorLogEntry feelLogEntries[] = new FeelErrorLogEntry[numberLogMessagesToGenerate];
		for (int i = 0; i < numberLogMessagesToGenerate; ++i)
		{
			FeelErrorLogEntry feelLogEntry = new FeelErrorLogEntry();
			feelLogEntry.setAcctNum(i);
			feelLogEntry.setBacktrace("backtrace" + i);
			feelLogEntry.setCmd("cmd" + i);
			feelLogEntry.setErrorFlag(i);
			feelLogEntry.setFlags(i);
			feelLogEntry.setIp("10.0.0." + i);
			List<MessageEntry> msgs = new LinkedList<MessageEntry>();
			for (int j = 0; j < numberOfMessagesPerLogEntry; ++j)
			{
				MessageEntry msg = new MessageEntry(MessageEntry.MessageType.CRawMsg,
													"Lovely Error Msg from Sparta Framework test" + i + j,
													ErrorVerbosity.ErrorDetails,
													FeelServConstants.FeelError.LAction_FEEL_System_Error);
				msgs.add(msg);
			}
			feelLogEntry.setMsgs(msgs);
			ErrorLogPageInfo errorLogPageInfo = new ErrorLogPageInfo("xslTemplate" + i,
																	 "maxcodeTemplate" + i,
																	 "title" + i,
																	 "language" + i);

			feelLogEntry.setPage(errorLogPageInfo);
			feelLogEntry.setPrevCmd("prevCmd" + i);
			feelLogEntry.setPrevPage(null);
			feelLogEntry.setPrevScript("prevScript" + 1);
			feelLogEntry.setScript("script" + i);
			feelLogEntry.setSystemInfo("systemInfo" + i);
			feelLogEntry.setVisitorId(i);
			feelLogEntries[i] = feelLogEntry;
			feelServClientMock.write(feelLogEntry);
			AssertJUnit.assertEquals("Log message not retrieved", feelLogEntry, feelServClientMock.getEntry(feelLogEntry));
		}

		// make sure they are all there
		for (int i = 0; i < numberLogMessagesToGenerate; ++i)
		{
			FeelErrorLogEntry foundFeelLogEntry = feelServClientMock.getEntry(feelLogEntries[i]);
			if (null == foundFeelLogEntry)
			{
				System.out.println("ErrorLogEntry not found " + feelLogEntries[i].toString());
				throw new RuntimeException("ErrorLogEntry not found ");
			}
			else if (!foundFeelLogEntry.equals(feelLogEntries[i]))
			{
				System.out.println("ErrorLogEntry mismatch.  Expected:\n" + feelLogEntries[i].toString());
				System.out.println("But found:\n" + foundFeelLogEntry.toString());
				throw new RuntimeException("ErrorLogEntry mismatch");
			}
		}
	}
	
	@Test
	public void testMemoryOnly() throws IOException
	{
		FeelServClientMock feelServClientMock = new FeelServClientMock(FeelServClientMock.MEMORY_ONLY);
		FeelErrorLogEntry feelLogEntry = new FeelErrorLogEntry();
		feelLogEntry.setAcctNum(acctNum);
		feelServClientMock.write(feelLogEntry);
		AssertJUnit.assertEquals("Log message not retrieved", feelLogEntry, feelServClientMock.getEntry(feelLogEntry));
	}

	@Test
	public void testByAcctNum() throws IOException
	{
		FeelServClientMock localFeelServClientMock = new FeelServClientMock(FeelServClientMock.MEMORY_ONLY);
		int numberToInsert = 3;
		for (int i = 0; i<numberToInsert; ++i)
		{
			FeelErrorLogEntry feelLogEntry = new FeelErrorLogEntry();
			feelLogEntry.setAcctNum(acctNum);
			localFeelServClientMock.write(feelLogEntry);
			AssertJUnit.assertEquals("Log message not retrieved", feelLogEntry, localFeelServClientMock.getEntry(feelLogEntry));
		}
		
		for (int i = 0; i<numberToInsert; ++i)
		{
			FeelErrorLogEntry feelLogEntry = new FeelErrorLogEntry();
			feelLogEntry.setAcctNum(acctNum+1);
			localFeelServClientMock.write(feelLogEntry);
			AssertJUnit.assertEquals("Log message not retrieved", feelLogEntry, localFeelServClientMock.getEntry(feelLogEntry));
		}

		FeelErrorLogEntry entries[] = localFeelServClientMock.getEntries(acctNum);
		AssertJUnit.assertEquals("Number of log entries mismatch", 1, entries.length);
	}

	@Test
	public void testMaxFileSize() throws IOException
	{
		String filename = "testMockFeel.log";
		FeelServClientMock localFeelServClientMock = new FeelServClientMock(filename);
		long maxLogFileSizeInBytes = 50L;
		localFeelServClientMock.setMaxLogFileSizeInBytes(maxLogFileSizeInBytes);

		int numberToInsert = 30;
		for (int i = 0; i<numberToInsert; ++i)
		{
			FeelErrorLogEntry feelLogEntry = new FeelErrorLogEntry();
			feelLogEntry.setAcctNum(acctNum+1);
			localFeelServClientMock.write(feelLogEntry);
			AssertJUnit.assertEquals("Log message not retrieved", feelLogEntry, localFeelServClientMock.getEntry(feelLogEntry));
		}
		File file = new File(filename);
		AssertJUnit.assertTrue("Expected test log file: " + filename + " to be normal file", file.isFile());
		long fileSize = file.length();
		AssertJUnit.assertTrue("Maximum log file size condition violated", (fileSize > maxLogFileSizeInBytes));
	}
}