package ca.IRM.selenium.UserTypeAccess;

import java.time.Duration;

import org.openqa.selenium.edge.EdgeDriver;
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

public class AdminToTheSuperintendent {
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
		user.changeUserType(user.admin, user.algo);
		System.out.println("Before Test");		
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
		user.changeUserType(user.staff, user.algo);
//		driver.quit();
		System.out.println("After Test");
	}
	
	
//	TestCase ID: TC0007
	@Test(groups="testing")
	public void createIncidentReport() {
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to NOT within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickNext();
		utils.duplicatePopUpCheck();
		
//		** Store the Incident Report ID
		regionalFields.verifyPage();
		String IncidentID = regionalFields.getIncidentID();
		System.out.println("Created Incident ID: " + IncidentID);
		
		regionalFields.clickNext();
		
		mediaFields.clickNext();
		
//		Only EOIR incident type should be visible
		incidentFields.verifyPage();
		Assert.assertEquals(incidentFields.verifyIIR(), false);
		Assert.assertEquals(incidentFields.verifyEOIR(), true);
		
//		Select IIR incident type
		incidentFields.expandItem("EOIR");
		incidentFields.expandItem("Death of Staff");
		incidentFields.expandItem("On Duty");
		incidentFields.selectItem("Off site");
		
		incidentFields.expandItem("Serious Contrabnd (Staff)");
		incidentFields.selectItem("Alcohol");
		
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
		search.verifyIncident(IncidentID, "EOIR, Death of Staff, Serious Contrabnd (Staff)"); //level 2 and 3 are not displayed
		
		
	}
	
//	TestCase ID: TC0009
	@Test(groups="testing")
	public void markIncidentReportConfidentialNot() {
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)");
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
		incidentFields.selectEOIRExample();
		incidentFields.clickNext();
		
		checklist.clickNext();
		
		support.clickNext();
		
		details.clickNext();
		
		contacted.selectReason(contacted.notPoliceMatter);
		contacted.clickNext();
		
		involve.clickNext();
		
//		Confidential option should not be visible
		Assert.assertEquals(report.confidentialPromptCheck(), false);
		report.selectContactPerson("Mark", "Belleza");
		report.finalize();
		report.clickSubmit();
		
//		Verify that the incident report is visible
		search.searchIncidentReport(IncidentID);
		search.verifyIncident(IncidentID);
		
	}
}
