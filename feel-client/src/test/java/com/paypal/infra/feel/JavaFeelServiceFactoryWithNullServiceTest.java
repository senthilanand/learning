package com.paypal.infra.feel;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.io.IOException;

import com.paypal.infra.feel.FeelServiceFactory.NullFeelService;
import com.paypal.infra.feel.domain.FeelErrorLogEntry;

public class JavaFeelServiceFactoryWithNullServiceTest {

	private static IFeelService infs = null;
	private static FeelErrorLogEntry fle = null;
	
	@BeforeClass
	public static void beforeClass() throws Exception{
		infs = FeelServiceFactory.getFeelService();
		AssertJUnit.assertNotNull(infs);
		fle = FeelClientTestUtil.getErrorLogSample();
		AssertJUnit.assertNotNull(fle);
	}
	
	@AfterClass
	public static void afterClass(){
		infs.stop(1L);
		FeelServiceFactory.reset();
	}
	
	@Test 
	public void testFeelServiceFactoryWithNullFeelService() throws IOException{
		AssertJUnit.assertTrue(infs.start());
		infs.write(fle);
	}
	
	@Test 
	public void testFeelServiceFactoryWithNullFeelService1() throws IOException{
		AssertJUnit.assertTrue(infs.start());
		infs.writeWithWait(fle);
	}
	
	@Test 
	public void testFeelServiceFactoryIsLoaded() throws Exception{
		AssertJUnit.assertFalse(FeelServiceFactory.isLoaded());
	}
	
	@Test
	public void testFeelServiceFactoryNullService() throws Exception{
		NullFeelService nfs = FeelServiceFactory.NullFeelService.class.newInstance();
		nfs.stop();
	}
	
}