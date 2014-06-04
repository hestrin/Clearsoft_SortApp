package sortApp;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StartTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testIsFound() {
		String test_string = "aEdf";
		UserSettings userSettings = new UserSettings(Find.App, Sort.AppName , test_string , false);
		Start s = new Start();
		for (int i=0; i<1000; i++){
			boolean result = s.isFound(userSettings, i + test_string+ 2*i, test_string + 3*i);
			assertEquals(true,result) ;
		}
	}

}
