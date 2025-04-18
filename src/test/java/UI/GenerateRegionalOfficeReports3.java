package UI;

import java.time.Duration;

import org.openqa.selenium.edge.EdgeDriver;
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
import ca.IRM.selenium.pages.SupportingDocuments;
import ca.IRM.selenium.pages.User;
import ca.IRM.selenium.utils.WebUtils;

public class GenerateRegionalOfficeReports3 {
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

	
	private EdgeDriver driver = new EdgeDriver();
	private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	
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
		
		WebUtils.setUpIrmPage(driver);
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
//		user.changeUserType(user.staff, user.algo);
//		driver.quit();
		System.out.println("After Test");
	}

	
	
//	TestCase ID: ????
	@Test(groups="testing")
	public void regionalOfficeLocationsOnlyEOIRNotFinal(){
//		Set user to Regional Office and Northern
		user.changeUserType(user.regional, "Western Regional Office");
		
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("Western Regional Office" + " (RegionalOffice)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickNext();
		
		
//		** Store the Incident Report ID
		regionalFields.verifyPage();
		String IncidentID = regionalFields.getIncidentID();
		System.out.println("Created Incident ID for Western Regional Office: " + IncidentID);
		
		regionalFields.selectRegionalOfficeContactedVia("Phone");
		regionalFields.selectRegionalOfficer("Belleza", "Mark");
		regionalFields.clickNext();
		
		mediaFields.verifyPage();
		mediaFields.selectNo();
		mediaFields.clickNext();
		
//		Both IIR and EOIR incident type should be visible
		incidentFields.verifyPage();
		Assert.assertEquals(incidentFields.verifyIIR(), true);
		Assert.assertEquals(incidentFields.verifyEOIR(), true);
		
		incidentFields.expandItem("EOIR");
		incidentFields.expandItem("Death of Staff");
		incidentFields.expandItem("On Duty");
		incidentFields.selectItem("On site");
		
		incidentFields.expandItem("Property Damage");
		incidentFields.expandItem("Major Damage");
		incidentFields.expandItem("Nonintentional");
		incidentFields.selectItem("By accident");
		
		incidentFields.expandItem("Labour Activities");
		incidentFields.selectItem("Information pickets");
		
		incidentFields.clickNext();
		
//		EOIR should be visible in all related sections
		checklist.verifyPage();
		Assert.assertEquals(true, checklist.verifyItem("EOIR"));
		checklist.expandItem("EOIR");
		checklist.expandItem("Death of Staff");
		checklist.selectChecklistItem("Details and circumstances of incident", "Yes");
		
		checklist.expandItem("Property Damage");
		checklist.selectChecklistItem("Cause of property damage, if known", "Yes");
		
		checklist.expandItem("Labour Activities");
		checklist.selectChecklistItem("Local union consultation", "Yes");
		
		checklist.clickNext();
		
		support.verifyPage();
		Assert.assertEquals(false, support.verifySupportDocumentUnavalilable());
		support.uploadFile("Death of Staff", "MOL Order", "UploadFileTest.docx");
		support.uploadFile("Labour Activities", "MOL Order", "UploadFileTest2.docx");
		support.clickNext();
		
		details.verifyPage();
		Assert.assertEquals(true, details.verifyEOIRDetails());
		details.addEOIRDetails("EOIR details 1");
		details.clickNext();
		
		contacted.verifyPage();
//		contacted.selectReason(contacted.notPoliceMatter);
		contacted.selectPoliceContacted("Yes");
		contacted.selectWillPoliceBeAttending("Yes");
		contacted.selectCriminalCharges("Yes");
		contacted.fillPersonContacted("Testing");
		contacted.fillPersonContactedBy("Mark Belleza");
		contacted.fillPoliceCase("9");
		contacted.fillPoliceContactedMethod("email");
		contacted.fillPoliceService("Hello world");
		contacted.fillPoliceTelephone("1111111111");
		contacted.clickNext();
		
		involve.verifyPage();
		involve.addInmateByName("JOHN", "SMITH", "Witness", "4", true);
		involve.addInmateByName("WILLIAM", "BEST", "Other", "3", true);
		involve.addInmateByName("AARON", "VASSCOUNT", "Participant", "1", true);
		involve.addInmateByName("SMITH", "TEST", "Witness", "0", false);
		
		involve.addEmployee("Mark", "Belleza", "Other", "2", true);
		involve.addEmployee("Derek", "Dao", "Witness", "3", true);
		involve.addEmployee("Travis", "Wong", "Participant", "3", false);
		involve.addEmployee("Roy", "Franck", "Other", "1", true);
		
		involve.addOthers("Will", "Lyan", "Vendor", "Participant", "0", false);
		involve.addOthers("Jason", "Smith", "AgencyStaff", "Other", "1", true);
		involve.addOthers("Mark", "Bell", "Visitor", "Witness", "0", false);
		involve.addOthers("Julian", "Da", "Volunteer", "Participant", "2", true);
		involve.clickNext();
		
		report.selectContactPerson("Mark", "Belleza");
		report.notFinalize();
		report.clickSubmit();
		
//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.verifyIncident(IncidentID, "EOIR, Death of Staff, Property Damage, Labour Activities"); //level 2 and 3 are not displayed
	}
}