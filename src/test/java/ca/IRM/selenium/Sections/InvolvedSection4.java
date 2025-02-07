package ca.IRM.selenium.Sections;

import org.openqa.selenium.edge.EdgeDriver;
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

public class InvolvedSection4 {
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
	private WebUtils utils = new WebUtils(driver);
	private String IncidentID;
	
	
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
		
//		Set user to Staff Sergeant and ALGOMA
		user.changeUserType(user.staff, user.algo);
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
		driver.quit();
		System.out.println("After Test");
	}

	
	
//	TestCase ID: TC0040
	@Test(groups="testing")
	public void editInvolvedInformationInUpdateMode() {
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickNext();
		utils.duplicatePopUpCheck();
		
//		** Store the Incident Report ID
		regionalFields.verifyPage();
		IncidentID = regionalFields.getIncidentID();
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
		
		involve.addEmployee("Mark", "Belleza", "Other", "2", true);
		involve.addEmployee("Derek", "Dao", "Witness", "3", true);
		
		involve.addOthers("Will", "Lyan", "Vendor", "Participant", "0", false);
		involve.addOthers("Jason", "Smith", "AgencyStaff", "Other", "1", true);
		
		involve.clickNext();
		
		report.selectContactPerson("Mark", "Belleza");
		report.finalize(); 
		report.clickSubmit();
		
//		Open the Incident report in Summary View
		search.searchIncidentReport(IncidentID);
		search.openIncidentReport(IncidentID);
		
//		Verify all involved persons are visible in summary view
		Summary summary = new Summary(driver);
		summary.verifyInmateByNameInInvolved("JOHN", "SMITH", "Witness");
		summary.verifyInmateByNameInInvolved("WILLIAM", "BEST", "Other");
		
		summary.verifyEmployeeInInvolved("Mark", "Belleza", "Other");
		summary.verifyEmployeeInInvolved("Derek", "Dao", "Witness");
		
		summary.verifyOtherInInvolved("Will", "Lyan", "Participant");
		summary.verifyOtherInInvolved("Jason", "Smith", "Other");
		
//		Edit the Involved section in Update mode
		summary.editInvolved();
		
//		Edit information of Inmates, Employees and Others
		involve.editInmateByName("JOHN", "SMITH", "Other", "1", true);
		involve.editInmateByName("WILLIAM", "BEST", "Witness", "0", true);
	
		involve.editEmployee("Mark", "Belleza", "Participant", "1", true);
		involve.editEmployee("Derek", "Dao", "Other", "0", false);
		
		involve.editOther("Will", "Lyan", "AgencyStaff", "Witness", "1", false);
		involve.editOther("Jason", "Smith", "Vendor", "Participant", "1", true);
		
		involve.clickUpdate();
	
		
//		Verify the Inmates, Employee & Others that were updated
		summary.verifyInmateByNameInInvolved("JOHN", "SMITH", "Other");
		summary.verifyInmateByNameInInvolved("WILLIAM", "BEST", "Witness");
		
		summary.verifyEmployeeInInvolved("Mark", "Belleza", "Participant");
		summary.verifyEmployeeInInvolved("Derek", "Dao", "Other");
		
		summary.verifyOtherInInvolved("Will", "Lyan", "Witness");
		summary.verifyOtherInInvolved("Jason", "Smith", "Participant");
	}
}
