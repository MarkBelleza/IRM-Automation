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

public class Superintendent4 {
	
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
		
//		Set user to Staff Sergeant and ALGOMA
		user.changeUserType(user.staff, user.brock);
		System.out.println("Before Test");		
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
		user.changeUserType(user.staff, user.algo);
		driver.quit();
		System.out.println("After Test");
	}
	
//	TestCase ID: TC0023
	@Test(groups="testing")
	public void viewAndUpdateOnlyIIR() {
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("BROCKVILLE JAIL - ADULT (Institution)");
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
		
//		Change user location to Algoma
		user.changeUserType(user.superin, user.algo);
		
//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.openIncidentReport(IncidentID);
		
//		In summary view, only IIR and all other sections related to IIR should be visible
		Summary sum = new Summary(driver);
		sum.verifyPage();
		sum.verifyIncidentTypes("IIR");
		sum.verifyIncidentTypes("Assault");
		sum.verifyIncidentTypes("(P1) Serious Inmate on Inmate");
		sum.verifyIncidentTypes("Item thrown/contact");
		sum.verifyIncidentTypes("Bodily substance");
		
		sum.verifyIncidentTypesNotVisible("EOIR");
		sum.verifyIncidentTypesNotVisible("Death of Staff");
		sum.verifyIncidentTypesNotVisible("Off Duty");
		
		sum.verifyChecklistItem("Assault", "CCRL notified if racially motivated");
		sum.verifyChecklistItemNotVisible("Details and circumstances of incident");
		
//		Verify Supporting Documents Section is not visible as EOIR is not visible
		Assert.assertEquals(false, sum.editSupportingDocuments());
		
		sum.verifyDetailsCircumstances("IIR", "IIR details 1");
		sum.verifyDetailsCircumstancesNotVisible("EOIR", "EOIR details 1");
		
//		Verify Involved Section is not visible as EOIR is not visible
		Assert.assertEquals(false, sum.editInvolved());
		
//		Edit Incident Report IIR
		sum.editIncidentType();
		
//		Only IIR incident type should be visible
		incidentFields.verifyPage();
		Assert.assertEquals(incidentFields.verifyIIR(), true);
		Assert.assertEquals(incidentFields.verifyEOIR(), false);
		
		incidentFields.expandItem("IIR");
		incidentFields.selectItem("Assault");
		incidentFields.expandItem("Threat");
		incidentFields.expandItem("(P2) Inmate on Inmate");
		incidentFields.selectItem("Verbal");
		
		incidentFields.clickUpdate();
		
//		Verify the changes are visible
		sum.verifyPage();
		sum.verifyIncidentTypes("IIR");
		sum.verifyIncidentTypes("Threat");
		sum.verifyIncidentTypes("(P2) Inmate on Inmate");
		sum.verifyIncidentTypes("Verbal");
		
		sum.verifyIncidentTypesNotVisible("Assault");
		sum.verifyIncidentTypesNotVisible("Death of Staff");
		
//		Verify EOIR is still not visible
		sum.verifyIncidentTypesNotVisible("EOIR");
		sum.verifyIncidentTypesNotVisible("Death of Staff");
		sum.verifyIncidentTypesNotVisible("Off Duty");
		
//		Verify only IIR in checklist is editable
		sum.editStandardItemChecklist();
		Assert.assertEquals(true, checklist.verifyItem("IIR"));
		Assert.assertEquals(false, checklist.verifyItem("EOIR"));
		
		checklist.expandItem("IIR");
		checklist.expandItem("Threat");
		checklist.selectChecklistItem("Indicate if individuals are high profile", "Yes");
		checklist.clickUpdate();
		
// 		Note: This "Assault" question was automatically removed when "Assault" was removed in the Incident Type page
		sum.verifyChecklistItemNotVisible("CCRL notified if racially motivated"); 
		sum.verifyChecklistItem("Threat", "Indicate if individuals are high profile");
		
		sum.verifyChecklistItemNotVisible("Link to the obituary notice");
		sum.verifyChecklistItemNotVisible("Details and circumstances of incident");
		
//		Verify can add details for Only IIR
		sum.editDetailsAndCircumstances();
		Assert.assertEquals(true, details.verifyIIRDetails());
		Assert.assertEquals(false, details.verifyEOIRDetails());
		details.addIIRDetails("Details for IIR 2");
		details.clickUpdate();
		
		sum.verifyDetailsCircumstances("IIR", "IIR details 1");
		sum.verifyDetailsCircumstances("IIR", "Details for IIR 2");
		sum.verifyDetailsCircumstancesNotVisible("EOIR", "EOIR details 1");
	}
}
