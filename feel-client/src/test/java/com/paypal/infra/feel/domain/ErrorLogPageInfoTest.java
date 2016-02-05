package com.paypal.infra.feel.domain;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertNotNull;
import org.testng.AssertJUnit;
import com.paypal.infra.feel.domain.ErrorLogPageInfo;


public class ErrorLogPageInfoTest {

	private String language = "English";
	private String maxCodeTemplate = "maxCodeTemplate";
	private String title = "errorLogPageInfo";
	private String xslTemplate = "xslTemplate";
	
	@Test
	public void testErrorLogPageInfoWithCustomValues1(){
		ErrorLogPageInfo elpi = new ErrorLogPageInfo(xslTemplate, maxCodeTemplate, title, language);
		AssertJUnit.assertEquals(language, elpi.getLanguage());
		AssertJUnit.assertEquals(maxCodeTemplate, elpi.getMaxcodeTemplate());
		AssertJUnit.assertEquals(title, elpi.getTitle());
		AssertJUnit.assertEquals(xslTemplate, elpi.getXslTemplate());
		AssertJUnit.assertTrue(elpi.is_valid());
	}
	
	@Test
		public void testErrorLogPageInfoWithToString(){
		ErrorLogPageInfo elpi = new ErrorLogPageInfo(xslTemplate, maxCodeTemplate, title, language);
		ErrorLogPageInfo elpi1 = new ErrorLogPageInfo(xslTemplate, maxCodeTemplate, title, language);
		AssertJUnit.assertEquals("xslTemplate: " + xslTemplate + " maxcodeTemplate: " + maxCodeTemplate + " title: " + title
				+ " language: " + language, elpi.toString());
		assertNotNull(elpi.hashCode());
		AssertJUnit.assertTrue(elpi.equals(elpi1));
	}
}