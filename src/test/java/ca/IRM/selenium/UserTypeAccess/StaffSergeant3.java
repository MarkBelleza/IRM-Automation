package ca.IRM.selenium.UserTypeAccess;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
import ca.IRM.selenium.utils.DateTimeUI;
import ca.IRM.selenium.utils.NavBar;
import ca.IRM.selenium.utils.WebUtils;

public class StaffSergeant3 {
	private NavBar nav;
	private Notification notificationFields;
	private DateTimeUI date;
	private RegionalOfficeDetails regionalFields;
	private IsMediaAware mediaFields;
	private IncidentTypeSelection incidentFields;
	private StandardItemChecklist checklist;
	private SupportingDocuments support ;
	private DetailsAndCircumstances details ;
	private PoliceContacted contacted;
	private Involved involve;
	private ReportPreparation report;	
	private User user;
	ReportSearch search;
	
	WebDriverWait wait;
//	FluentWait<EdgeDriver> wait;
	
	private EdgeDriver driver = new EdgeDriver();
	private WebUtils utils = new WebUtils(driver);
	
	
	
	@BeforeTest(groups="testing")
	public void setUpApplication() {
//		Setup IRM application
//		WebDriverManager.edgedriver().setup();
		nav = new NavBar(driver);
		notificationFields = new Notification(driver);
		date = new DateTimeUI(driver);
		regionalFields = new RegionalOfficeDetails(driver);
		mediaFields = new IsMediaAware(driver);
		incidentFields = new IncidentTypeSelection(driver);
		checklist = new StandardItemChecklist(driver);
		support = new SupportingDocuments(driver);
		details = new DetailsAndCircumstances(driver);
		contacted = new PoliceContacted(driver);
		involve = new Involved(driver);
		report = new ReportPreparation(driver);		
		user = new User(driver);
		search = new ReportSearch(driver);
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		wait = new FluentWait<>(driver)
//			    .withTimeout(Duration.ofSeconds(10))
//			    .pollingEvery(Duration.ofMillis(500))
//			    .ignoring(NoSuchElementException.class);
		
		WebUtils.setUpIrmPage(driver);
		
//		Set user to Staff Sergeant and ALGOMA
		user.changeUserType(user.staff, user.algo);
		System.out.println("Before Test USKJDSFJFFSD");		
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
//		driver.quit();
		System.out.println("After Test");
	}
	
	
//	TestCase ID: TC0004
	@Test(groups="testing")
	public void createIncidentReport() {
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to NOT within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("BROCKVILLE JAIL - ADULT (Institution)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickNext();
		utils.duplicatePopUpCheck();
		
//		** Store the Incident Report ID
		regionalFields.verifyPage();
		String IncidentID = regionalFields.getIncidentID();
		System.out.println("Created Incident ID: " + IncidentID);
		
		regionalFields.clickNext();
		
		mediaFields.clickNext();
		
//		Only IIR incident type should be visible
		incidentFields.verifyPage();
		Assert.assertEquals(incidentFields.verifyIIR(), true);
		Assert.assertEquals(incidentFields.verifyEOIR(), false);
		
//		Select IIR incident type
		incidentFields.expandItem("IIR");
		incidentFields.expandItem("Assault");
		incidentFields.expandItem("(P1) Serious Inmate on Inmate");
		incidentFields.expandItem("Item thrown/contact");
		incidentFields.selectItem("Bodily substance");
		
		incidentFields.expandItem("Emergency Situation");
		incidentFields.selectItem("(P2) Disturbance");
		
		incidentFields.clickNext();
		
		checklist.clickNext();
		
		support.clickNext();
		
		details.clickNext();
		
		contacted.selectReason(contacted.notPoliceMatter);
		contacted.clickNext();
		
		involve.clickNext();
		
		report.selectContactPerson("Mark", "Belleza");
		report.finalize(); 
		report.clickSubmit();
		
//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.verifyIncident(IncidentID, "IIR, Assault, Emergency Situation"); //level 2 and 3 are not displayed
		
		
	}
	
//	TestCase ID: TC0006
	@Test(groups="testing")
	public void markIncidentReportConfidentialNot() {
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("BROCKVILLE JAIL - ADULT (Institution)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickNext();
		utils.duplicatePopUpCheck();
		
//		** Store the Incident Report ID
		regionalFields.verifyPage();
		String IncidentID = regionalFields.getIncidentID();
		System.out.println("Created Incident ID: " + IncidentID);
		
		regionalFields.clickNext();
		
		mediaFields.clickNext();
		
		incidentFields.verifyPage();
		incidentFields.selectIIRExample();
		incidentFields.clickNext();
		
		checklist.clickNext();
		
		support.clickNext();
		
		details.clickNext();
		
		contacted.selectReason(contacted.notPoliceMatter);
		contacted.clickNext();
		
		involve.clickNext();
		
//		Verify Confidential option does not exist
		Assert.assertEquals(report.confidentialPromptCheck(), false);
		report.selectContactPerson("Mark", "Belleza");
		report.finalize();
		report.clickSubmit();
		
//		Verify that the incident report is visible
		search.searchIncidentReport(IncidentID);
		search.verifyIncident(IncidentID);
		
	}
}