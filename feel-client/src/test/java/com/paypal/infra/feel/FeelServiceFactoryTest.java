package com.paypal.infra.feel;

import java.util.Properties;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ebay.kernel.initialization.InitializationException;
import com.paypal.infra.feel.config.FeelPropertiesProvider;

public class FeelServiceFactoryTest {
	private static IFeelService ifs = null;
	@BeforeClass
	public static void beforeClass() throws Exception{
		
		Properties props = new Properties();
		FeelPropertiesProvider provider = new FeelPropertiesProvider(props);
		
		FeelServiceFactory.initFeelService(provider);
		ifs = FeelServiceFactory.getFeelService();
		AssertJUnit.assertTrue(ifs.start());
	}
	
	@AfterClass
	public static void afterClass(){
		ifs.stop(1L);
		FeelServiceFactory.reset();
		AssertJUnit.assertFalse(FeelServiceFactory.isLoaded());
	}
	
	@Test
	public void testFeelServiceFactorySetAndGetService() throws Exception{
		try{
			FeelServiceFactory.initFeelService(null);
			AssertJUnit.assertFalse(FeelServiceFactory.isLoaded());
			FeelServiceFactory.getFeelService();
			AssertJUnit.fail();
		}catch(InitializationException iex){
			AssertJUnit.assertTrue(iex.getMessage().contains("The FeelServiceFactory is already initialized."));
		}
	}
	
	@Test 
	public void testFeelServiceFactoryIsLoaded() throws Exception{
		AssertJUnit.assertTrue(FeelServiceFactory.isLoaded());
	}
}