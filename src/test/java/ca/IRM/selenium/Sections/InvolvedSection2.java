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

import ca.IRM.selenium.components.NavBar;
import ca.IRM.selenium.components.SearchTables;
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

public class InvolvedSection2 {
	
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
		
//		Set user to Staff Sergeant and ALGOMA
		user.changeUserType(user.staff, user.algo);
		System.out.println("Before Test USKJDSFJFFSD");		
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
		driver.quit();
		System.out.println("After Test");
	}
	
//	TestCase ID: TC0038
	@Test(groups="testing")
	public void duplicateInCreationModeInvolvedSection() {
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
		Assert.assertEquals(incidentFields.verifyIIR(), incidentFields.verifyEOIR());
		
//		Select IIR and EOIR incident types
		incidentFields.selectIIRExample();
		
		incidentFields.clickNext();
		
		checklist.clickNext();
		
		support.clickNext();
		
		details.clickNext();
		
		contacted.selectReason(contacted.notPoliceMatter);
		contacted.clickNext();
		
//		Add Inmate
		involve.addInmateByName("JOHN", "SMITH", "Witness", "4", true);
		
//		Add same Inmate again
		involve.inmateSearchButtonClick();
		SearchTables table = new SearchTables(driver);
		table.selectUserFromTable("JOHN", "SMITH");
		
//		Verify Duplicate Alert Message visible and no duplicate
		involve.verifyDuplicateInmateAlert();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("(//td[text()='JOHN' and text()='SMITH' and @data-label='Name']/ancestor::tr)[2]")));

//		Add Employee
		involve.addEmployee("Mark", "Belleza", "Other", "2", true);
		
//		Add same Employee again
		involve.employeeSearchButtonClick();
		table.selectUserFromTable("Mark", "Belleza");
		
//		Verify Duplicate Alert Message visible and no duplicate
		involve.verifyDuplicateEmployeeAlert();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("(//td[text()='Mark' and text()='Belleza' and @data-label='Employee']/ancestor::tr)[2]")));

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
		summary.verifyEmployeeInInvolved("Mark", "Belleza", "Other");
		
//		Verify the duplicates don't exist
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("(//td[text()='JOHN' and text()='SMITH' and @data-label='Name']/ancestor::tr)[2]")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("(//td[text()='Mark' and text()='Belleza' and @data-label='Employee']/ancestor::tr)[2]")));
	}
	
}
