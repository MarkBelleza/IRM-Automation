package ca.IRM.selenium.UserTypeAccess;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ca.IRM.selenium.components.NavBar;
import ca.IRM.selenium.pages.Notification;
import ca.IRM.selenium.pages.User;
import ca.IRM.selenium.utils.ReadXLSData;
import ca.IRM.selenium.utils.WebUtils;

public class AdminToTheSuperintendent3 {
	private NavBar nav;
	private Notification notificationFields;
	private User user;
	
	private EdgeDriver driver = new EdgeDriver();
	private WebUtils utils = new WebUtils(driver);
	
	
	
	@BeforeTest(groups="testing")
	public void setUpApplication() {
//		Setup IRM application
//		WebDriverManager.edgedriver().setup();
		nav = new NavBar(driver);
		notificationFields = new Notification(driver);
		user = new User(driver);
		
		WebUtils.setUpIrmPage(driver);
		
//		Set user to Staff Sergeant and ALGOMA
		user.changeUserType(user.admin, user.algo);
		System.out.println("Before Test");		
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
		driver.navigate().to("http://jtsazistcirm01/");
		user.changeUserType(user.staff, user.algo);
		driver.quit();
		System.out.println("After Test");
	}
	
	
//	TestCase ID: TC0010
	@Test(groups="testing")
	public void createIncidentReportNot() throws EncryptedDocumentException, IOException {
		ReadXLSData loc = new ReadXLSData();
		String[] locations =loc.getData("Locations");
		
		nav.createNewReport();
		
//		Verify no locations are visible in the Locations drop down besides User location
		notificationFields.clickLocationDropDown();
		notificationFields.verifyLocationInDropDown("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)");
		
		for (String institution: locations) {
			if (!institution.equals("ALGOMA TREATMENT & REMAND CTR-ADULT")) {				
				notificationFields.verifyLocationNotInDropDown(institution + " (Institution)");
			}
		}
	}
}
