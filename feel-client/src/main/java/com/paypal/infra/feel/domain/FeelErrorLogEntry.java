package com.paypal.infra.feel.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.paypal.infra.feel.domain.FeelServConstants.ErrorVerbosity;
import com.paypal.infra.feel.domain.FeelServConstants.FeelError;

/**
 * Encapsulates data to be written to "Front-End Error Logging" (FEEL) database wfe_err_log table. </p> 
 * Note that it is possible that certain classes of system errors may
 * have no account number or visitor ID associated with them. These errors will never be seen in Admin Tools (since there is no way to associate them
 * with a user), but they will be logged anyway. In the future, Omaha may wish to include them in aggregate reports.
 * 
 * @see com.paypal.infra.feel.FeelServClient
 * @see com.paypal.infra.feel.domain.FeelServConstants
 */
public class FeelErrorLogEntry implements IFeelLogEntry
{
	public static final String TOKEN_DELIM = "\b";
	public static final String MSG_INDICATOR = "#";
	public static final String BACKTRACE_INDICATOR = "\\a";
	public static final String SYSTEM_INFO_INDICATOR = "\f";
	public static final long FEEL_USER_ERROR_FLAG = 0x00000001;
	public static final long FEEL_SYSTEM_ERROR_FLAG = 0x00000002;

	/**
	 * User account number if available.
	 */
	private long acctNum;
	private long flags;
	private long visitorId;

	/**
	 * Information corresponding to the current page that user is visiting.
	 */
	private ErrorLogPageInfo page;

	/**
	 * Information corresponding to the previous page that user had visited.
	 */
	private ErrorLogPageInfo prevPage;

	/**
	 * An array of error messages that user saw on the site.
	 * 
	 * @see MessageEntry An instance of FeelLogEntry would usually contain only User Errors or System Errors Occasionally it may have both
	 */
	private final List<MessageEntry> msgs = new LinkedList<MessageEntry>();

	private String ip; // This is the IP associated with the user
	private String cmd;
	private String prevCmd;
	private String script;
	private String prevScript;

	/**
	 * Stores the hexadecimal backtrace symbols. Only system errors will have a backtrace. So if the ErrorLogEntry instance has only User Errors this
	 * will be empty
	 */
	private String backtrace;

	/**
	 * Stores information about the system on which the application was executing
	 */
	private String systemInfo;

	/**
	 * Indicates if the ErrorLogEntry contains User Errors, System Errors or both
	 */
	private long errorFlag;

	public FeelErrorLogEntry()
	{

	}

	public FeelErrorLogEntry(String msg)
	{
		if (null == msg)
		{
			return;
		}
		MessageEntry messageEntry = new MessageEntry(MessageEntry.MessageType.CRawMsg, msg, ErrorVerbosity.ErrorDetails, FeelError.LAction_FEEL_System_Error);
		msgs.add(messageEntry);
	}

	public long getAcctNum()
	{
		return acctNum;
	}

	/**
	 * Set the account number of the user that is logged in; otherwise null.
	 * 
	 * @param acctNum
	 */
	public void setAcctNum(long acctNum)
	{
		this.acctNum = acctNum;
	}

	public long getFlags()
	{
		return flags;
	}

	/**
	 * Type of the error, specifically refers to following DBLog entry actions at the time of this writing.
	 * 
	 * <pre>
	 * LAction_FEEL_User_Error
	 * LAction_FEEL_System_Error
	 * </pre>
	 * 
	 * @see FeelServConstants.FeelError
	 */
	public void setFlags(long flags)
	{
		this.flags = flags;
	}

	public long getVisitorId()
	{
		return visitorId;
	}

	/**
	 * This is the visitor ID associated with the user. This value should under normal circumstances be available even if the user isn't logged into
	 * the site.
	 * 
	 * @param visitorId
	 */
	public void setVisitorId(long visitorId)
	{
		this.visitorId = visitorId;
	}

	public ErrorLogPageInfo getPage()
	{
		return page;
	}

	public void setPage(ErrorLogPageInfo page)
	{
		this.page = page;
	}

	public ErrorLogPageInfo getPrevPage()
	{
		return prevPage;
	}

	public void setPrevPage(ErrorLogPageInfo prevPage)
	{
		this.prevPage = prevPage;
	}

	public List<MessageEntry> getMsgs()
	{
		return Collections.unmodifiableList(msgs);
	}

	public void setMsgs(List<MessageEntry> msgs)
	{
		if (msgs != null) {
			this.msgs.addAll(msgs);
		}
	}

	public String getIp()
	{
		return ip;
	}

	/**
	 * This is the IP associated with the user (more specifically, with the source of the HTTP request).
	 * 
	 * @param ip
	 */
	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getCmd()
	{
		return cmd;
	}

