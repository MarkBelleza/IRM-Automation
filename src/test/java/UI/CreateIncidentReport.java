package UI;

import java.time.Duration;

import ca.IRM.selenium.components.DateTimeUI;
import ca.IRM.selenium.components.NavBar;
import ca.IRM.selenium.pages.DetailsAndCircumstances;
import ca.IRM.selenium.pages.IncidentTypeSelection;
import ca.IRM.selenium.pages.Involved;
import ca.IRM.selenium.pages.IsMediaAware;
import ca.IRM.selenium.pages.Notification;
import ca.IRM.selenium.pages.PoliceContacted;
import ca.IRM.selenium.pages.RegionalOfficeDetails;
import ca.IRM.selenium.pages.ReportPreparation;
import ca.IRM.selenium.pages.ReportSearch;
import ca.IRM.selenium.pages.StandardItemChecklist;
import ca.IRM.selenium.pages.SupportingDocuments;
import ca.IRM.selenium.pages.User;
import ca.IRM.selenium.utils.*;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class CreateIncidentReport {
	
	EdgeDriver driver = new EdgeDriver();
	WebUtils utils = new WebUtils(driver);
	
	@BeforeTest(groups="InProcess1")
	public void setUpApplication() {
//		Setup IRM application
//		WebDriverManager.edgedriver().setup();
		WebUtils.setUpIrmPage(driver);
		System.out.println("Before Test");
	}
	
	@AfterTest(groups="InProcess1")
	public void close() {
//		driver.quit();
		System.out.println("After Test");
	}


//	@Test(groups="InProcess")
//	public void createIncidentReport() {
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		
////		Make sure User Type is Staff Sergeant
//		String expectedUSerTitle = "Staff Sergeant";
//		String[] userNavBarInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/header/div/p[1]"))).getText().split(", ");
//		
//		String actualUserTitle = userNavBarInfo[1];
//		System.out.println("User Title: " + actualUserTitle);
//		
//		Assert.assertEquals(actualUserTitle, expectedUSerTitle);
//		
////		Create simple Incident Report
//		utils.createGenericIncidentReport(driver);
//		
////		** Store the Incident Report ID
//		String[] texts = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[@class='mud-typography mud-typography-h6' and contains(text(), 'Regional Office Details')]"))).getText().split(" ");
//		String IncidentID = texts[texts.length - 1];
//		System.out.println("Created Incident ID: " + IncidentID);
//		
////		Verify Incident Report is saved
//		ReportSearch search = new ReportSearch(driver);
//		search.searchIncidentReport(IncidentID);		
//	}
//	
//	
//	@Test(groups="InProcess")
//	public void changeUser() {
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		NavBar nav = new NavBar(driver);
//		
//		nav.navigateNavOption("Administration", "Users");
//		
//		User user = new User(driver);
//		user.verifyPage();
//		user.changeUserType(user.staff, user.algo);
//	}
	
	
	@Test(groups="InProcess1")
	public void createAndFillIncidentReport() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		NavBar nav = new NavBar(driver);
		
//		Make sure User Type is Staff Sergeant
		String expectedUSerTitle = "Staff Sergeant";
		String[] userNavBarInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/header/div/p[1]"))).getText().split(", ");
		String actualUserTitle = userNavBarInfo[1];
		System.out.println("User Title: " + actualUserTitle);
		Assert.assertEquals(actualUserTitle, expectedUSerTitle);
		
		nav.createNewReport();
		
		
//		Fill all fields in Notification page
		Notification notificationFields = new Notification(driver);
		DateTimeUI date = new DateTimeUI(driver);
		
		notificationFields.verifyPage();
		
//		Select a date & and time (UI)
		date.selectCurrentDate();
		date.selectTimeUI(1, 0);
		
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)");
		notificationFields.selectArea("Other");
		notificationFields.fillLocationDetails("Somewhere anywhere");
		notificationFields.clickNext();
		
		
		
//		Fill in fields in Regional Office Details page
		RegionalOfficeDetails regionalFields = new RegionalOfficeDetails(driver);
		regionalFields.verifyPage();
		regionalFields.selectRegionalOfficeContactedVia("Phone");
		regionalFields.selectRegionalOfficer("Belleza", "Mark");
		regionalFields.fillAdditionalDetails("Some additional details");
		regionalFields.clickNext();
		
//		Fill in fields in Media Aware Detail page
		IsMediaAware mediaFields = new IsMediaAware(driver);
		mediaFields.verifyPage();
		mediaFields.selectUnknown();
		mediaFields.selectNo();
		mediaFields.selectYes("CCTV");
		mediaFields.clickNext();
		
		
//		Select an incident type
		IncidentTypeSelection incidentFields = new IncidentTypeSelection(driver);
		incidentFields.verifyPage();
		
//		Both IIR and EOIR incident type should be visible
		Assert.assertEquals(incidentFields.verifyIIR(), incidentFields.verifyEOIR());
		
		incidentFields.selectIIRExample();
		incidentFields.clickNext();
		
		StandardItemChecklist checklist = new StandardItemChecklist(driver);
		checklist.verifyPage();
		checklist.clickNext();
		
		SupportingDocuments support = new SupportingDocuments(driver);
		support.verifyPage();
		support.clickNext();
		
		DetailsAndCircumstances details = new DetailsAndCircumstances(driver);
		details.verifyPage();
		details.clickNext();
		
		PoliceContacted contacted = new PoliceContacted(driver);
		contacted.verifyPage();
		contacted.selectReason(contacted.notPoliceMatter);
		contacted.clickNext();
		
		Involved involve = new Involved(driver);
		involve.verifyPage();
		involve.clickNext();
		
		ReportPreparation report = new ReportPreparation(driver);
		report.verifyPage();
		report.selectContactPerson("Mark", "Belleza");
		report.finalize();
		
//		** Store the Incident Report ID
		String[] texts = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[@class='mud-typography mud-typography-h6']"))).getText().split(" ");
		String IncidentID = texts[texts.length - 1];
		System.out.println("Created Incident ID: " + IncidentID);
		
		report.clickSubmit();
		
//		Verify Incident Report is saved
		ReportSearch search = new ReportSearch(driver);
		search.searchIncidentReport(IncidentID);
		
	}

}
