package UI;
import org.testng.Assert;
import org.testng.annotations.Test;

import ca.IRM.selenium.config.TestRetryAnalyzer;


public class failTest {
	
	@Test (groups="testing")
	public void testgoogle() {
		Assert.assertTrue(false);
		System.out.println("Google is working fine");
	}
}
