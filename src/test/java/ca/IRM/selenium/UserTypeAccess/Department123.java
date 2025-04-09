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
import ca.IRM.selenium.utils.ReadXLSData;
import ca.IRM.selenium.utils.WebUtils;

public class Department123 {
	
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
		
		System.out.println("Before Test");		
	}
	
	
	@AfterTest(groups="testing")
	public void close() {
		user.changeUserType(user.staff, user.algo);
		driver.quit();
		System.out.println("After Test");
	}
	
//	TestCase ID: TC0031
	@Test(dataProvider="xmls", dataProviderClass=ReadXLSData.class, groups="testing")
	public void Department1(String location) {
		
		Summary sum = new Summary(driver);
		user.changeUserType(user.depart1, location);
		
//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.openIncidentReport(IncidentID);
		
//		In summary view, only IIR and all other sections related to IIR should be visible
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
		
		//Verify Department 1 cannot Edit Incident Type
		Assert.assertEquals(sum.editIncidentType(), false);
		Assert.assertEquals(sum.editStandardItemChecklist(), false);
		Assert.assertEquals(sum.editSupportingDocuments(), false);
		Assert.assertEquals(sum.editDetailsAndCircumstances(), false);
		Assert.assertEquals(sum.editInvolved(), false);
					
		}
	
//	TestCase ID: TC0032
	@Test(groups="testing")
	public void department2ViewIIR() {

		Summary sum = new Summary(driver);
		String[] locations = {"Data Insights and Strategic Initiatives Division "};

		for (String loc: locations) {
			user.changeUserType(user.depart2, loc);
			
//			Verify Incident Report is saved
			search.searchIncidentReport(IncidentID);
			search.openIncidentReport(IncidentID);
			
//			In summary view, only IIR and all other sections related to IIR should be visible
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
			
			//Verify Department 2 cannot Edit Incident Type
			Assert.assertEquals(sum.editIncidentType(), false);
			Assert.assertEquals(sum.editStandardItemChecklist(), false);
			Assert.assertEquals(sum.editSupportingDocuments(), false);
			Assert.assertEquals(sum.editDetailsAndCircumstances(), false);
			Assert.assertEquals(sum.editInvolved(), false);
			}
 	}
	
//	TestCase ID: TC0033
	@Test(dataProvider="xmls", dataProviderClass=ReadXLSData.class, groups="testing")
	public void Department3(String location) {
		
		Summary sum = new Summary(driver);
		user.changeUserType(user.depart3, location);
		
//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.openIncidentReport(IncidentID);
		
//		In summary view, IIR and EOIR are visible in all related sections
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
		
		//Verify Department 3 cannot Edit Incident Type, Checklist, Support Documents, Details and Circumstances and Involved
		Assert.assertEquals(sum.editIncidentType(), false);
		Assert.assertEquals(sum.editStandardItemChecklist(), false);
		Assert.assertEquals(sum.editSupportingDocuments(), false);
		Assert.assertEquals(sum.editDetailsAndCircumstances(), false);
		Assert.assertEquals(sum.editInvolved(), false);
	}
	
}
