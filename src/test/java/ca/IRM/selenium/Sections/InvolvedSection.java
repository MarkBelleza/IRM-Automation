package ca.IRM.selenium.Sections;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
import ca.IRM.selenium.pages.Summary;
import ca.IRM.selenium.pages.SupportingDocuments;
import ca.IRM.selenium.pages.User;
import ca.IRM.selenium.utils.WebUtils;

public class InvolvedSection {

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

	
	
//	TestCase ID: TC0036
	@Test(groups="testing")
	public void addInvolved() {
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
		
//		Both IIR and EOIR incident type should be visible
		incidentFields.verifyPage();
		Assert.assertEquals(incidentFields.verifyIIR(), incidentFields.verifyEOIR());
		
//		Select IIR and EOIR incident types
		incidentFields.selectIIRExample();
		
		incidentFields.clickNext();
		
		checklist.clickNext();
		
		support.clickNext();
		
		details.clickNext();
		
		contacted.selectReason(contacted.notPoliceMatter);
		contacted.clickNext();
		
//		Add multiple Inmates, Employees and Others
		involve.addInmateByName("JOHN", "SMITH", "Witness", "4", true);
		involve.addInmateByName("WILLIAM", "BEST", "Other", "3", true);
		involve.addInmateByName("AARON", "VASSCOUNT", "Participant", "1", true);
		involve.addInmateByName("SMITH", "TEST", "Witness", "0", false);
		involve.addInmateByName("KENO", "MARTYN", "Other", "2", false);
		
		
		involve.addEmployee("Mark", "Belleza", "Other", "2", true);
		involve.addEmployee("Derek", "Dao", "Witness", "3", true);
		involve.addEmployee("Travis", "Wong", "Participant", "3", false);
		involve.addEmployee("Roy", "Franck", "Other", "1", true);
		involve.addEmployee("Cathy", "Marcotte", "Witness", "2", false);
		
		
		involve.addOthers("Will", "Lyan", "Vendor", "Participant", "0", false);
		involve.addOthers("Jason", "Smith", "AgencyStaff", "Other", "1", true);
		involve.addOthers("Mark", "Bell", "Visitor", "Witness", "0", false);
		involve.addOthers("Julian", "Da", "Volunteer", "Participant", "2", true);
		involve.addOthers("May", "Silva", "Other", "Other", "3", false);
		
		
		involve.clickNext();
		
		report.selectContactPerson("Mark", "Belleza");
		report.finalize(); 
		report.clickSubmit();
		
//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.openIncidentReport(IncidentID);
		
//		Verify all involved persons are visible in summary view
		Summary summary = new Summary(driver);
		summary.verifyInmateByNameInInvolved("JOHN", "SMITH", "Witness");
		summary.verifyInmateByNameInInvolved("WILLIAM", "BEST", "Other");
		summary.verifyInmateByNameInInvolved("AARON", "VASSCOUNT", "Participant");
		summary.verifyInmateByNameInInvolved("SMITH", "TEST", "Witness");
		summary.verifyInmateByNameInInvolved("KENO", "MARTYN", "Other");
		
		summary.verifyEmployeeInInvolved("Mark", "Belleza", "Other");
		summary.verifyEmployeeInInvolved("Derek", "Dao", "Witness");
		summary.verifyEmployeeInInvolved("Travis", "Wong", "Participant");
		summary.verifyEmployeeInInvolved("Roy", "Franck", "Other");
		summary.verifyEmployeeInInvolved("Cathy", "Marcotte", "Witness");
		
		summary.verifyOtherInInvolved("Will", "Lyan", "Participant");
		summary.verifyOtherInInvolved("Jason", "Smith", "Other");
		summary.verifyOtherInInvolved("Mark", "Bell", "Witness");
		summary.verifyOtherInInvolved("Julian", "Da", "Participant");
		summary.verifyOtherInInvolved("May", "Silva", "Other");
	}
	
//	TestCase ID: TC0037
	@Test(groups="testing")
	public void deleteInvolved() {
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
		
//		Both IIR and EOIR incident type should be visible
		incidentFields.verifyPage();
		Assert.assertEquals(incidentFields.verifyIIR(), incidentFields.verifyEOIR());
		
//		Select IIR and EOIR incident types
		incidentFields.selectIIRExample();
		
		incidentFields.clickNext();
		
		checklist.clickNext();
		
		support.clickNext();
		
		details.clickNext();
		
		contacted.selectReason(contacted.notPoliceMatter);
		contacted.clickNext();
		
//		Add multiple Inmates, Employees and Others
		involve.addInmateByName("JOHN", "SMITH", "Witness", "4", true);
		involve.addInmateByName("WILLIAM", "BEST", "Other", "3", true);
		involve.addInmateByName("AARON", "VASSCOUNT", "Participant", "1", true);
		involve.addInmateByName("SMITH", "TEST", "Witness", "0", false);
		involve.addInmateByName("KENO", "MARTYN", "Other", "2", false);
		
		
		involve.addEmployee("Mark", "Belleza", "Other", "2", true);
		involve.addEmployee("Derek", "Dao", "Witness", "3", true);
		involve.addEmployee("Travis", "Wong", "Participant", "3", false);
		involve.addEmployee("Roy", "Franck", "Other", "1", true);
		involve.addEmployee("Cathy", "Marcotte", "Witness", "2", false);
		
		
		involve.addOthers("Will", "Lyan", "Vendor", "Participant", "0", false);
		involve.addOthers("Jason", "Smith", "AgencyStaff", "Other", "1", true);
		involve.addOthers("Mark", "Bell", "Visitor", "Witness", "0", false);
		involve.addOthers("Julian", "Da", "Volunteer", "Participant", "2", true);
		involve.addOthers("May", "Silva", "Other", "Other", "3", false);
		
//		Delete some Inmates, Employees and Others
		involve.deleteInmateByName("JOHN", "SMITH");
		involve.deleteInmateByName("AARON", "VASSCOUNT");
		involve.deleteInmateByName("KENO", "MARTYN");
		
		involve.deleteEmployee("Mark", "Belleza");
		involve.deleteEmployee("Derek", "Dao");
		involve.deleteEmployee("Travis", "Wong");
		
		involve.deleteOther("Mark", "Bell");
		involve.deleteOther("Julian", "Da");
		involve.deleteOther("May", "Silva");
		
		involve.clickNext();
		
		report.selectContactPerson("Mark", "Belleza");
		report.finalize(); 
		report.clickSubmit();
		
//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.openIncidentReport(IncidentID);
		
//		Verify all involved persons are visible in summary view
		Summary summary = new Summary(driver);
		summary.verifyInmateByNameNotInvolved("JOHN", "SMITH", "Witness");
		summary.verifyInmateByNameInInvolved("WILLIAM", "BEST", "Other");
		summary.verifyInmateByNameNotInvolved("AARON", "VASSCOUNT", "Participant");
		summary.verifyInmateByNameInInvolved("SMITH", "TEST", "Witness");
		summary.verifyInmateByNameNotInvolved("KENO", "MARTYN", "Other");

		summary.verifyEmployeeNotInvolved("Mark", "Belleza", "Other");
		summary.verifyEmployeeNotInvolved("Derek", "Dao", "Witness");
		summary.verifyEmployeeNotInvolved("Travis", "Wong", "Participant");
		summary.verifyEmployeeInInvolved("Roy", "Franck", "Other");
		summary.verifyEmployeeInInvolved("Cathy", "Marcotte", "Witness");

		summary.verifyOtherInInvolved("Will", "Lyan", "Participant");
		summary.verifyOtherInInvolved("Jason", "Smith", "Other");
		summary.verifyOtherNotInvolved("Mark", "Bell", "Witness");
		summary.verifyOtherNotInvolved("Julian", "Da", "Participant");
		summary.verifyOtherNotInvolved("May", "Silva", "Other");
	}

}
