package com.paypal.infra.feel;

import java.util.Properties;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paypal.infra.feel.config.FeelPropertiesProvider;
import com.paypal.infra.feel.domain.FeelErrorLogEntry;

public class JavaFeelServiceTest {
	private static JavaFeelService jfs = null;
	private static FeelErrorLogEntry fle = null;
	
	@BeforeClass
	public static void beforeClass() throws Exception{
		
		//TODO 
		//URL resource = JavaFeelServiceTest.class.getResource("/feelClient.properties");
		
		try {
		Properties props = new Properties();
		FeelPropertiesProvider provider = new FeelPropertiesProvider(props);
		
		
		jfs = new JavaFeelService(provider);
		AssertJUnit.assertTrue(jfs.start());
		fle = FeelClientTestUtil.getErrorLogSample();
		AssertJUnit.assertNotNull(fle);
		}catch (Exception e) {
			System.out.println("EXCEPTION:"+e.getMessage());
		}
	}
	
	@AfterClass
	public static void afterClass(){
		jfs.stop(1L); 
	}
	
	@Test
	public void testJavaFeelServiceWithWriteWithWaitNull() throws Exception{
		jfs.writeWithWait(null);
	}
	
	@Test
	public void testJavaFeelServiceWithStopService() throws Exception{
		try{
			jfs.stop(-1L);
		}catch(IllegalArgumentException iaex){
			AssertJUnit.assertTrue(iaex.getMessage().contains("Timeout must be a positive number."));
		}

	}	
}