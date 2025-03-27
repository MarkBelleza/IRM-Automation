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

public class AdminToTheSuperintendent2 {
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
		user.changeUserType(user.staff, user.algo);
		
		System.out.println("Before Test");		
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
		user.changeUserType(user.staff, user.algo);
		driver.quit();
		System.out.println("After Test");
	}
	
//	TestCase ID: TC0008
	@Test(groups="testing")
	public void viewAndUpdateEOIRandViewIIR() {
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
		
//		Change user type to Admin to the Superintendent
		user.changeUserType(user.admin, user.algo);
		
//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.openIncidentReport(IncidentID);
		
//		In summary view, the IIR and EOIR should be visible and all other related sections
		Summary sum = new Summary(driver);
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
		
//		Edit Incident type. Only EOIR should be editable
		sum.editIncidentType();
		incidentFields.verifyPage();
		Assert.assertEquals(incidentFields.verifyIIR(), false);
		Assert.assertEquals(incidentFields.verifyEOIR(), true);
		
//		Remove Off Duty
		incidentFields.expandItem("EOIR");
		incidentFields.expandItem("Death of Staff");
		incidentFields.selectItem("Off Duty");
		
//		Add Off site
		incidentFields.expandItem("On Duty");
		incidentFields.selectItem("Off site");
		
//		Add Security (i.e., handcuff or cell/unit key)
		incidentFields.expandItem("Items Lost/Items Stolen");
		incidentFields.expandItem("Keys");
		incidentFields.selectItem("Security (i.e., handcuff or cell/unit key)");
		incidentFields.clickUpdate();
		
//		Verify the changes are visible
		sum.verifyPage();
		
//		IIR should be unchanged
		sum.verifyIncidentTypes("IIR");
		sum.verifyIncidentTypes("Assault");
		sum.verifyIncidentTypes("(P1) Serious Inmate on Inmate");
		sum.verifyIncidentTypes("Item thrown/contact");
		sum.verifyIncidentTypes("Bodily substance");
		
//		New changes to EOIR should be reflected
		sum.verifyIncidentTypes("Death of Staff");
		sum.verifyIncidentTypes("On Duty");
		sum.verifyIncidentTypes("Off site");
		sum.verifyIncidentTypes("Items Lost/Items Stolen");
		sum.verifyIncidentTypes("Keys");
		sum.verifyIncidentTypes("Security (i.e., handcuff or cell/unit key)");
		
		sum.verifyIncidentTypesNotVisible("Off Duty");
		
//		Verify only EOIR checklist is editable
		sum.editStandardItemChecklist();
		checklist.verifyPage();
		Assert.assertEquals(false, checklist.verifyItem("IIR"));
		Assert.assertEquals(true, checklist.verifyItem("EOIR"));
	
		checklist.expandItem("EOIR");
		checklist.expandItem("Death of Staff");
		checklist.selectChecklistItem("Details and circumstances of incident", "No");
		checklist.selectChecklistItem("Link to the obituary notice", "Yes");
		checklist.clickUpdate();
		
//		IIR checklist is still visible
		sum.verifyChecklistItem("Assault", "CCRL notified if racially motivated"); 
		
//		Changes to EOIR checklist are visible
		sum.verifyChecklistItem("Death of Staff", "Link to the obituary notice");
		sum.verifyChecklistItemNotVisible("Details and circumstances of incident");
		
//		Verify Supporting documents is editable
		sum.editSupportingDocuments();
		support.verifyPage();
		Assert.assertEquals(false, support.verifySupportDocumentUnavalilable());
		support.uploadFile("Death of Staff", "MOL Order", "UploadFileTest2.docx");
		support.clickUpdate();
		
		sum.verifySupportingDocument("Death of Staff", "UploadFileTest2.docx");
		sum.verifySupportingDocumentNotVisible("Death of Staff", "UploadFileTest.docx");
		
//		Verify can add details for only EOIR
		sum.editDetailsAndCircumstances();
		details.verifyPage();
		Assert.assertEquals(true, details.verifyEOIRDetails());
		Assert.assertEquals(false, details.verifyIIRDetails());
		details.addEOIRDetails("Details for EOIR 2");
		details.clickUpdate();
		
//		Added EOIR is visible along with the unchanged IIR details
		sum.verifyDetailsCircumstances("IIR", "IIR details 1");
		sum.verifyDetailsCircumstances("EOIR", "EOIR details 1");
		sum.verifyDetailsCircumstances("EOIR", "Details for EOIR 2");
		
//		Verify Involved section visible and editable
		sum.verifyEmployeeInInvolved("Mark", "Belleza", "Other");
		sum.editInvolved();
		involve.verifyPage();
	}
}
