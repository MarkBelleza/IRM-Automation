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
import ca.IRM.selenium.pages.Summary;
import ca.IRM.selenium.pages.SupportingDocuments;
import ca.IRM.selenium.pages.User;
import ca.IRM.selenium.utils.WebUtils;

public class Superintendent {
	
	private NavBar nav;
	private Notification notificationFields;
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
	private ReportSearch search;
	
	private WebDriverWait wait;
	
	private EdgeDriver driver = new EdgeDriver();
	private WebUtils utils = new WebUtils(driver);
	
	
	
	@BeforeTest(groups="testing")
	public void setUpApplication() {
//		Setup IRM application
//		WebDriverManager.edgedriver().setup();
		nav = new NavBar(driver);
		notificationFields = new Notification(driver);
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
		
		WebUtils.setUpIrmPage(driver);
		
//		Set user to Superintendent and ALGOMA
		user.changeUserType(user.superin, user.algo);
		System.out.println("Before Test USKJDSFJFFSD");		
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
		user.changeUserType(user.staff, user.algo);
		driver.quit();
		System.out.println("After Test");
	}

	
	
//	TestCase ID: TC0019
	@Test(groups="testing")
	public void createIncidentReport() {
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickNext();
		
		
//		** Store the Incident Report ID
		regionalFields.verifyPage();
		String IncidentID = regionalFields.getIncidentID();
		System.out.println("Created Incident ID: " + IncidentID);
		
		regionalFields.clickNext();
		
		mediaFields.clickNext();
		
//		Both IIR and EOIR incident type should be visible
		incidentFields.verifyPage();
		Assert.assertEquals(incidentFields.verifyIIR(), true);
		Assert.assertEquals(incidentFields.verifyEOIR(), true);
		
//		Select IIR and EOIR incident types
		incidentFields.expandItem("IIR");
		incidentFields.expandItem("Assault");
		incidentFields.expandItem("(P1) Serious Inmate on Inmate");
		incidentFields.expandItem("Item thrown/contact");
		incidentFields.selectItem("Bodily substance");
		
		incidentFields.expandItem("EOIR");
		incidentFields.expandItem("Death of Staff");
		incidentFields.selectItem("Off Duty");
		
		incidentFields.clickNext();
		
//		Both IIR and EOIR should be visible in all related sections
		checklist.verifyPage();
		Assert.assertEquals(true, checklist.verifyItem("IIR"));
		checklist.expandItem("IIR");
		checklist.expandItem("Assault");
		checklist.selectChecklistItem("CCRL notified if racially motivated", "Yes");
		
		Assert.assertEquals(true, checklist.verifyItem("EOIR"));
		checklist.expandItem("EOIR");
		checklist.expandItem("Death of Staff");
		checklist.selectChecklistItem("Details and circumstances of incident", "Yes");
		checklist.clickNext();
		
		support.verifyPage();
		Assert.assertEquals(false, support.verifySupportDocumentUnavalilable());
		support.uploadFile("Death of Staff", "MOL Order", "UploadFileTest.docx");
		support.clickNext();
		
		details.verifyPage();
		Assert.assertEquals(true, details.verifyIIRDetails());
		Assert.assertEquals(true, details.verifyEOIRDetails());
		details.addIIRDetails("IIR details 1");
		details.addEOIRDetails("EOIR details 1");
		details.clickNext();
		
		contacted.selectReason(contacted.notPoliceMatter);
		contacted.clickNext();
		
		involve.clickNext();
		
		report.selectContactPerson("Mark", "Belleza");
		report.finalize(); 
		report.clickSubmit();
		
//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.verifyIncident(IncidentID, "IIR, Assault, EOIR, Death of Staff"); //level 2 and 3 are not displayed
		
		
	}
	
//	TestCase ID: TC0021
	@Test(groups="testing")
	public void markAndUnmartIncidentReportConfidentialAndView() {
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickNext();
		
		
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
		
		//Verify Confidential option
		Assert.assertEquals(report.confidentialPromptCheck(), true);
		
//		Set Confidential and Verify Confidential option is markable
		Assert.assertEquals(report.verifyConfidentialUnmarkable(), false);
		report.setConfidential();
		report.selectContactPerson("Mark", "Belleza");
		report.finalize();
		report.clickSubmit();
		
//		Verify that the incident report is still visible as a Superintendent 
		search.searchIncidentReport(IncidentID);
		search.verifyIncident(IncidentID);
		
//		Verify that the incident report is not visible as a Staff Sergeant
		user.changeUserType(user.staff, user.algo);
		search.searchIncidentReport(IncidentID);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[@class ='mud-table-cell' and text()='" + IncidentID + "']")));
		
//		Verify the incident report can be unmarked as confidential (as a Superintendent)
		user.changeUserType(user.superin, user.algo);
		search.searchIncidentReport(IncidentID);
		search.openIncidentReport(IncidentID);
		
		Summary sum = new Summary(driver);
		sum.editReportPreparation();
		
		//Verify Confidential option
		Assert.assertEquals(report.confidentialPromptCheck(), true);
		
		report.setConfidential(); //Un-mark confidentiality
		report.finalize();
		report.clickSubmit();
		search.searchIncidentReport(IncidentID);
		search.verifyIncident(IncidentID);
		
//		Verify that the incident report is now visible as a Staff Sergeant
		user.changeUserType(user.staff, user.algo);
		search.searchIncidentReport(IncidentID);
		search.verifyIncident(IncidentID);
		
	}
}
