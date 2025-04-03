package ca.IRM.selenium.UserTypeAccess;

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

public class RegionalOffice6 {

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
	
	private String IncidentID;
	
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
		
		WebUtils.setUpIrmPage(driver);
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
		user.changeUserType(user.staff, user.algo);
		driver.quit();
	}
	
//	TestCase ID: TC0043
	@Test(groups="testing")
	public void regionalOfficeLocationInNotificationUpdateMode() {
//		Set user to Regional Office and Northern
		user.changeUserType(user.regional, user.northern);
		
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickNext();
		
		
//		** Store the Incident Report ID
		regionalFields.verifyPage();
		IncidentID = regionalFields.getIncidentID();
		System.out.println("Created Incident ID: " + IncidentID);
		
		regionalFields.clickNext();
		
		mediaFields.clickNext();
		
//		Both IIR and EOIR incident type should be visible
		incidentFields.verifyPage();
		Assert.assertEquals(incidentFields.verifyIIR(), true);
		Assert.assertEquals(incidentFields.verifyEOIR(), true);
		
		incidentFields.expandItem("IIR");
		incidentFields.expandItem("Assault");
		incidentFields.expandItem("(P1) Serious Inmate on Inmate");
		incidentFields.expandItem("Item thrown/contact");
		incidentFields.selectItem("Bodily substance");
		
		incidentFields.expandItem("EOIR");
		incidentFields.expandItem("Administrative");
		incidentFields.selectItem("Good News Story");
		
		incidentFields.expandItem("Death of Staff");
		incidentFields.selectItem("Off Duty");
		
		
		incidentFields.clickNext();
		
		checklist.verifyPage();
		checklist.expandItem("IIR");
		checklist.expandItem("Assault");
		checklist.selectChecklistItem("CCRL notified if racially motivated", "Yes");
		
		checklist.expandItem("EOIR");
		checklist.expandItem("Death of Staff");
		checklist.selectChecklistItem("Details and circumstances of incident", "Yes");
		checklist.clickNext();
		
		support.verifyPage();
		support.uploadFile("Death of Staff", "MOL Order", "UploadFileTest.docx");
		support.clickNext();
		
		details.verifyPage();
		details.addIIRDetails("IIR details 1");
		details.addEOIRDetails("EOIR details 1");
		details.clickNext();
		
		contacted.selectReason(contacted.notPoliceMatter);
		contacted.clickNext();
		
		involve.addEmployee("Mark", "Belleza", "Other", "1", true);
		involve.clickNext();
		
		report.selectContactPerson("Mark", "Belleza");
		report.finalize(); 
		report.clickSubmit();
		
		Summary sum = new Summary(driver);

//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.openIncidentReport(IncidentID);
		
//		In summary view, the IIR and EOIR should be visible and all other related sections
		sum.verifyPage();
		sum.verifyIncidentTypes("IIR");
		sum.verifyIncidentTypes("Assault");
		sum.verifyIncidentTypes("(P1) Serious Inmate on Inmate");
		sum.verifyIncidentTypes("Item thrown/contact");
		sum.verifyIncidentTypes("Bodily substance");
		sum.verifyIncidentTypes("EOIR");
		sum.verifyIncidentTypes("Death of Staff");
		sum.verifyIncidentTypes("Off Duty");
		
		sum.verifyChecklistItem("Assault", "CCRL notified if racially motivated");
		sum.verifyChecklistItem("Death of Staff", "Details and circumstances of incident");
		
		sum.verifySupportingDocument("Death of Staff", "UploadFileTest.docx");
		
		sum.verifyDetailsCircumstances("IIR", "IIR details 1");
		sum.verifyDetailsCircumstances("EOIR", "EOIR details 1");
		
		sum.verifyEmployeeInInvolved("Mark", "Belleza", "Other");
		
//		In Summary view, the location and area in the Notification section are visible
		sum.verifyNotificationLocation("ALGOMA TREATMENT & REMAND CTR-ADULT");
		sum.verifyNotificationArea("Washroom");
		sum.verifyNotificationUnitRange("");
		
//		Edit Notification section
		sum.editNotification();
		
		notificationFields.updateLocation(user.northern + " (RegionalOffice)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickUpdate();
		
//		In summary view, the IIR and EOIR and all related sections are still visible
		sum.verifyPage();
		sum.verifyIncidentTypes("IIR");
		sum.verifyIncidentTypes("Assault");
		sum.verifyIncidentTypes("(P1) Serious Inmate on Inmate");
		sum.verifyIncidentTypes("Item thrown/contact");
		sum.verifyIncidentTypes("Bodily substance");
		sum.verifyIncidentTypes("EOIR");
		sum.verifyIncidentTypes("Death of Staff");
		sum.verifyIncidentTypes("Off Duty");
		
		sum.verifyChecklistItem("Assault", "CCRL notified if racially motivated");
		sum.verifyChecklistItem("Death of Staff", "Details and circumstances of incident");
		
		sum.verifySupportingDocument("Death of Staff", "UploadFileTest.docx");
		
		sum.verifyDetailsCircumstances("IIR", "IIR details 1");
		sum.verifyDetailsCircumstances("EOIR", "EOIR details 1");
		
		sum.verifyEmployeeInInvolved("Mark", "Belleza", "Other");
	}
}
