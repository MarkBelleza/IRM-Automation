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

public class StaffSergeant2 {
	
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
//		driver.quit();
		System.out.println("After Test");
	}
	
//	TestCase ID: TC0002
	@Test(groups="testing")
	public void viewAndUpdate() {
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
		Assert.assertEquals(incidentFields.verifyIIR(), incidentFields.verifyEOIR());
		
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
		
//		Edit Incident Report IIR and EOIR
		sum.editIncidentType();
		
//		Both IIR and EOIR incident type should be visible
		incidentFields.verifyPage();
		Assert.assertEquals(true, incidentFields.verifyIIR());
		Assert.assertEquals(true, incidentFields.verifyEOIR());
		
		incidentFields.expandItem("IIR");
		incidentFields.selectItem("Assault");  //Remove Assault
		
		incidentFields.expandItem("Threat");
		incidentFields.expandItem("(P2) Inmate on Inmate");
		incidentFields.selectItem("Verbal");   //Add Verbal
		
		
		incidentFields.expandItem("EOIR");
		incidentFields.selectItem("Administrative");  //Remove Administrative
		
		incidentFields.expandItem("Workplace Violence");
		incidentFields.selectItem("Staff on transfer payment staff");  //Add Staff on transfer payment staff
		
		incidentFields.clickUpdate();
		
//		Verify the changes are visible
		sum.verifyPage();
		sum.verifyIncidentTypes("IIR");
		sum.verifyIncidentTypes("Threat");
		sum.verifyIncidentTypes("(P2) Inmate on Inmate");
		sum.verifyIncidentTypes("Verbal");
		
		sum.verifyIncidentTypes("EOIR");
		sum.verifyIncidentTypes("Death of Staff");
		sum.verifyIncidentTypes("Off Duty");
		
		sum.verifyIncidentTypes("Workplace Violence");
		sum.verifyIncidentTypes("Staff on transfer payment staff");
		
		sum.verifyIncidentTypesNotVisible("Assault");
		sum.verifyIncidentTypesNotVisible("Administrative");
		
//		Verify IIR and EOIR checklist is editable
		sum.editStandardItemChecklist();
		Assert.assertEquals(true, checklist.verifyItem("IIR"));
		Assert.assertEquals(true, checklist.verifyItem("EOIR"));
		
		checklist.expandItem("IIR");
		checklist.expandItem("Threat");
		checklist.selectChecklistItem("Indicate if individuals are high profile", "Yes");
		
		checklist.expandItem("EOIR");
		checklist.expandItem("Death of Staff");
		checklist.selectChecklistItem("Details and circumstances of incident", "No");
		checklist.selectChecklistItem("Link to the obituary notice", "Yes");
		checklist.clickUpdate();
		
// 		Note: This "Assault" question was automatically removed when "Assault" was removed in the Incident Type page
		sum.verifyChecklistItemNotVisible("CCRL notified if racially motivated"); 
		sum.verifyChecklistItem("Threat", "Indicate if individuals are high profile");
		
		sum.verifyChecklistItem("Death of Staff", "Link to the obituary notice");
		sum.verifyChecklistItemNotVisible("Details and circumstances of incident");
		
//		Verify Supporting documents is editable
		sum.editSupportingDocuments();
		support.uploadFile("Death of Staff", "MOL Order", "UploadFileTest2.docx");
		support.clickUpdate();
		
		sum.verifySupportingDocument("Death of Staff", "UploadFileTest2.docx");
		sum.verifySupportingDocumentNotVisible("Death of Staff", "UploadFileTest.docx");
		
//		Verify can add details for both IIR and EOIR
		sum.editDetailsAndCircumstances();
		details.addIIRDetails("Details for IIR 2");
		details.addEOIRDetails("Details for EOIR 2");
		details.clickUpdate();
		
		sum.verifyDetailsCircumstances("IIR", "IIR details 1");
		sum.verifyDetailsCircumstances("IIR", "Details for IIR 2");
		sum.verifyDetailsCircumstances("EOIR", "EOIR details 1");
		sum.verifyDetailsCircumstances("EOIR", "Details for EOIR 2");
		
//		Verify Involved section visible and editable
		sum.verifyEmployeeInInvolved("Mark", "Belleza", "Other");
		sum.editInvolved();
		involve.verifyPage();
		
	}

}
