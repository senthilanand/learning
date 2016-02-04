package com.paypal.infra.feel.domain;

import org.apache.commons.lang.StringUtils;

/**
* A type that encapsulates information that we store in wfe_err_page_info
* table.
*/

public class ErrorLogPageInfo 
{
	private	final String xslTemplate;
	private	final String maxcodeTemplate;
	private	final String title;
	private	final String language;

	public ErrorLogPageInfo(String xslTemplate,
			String maxcodeTemplate, String title, String language) 
	{
		this.xslTemplate = xslTemplate;
		this.maxcodeTemplate = maxcodeTemplate;
		this.title = title;
		this.language = language;
	}	

	public String getXslTemplate() {
		return xslTemplate;
	}

	public String getMaxcodeTemplate() {
		return maxcodeTemplate;
	}
	
	public String getTitle() {
		return title;
	}

	public String getLanguage() {
		return language;
	}

   public boolean is_valid() {
		// We treat the entry as a valid to create in the DB if we have atleast
		// one of the fields not empty.
		return StringUtils.isNotEmpty(xslTemplate)
				|| StringUtils.isNotEmpty(title);
	}

   @Override
   public String toString()
   {
	   StringBuilder sb = new StringBuilder();
	   sb.append("xslTemplate: ").append((xslTemplate == null ? "null" : xslTemplate));
	   sb.append(" maxcodeTemplate: ").append((maxcodeTemplate == null ? "null" : maxcodeTemplate));
	   sb.append(" title: ").append((title == null ? "null" : title));
	   sb.append(" language: ").append((language == null ? "null" : language));
	   return sb.toString();
   }

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((maxcodeTemplate == null) ? 0 : maxcodeTemplate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((xslTemplate == null) ? 0 : xslTemplate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ErrorLogPageInfo))
			return false;
		ErrorLogPageInfo other = (ErrorLogPageInfo) obj;
		if (language == null)
		{
			if (other.language != null)
				return false;
		}
		else if (!language.equals(other.language))
			return false;
		
		if (maxcodeTemplate == null)
		{
			if (other.maxcodeTemplate != null)
				return false;
		}
		else if (!maxcodeTemplate.equals(other.maxcodeTemplate))
			return false;
		
		if (title == null)
		{
			if (other.title != null)
				return false;
		}
		else if (!title.equals(other.title))
			return false;
		
		if (xslTemplate == null)
		{
			if (other.xslTemplate != null)
				return false;
		}
		else if (!xslTemplate.equals(other.xslTemplate))
			return false;
		return true;
	}

}