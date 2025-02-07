package ca.IRM.selenium.UserTypeAccess;

import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ca.IRM.selenium.components.NavBar;
import ca.IRM.selenium.pages.Notification;
import ca.IRM.selenium.pages.User;
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
	public void createIncidentReportNot() {
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to NOT within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("BROCKVILLE JAIL - ADULT (Institution)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickNext();
		utils.duplicatePopUpCheck();

//		Verify access denied (cannot make incident reports NOT within user location)
		utils.accessDeniedVerify();
	}
}