	/**
	 * Command requested to be executed.
	 * 
	 * @param cmd
	 */
	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}

	public String getPrevCmd()
	{
		return prevCmd;
	}

	/**
	 * Previous command that was requested to be executed.
	 * 
	 * @param prevCmd
	 */
	public void setPrevCmd(String prevCmd)
	{
		this.prevCmd = prevCmd;
	}

	public String getScript()
	{
		return script;
	}

	/**
	 * CGI program name requested to be executed.
	 * 
	 * @param script
	 */
	public void setScript(String script)
	{
		this.script = script;
	}

	public String getPrevScript()
	{
		return prevScript;
	}

	/**
	 * CGI Program name that was requested for the previous page.
	 * 
	 * @param prevScript
	 */
	public void setPrevScript(String prevScript)
	{
		this.prevScript = prevScript;
	}

	public String getBacktrace()
	{
		return backtrace;
	}

	public void setBacktrace(String backtrace)
	{
		this.backtrace = backtrace;
	}

	public String getSystemInfo()
	{
		return systemInfo;
	}

	public void setSystemInfo(String systemInfo)
	{
		this.systemInfo = systemInfo;
	}

	public long getErrorFlag()
	{
		return errorFlag;
	}

	public void setErrorFlag(long errorFlag)
	{
		this.errorFlag = errorFlag;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("acctNum: ").append(acctNum);
		sb.append(" flags: ").append(flags);
		sb.append(" visitorId: ").append(visitorId);
		if (null != page)
		{
			sb.append("\n\tpage:\n\t[").append(page.toString()).append("]");
		}
		else
		{
			sb.append("\n\tpage:\n\t[null]");
		}

		if (null != prevPage)
		{
			sb.append("\n\tprevPage:\n\t[").append(prevPage.toString()).append("]");
		}
		else
		{
			sb.append("\n\tprevPage:\n\t[null]");
		}

		if (null != msgs)
		{
			sb.append("\n\tmsgs: ");
			for (MessageEntry messageEntry : msgs)
			{
				sb.append("\n\t\tmsg:\n\t\t[").append(messageEntry.toString()).append("]");
			}
		}
		else
		{
			sb.append("\n\t\tmsgs:\n\t\t[null]");
		}
		sb.append("\n\t");
		
		sb.append(" ip: ").append((ip == null ? "null" : ip));
		sb.append(" cmd: ").append((cmd == null ? "null" : cmd));
		sb.append(" prevCmd: ").append((prevCmd == null ? "null" : prevCmd));
		sb.append(" script: ").append((script == null ? "null" : script));
		sb.append(" prevScript: ").append((prevScript == null ? "null" : prevScript));
		sb.append(" backtrace: ").append((backtrace == null ? "null" : backtrace));
		sb.append(" systemInfo: ").append((systemInfo == null ? "null" : systemInfo));
		sb.append(" errorFlag: ").append(errorFlag);
		return sb.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (acctNum ^ (acctNum >>> 32));
		result = prime * result + ((backtrace == null) ? 0 : backtrace.hashCode());
		result = prime * result + ((cmd == null) ? 0 : cmd.hashCode());
		result = prime * result + (int) (errorFlag ^ (errorFlag >>> 32));
		result = prime * result + (int) (flags ^ (flags >>> 32));
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((msgs == null) ? 0 : msgs.hashCode());
		result = prime * result + ((page == null) ? 0 : page.hashCode());
		result = prime * result + ((prevCmd == null) ? 0 : prevCmd.hashCode());
		result = prime * result + ((prevPage == null) ? 0 : prevPage.hashCode());
		result = prime * result + ((prevScript == null) ? 0 : prevScript.hashCode());
		result = prime * result + ((script == null) ? 0 : script.hashCode());
		result = prime * result + ((systemInfo == null) ? 0 : systemInfo.hashCode());
		result = prime * result + (int) (visitorId ^ (visitorId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FeelErrorLogEntry))
			return false;
		FeelErrorLogEntry other = (FeelErrorLogEntry) obj;
		if (acctNum != other.acctNum)
			return false;
		if (backtrace == null)
		{
			if (other.backtrace != null)
				return false;
		}
		else if (!backtrace.equals(other.backtrace))
			return false;
		if (cmd == null)
		{
			if (other.cmd != null)
				return false;
		}
		else if (!cmd.equals(other.cmd))
			return false;
		if (errorFlag != other.errorFlag)
			return false;
		if (flags != other.flags)
			return false;
		if (ip == null)
		{
			if (other.ip != null)
				return false;
		}
		else if (!ip.equals(other.ip))
			return false;
		if (msgs == null)
		{
			if (other.msgs != null)
				return false;
		}
		else if (!msgs.equals(other.msgs))
			return false;
		if (page == null)
		{
			if (other.page != null)
				return false;
		}
		else if (!page.equals(other.page))
			return false;
		if (prevCmd == null)
		{
			if (other.prevCmd != null)
				return false;
		}
		else if (!prevCmd.equals(other.prevCmd))
			return false;
		if (prevPage == null)
		{
			if (other.prevPage != null)
				return false;
		}
		else if (!prevPage.equals(other.prevPage))
			return false;
		if (prevScript == null)
		{
			if (other.prevScript != null)
				return false;
		}
		else if (!prevScript.equals(other.prevScript))
			return false;
		if (script == null)
		{
			if (other.script != null)
				return false;
		}
		else if (!script.equals(other.script))
			return false;
		if (systemInfo == null)
		{
			if (other.systemInfo != null)
				return false;
		}
		else if (!systemInfo.equals(other.systemInfo))
			return false;
		if (visitorId != other.visitorId)
			return false;
		return true;
	}
	
}