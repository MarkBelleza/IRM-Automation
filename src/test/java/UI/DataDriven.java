package UI;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ca.IRM.selenium.components.DateTimeUI;
import ca.IRM.selenium.components.NavBar;
import ca.IRM.selenium.components.SearchTables;
import ca.IRM.selenium.pages.DetailsAndCircumstances;
import ca.IRM.selenium.pages.IncidentTypeSelection;
import ca.IRM.selenium.pages.Involved;
import ca.IRM.selenium.pages.IsMediaAware;
import ca.IRM.selenium.pages.Journal;
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

public class DataDriven {

	
//	@Test(dataProvider="xmls", dataProviderClass=ReadXLSData.class, groups="testing1")
//	public static void Locations(String string) {
//		System.out.println(string);
//	}
//	
//	@Test(dataProvider="locations", dataProviderClass=Data.class, groups="testing1")
//	public void test2(String string1, String string2) {
//		System.out.println(string1 + string2);
//	}
	
//	@Test
//	public void test3() throws EncryptedDocumentException, IOException {
//		ReadXLSData loc = new ReadXLSData();
//		String[] locations =loc.getData("Locations");
//		System.out.println(locations);
//	}
//	
	@Test(groups="testing")
	public void a() {
		
		EdgeDriver driver = new EdgeDriver();
		WebUtils utils = new WebUtils(driver);
		WebUtils.setUpIrmPage(driver);
		
		ReportSearch search = new ReportSearch(driver);
		NavBar nav = new NavBar(driver);
		Notification notificationFields = new Notification(driver);
		DateTimeUI date = new DateTimeUI(driver);
		RegionalOfficeDetails regionalFields = new RegionalOfficeDetails(driver);
		IsMediaAware mediaFields = new IsMediaAware(driver);
		IncidentTypeSelection incidentFields = new IncidentTypeSelection(driver);
		StandardItemChecklist checklist = new StandardItemChecklist(driver);
		SupportingDocuments support = new SupportingDocuments(driver);
		DetailsAndCircumstances details = new DetailsAndCircumstances(driver);
		PoliceContacted contacted = new PoliceContacted(driver);
		Involved involve = new Involved(driver);
		ReportPreparation report = new ReportPreparation(driver);		
		User user = new User(driver);
		SearchTables table = new SearchTables(driver);
		Journal journal = new Journal(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
//		user.changeUserType(user.depart1, "Correctional Services Oversight & Investigations");
		
		search.searchIncidentReport("28471");
		search.openIncidentReport("28471");
		
		Summary sum = new Summary(driver);
		sum.verifyPage();
		
//		sum.verifyNotificationLocation("ALGOMA TREATMENT & REMAND CTR-ADULT");
//		sum.verifyNotificationArea("Recreation");
//		sum.verifyNotificationUnitRange("");
		
//		sum.editNotification();
		
//		notificationFields.updateLocation("BROCKVILLE JAIL - ADULT (Institution)");
		
		
//		sum.verifyIncidentTypesNotVisible("Assault/contact");
//		Assert.assertEquals(sum.editIncidentType(), false);
//		Assert.assertEquals(sum.editInvolved(), false);
//		Assert.assertEquals(sum.editNotification(), false);
//		Assert.assertEquals(sum.editRegionalOfficeDetails(), false);
//		Assert.assertEquals(sum.editReportPreparation(), false);
		
//		sum.editInvolved();
//		involve.addInmateByName("JOHN", "SMITH", "Witness", "1", true);
//		involve.addEmployee("Mark", "Belleza", "Other", "2", true);
//		involve.addOthers("Mark3", "Belleza3", "Vendor", "Participant", "3", false);
//		involve.addOthers("Lite", "Bell", "Visitor", "Other", "2", false);
//		String currDate = DateTimeUI.getCurrentDate(""); // dd/MM/yyyy
//		
//		involve.searchInmate("JOHN", "SMITH");
//		table.selectUserFromTable("JOHN", "SMITH");
//		String currTimeInmateSelected = DateTimeUI.getCurrentTime(""); // "hh:mm"
//		System.out.println("Time inmate selected: " + currTimeInmateSelected);
//		
//		involve.editInmateByName("JOHN", "SMITH", "Participant", "2", true);
//		String currTimeInmateInfoAddded = DateTimeUI.getCurrentTime(""); // "hh:mm"
//		System.out.println("Time inmate info added: " + currTimeInmateInfoAddded);
//		
//		involve.clickUpdate();
		
		sum.openChangeJournal();
		journal.verifyPage();
		journal.selectRowsPerPage("100");
		
		journal.verifyInvolvedInmate("17/03/2025", "11:49", "SMITH, JOHN", "1001292722");
		journal.verifyInvolvedInmateDetails("17/03/2025", "11:49", "2", "True", "Participant");
		journal.verifyInvolvedInmateTotal("17/03/2025", "11:49", "3", "7", "3", "26", "2", "4");
		
		journal.verifyInvolvedInmateDetails("17/03/2025", "01:19", "3", "", "");
		journal.verifyInvolvedInmateTotal("17/03/2025", "01:19", "", "", "5", "28", "", "");
		
		
		journal.verifyInvolvedEmployee("17/03/2025", "01:23", "Dao, Richard", "", "", "YJS - Brookside Youth Centre - Cobourg");
		journal.verifyInvolvedEmployeeDetails("17/03/2025", "01:23", "1", "True", "Participant");
		journal.verifyInvolvedEmployeeTotal("17/03/2025", "01:23", "2", "8", "13", "29", "1", "5");
		
		journal.verifyInvolvedOthers("17/03/2025", "02:23", "Yeager, Eren", "1",  "True", "Participant", "Visitor");
		journal.verifyInvolvedOthersTotal("17/03/2025", "02:24", "4", "9", "12", "30", "3", "6");
		
//		involve.deleteInmateByName("JOHN", "SMITH");
//		involve.deleteEmployee("Mark", "Belleza");
//		involve.deleteEmployee("Richard", "Dao");
//		involve.deleteOther("Mark", "Belleza");
		
//		involve.editInmateByName("JOHN", "SMITH", "Other", "4", false);
//		involve.editEmployee("Mark", "Belleza", "Witness", "4", false);
//		involve.editOther("Lite", "Bell", "Visitor", "Participant", "6", true);
		
//		involve.clickUpdate();
		
//		sum.verifyInmateByNameInInvolved("JOHN", "SMITH", "Other");
//		sum.verifyInmateByNameNotInvolved("JOHN2", "SMITH3", "Other");
//		sum.verifyEmployeeInInvolved("Mark", "Belleza", "Witness"); 	
//		sum.verifyEmployeeNotInvolved("dsfsf", "sdfsd", "Other");
//		sum.verifyOtherInInvolved("Lite", "Bell", "Participant");
//		regionalFields.selectRegionalOfficer("Belleza", "Mark");
//		regionalFields.clickUpdate();
		
	}
	
	@Test(groups="testing")
	public void b() {
		EdgeDriver driver = new EdgeDriver();
		WebUtils utils = new WebUtils(driver);
		WebUtils.setUpIrmPage(driver);
		
		ReportSearch search = new ReportSearch(driver);
		NavBar nav = new NavBar(driver);
		Notification notificationFields = new Notification(driver);
		DateTimeUI date = new DateTimeUI(driver);
		RegionalOfficeDetails regionalFields = new RegionalOfficeDetails(driver);
		IsMediaAware mediaFields = new IsMediaAware(driver);
		IncidentTypeSelection incidentFields = new IncidentTypeSelection(driver);
		StandardItemChecklist checklist = new StandardItemChecklist(driver);
		SupportingDocuments support = new SupportingDocuments(driver);
		DetailsAndCircumstances details = new DetailsAndCircumstances(driver);
		PoliceContacted contacted = new PoliceContacted(driver);
		Involved involve = new Involved(driver);
		ReportPreparation report = new ReportPreparation(driver);		
		User user = new User(driver);
		Journal journal = new Journal(driver);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		search.searchIncidentReport("29098");
		search.openIncidentReport("29098");
		
//		search.searchIncidentReport("29839");
//		search.openIncidentReport("29839");
		
		Summary sum = new Summary(driver);
		sum.verifyPage();
//		sum.editDetailsAndCircumstances();
//		details.addIIRDetails("Something");
		
//		sum.verifyChecklistItem("Assault", "Advised of right to pursue/decline criminal charges");
//		sum.verifySupportingDocument("Death of Staff", "UploadFileTest.docx");
//		sum.verifyDetailsCircumstances("EOIR", "fsd");
//		
//		sum.editIncidentType();
//		incidentFields.verifyPage();
//		incidentFields.expandItem("IIR");
//		incidentFields.selectItem("Assault");  //Remove Assault
//		
//		incidentFields.expandItem("Threat");
//		incidentFields.expandItem("(P2) Inmate on Inmate");
//		incidentFields.selectItem("Verbal");   //Add Verbal
//		sum.editPoliceContacted();
//		contacted.verifyPage();
//		contacted.selectPoliceContacted("Yes");
//		contacted.selectWillPoliceBeAttending("Yes");
//		contacted.selectCriminalCharges("Yes");
//		contacted.fillPersonContacted("Testing");
//		contacted.fillPersonContactedBy("Mark Belleza");
//		contacted.fillPoliceCase("9");
//		contacted.fillPoliceContactedMethod("email");
//		contacted.fillPoliceService("Hello world");
//		contacted.fillPoliceTelephone("6471231234");
//		contacted.clickUpdate();
		
		
//		sum.editSupportingDocuments();
//		support.verifyPage();
//		support.uploadFile("Death of Staff", "Field Visit Report", "UploadFileTest.docx");
//		support.uploadFile("External Media Inquiries", "Copy of media article", "UploadFileTest2.docx");
		

//		sum.editStandardItemChecklist();
//		checklist.verifyPage();
//		checklist.expandItem("IIR");
//		checklist.expandItem("Assault");
//		checklist.selectChecklistItem("Advised of right to pursue/decline criminal charges", "Yes");
//		checklist.selectChecklistItem("CCRL notified if racially motivated", "No");
		
		sum.openChangeJournal();
		journal.verifyPage();
		journal.selectRowsPerPage("100");
		
//		journal.verifyInvolvedEmployee("12/03/2025", "10:04:19", "Dao, Richard", "", "", "YJS - Brookside Youth Centre - Cobourg");
//		journal.verifyInvolvedEmployeeDetails("12/03/2025", "10:04:24", "1", "True", "Witness");
//		journal.verifyInvolvedEmployeeTotal("12/03/2025", "10:04:24", "2", "5", "2", "5", "2", "5" );
		
//		journal.verifyInvolvedInmate("12/03/2025", "03:14:40", "PETERS, STEVEN", "");
//		journal.verifyInvolvedInmateDetails("13/03/2025", "11:58:29", "1", "True", "Participant");
//		journal.verifyInvolvedInmateTotal("13/03/2025", "11:58:29", "5", "13", "4", "12", "4", "11");
		
//		journal.verifyInvolvedOthers("12/03/2025", "03:41:03", "Yanami, Lite", "1", "True", "Other", "Volunteer");
//		journal.verifyInvolvedOthersTotal("12/03/2025", "03:41:49", "2", "8", "2", "7", "2", "7");
		
		journal.verifyPoliceContacted("19/02/2025", "11:27:01", "True", "", "False", "True", "2/19/2025", "12:00:00");
		journal.verifyPoliceContacted2("19/02/2025", "11:27:01", "Mark", "Julianne Smit-Brousseau", "servrice", "phone", "(213) 231 2112", "3");
//		
//		journal.verifySupportingDocument("05/03/2025", "02:46:16", "Death of Staff", "MOL Order", "OHRC_Certificate.pdf");
		
//		System.out.println(DateTimeUI.getCurrentDate("full"));
//		System.out.println(DateTimeUI.getCurrentDate(""));		
//		System.out.println(DateTimeUI.getCurrentTime("full"));
//		System.out.println(DateTimeUI.getCurrentTime(""));
//		
//		String currDate = DateTimeUI.getCurrentDate("partial");
//		String currTime = DateTimeUI.getCurrentTime("partial");
		
//		journal.selectRowsPerPage("100");
		journal.verifyIncident("19/02/2025", "11:25:23", "2/17/2025", "12:00:00", "ALGOMA TREATMENT & REMAND CTR-ADULT", "Two", "Washroom", "", "");
		journal.verifyRegionalOfficeContacted("19/02/2025", "11:25:35", "Phone", "", "2/18/2025", "12:00:00", "Keven Szaruga");
//		journal.verifyMediaAware("25/02/2025", "10:21:35", "Yes", "CCTV");
//		journal.verifyMediaAware("19/02/2025", "11:25:37", "No", "");
//		journal.verifyInvolvedIncidentTypes("19/02/2025", "11:25:57", "Throwing a small item (e.g. apple, styrofoam up)");
//		journal.verifyIncidentTypeAnswers("19/02/2025", "11:26:07", "Assault", "CCRL notified if racially motivated");
//		journal.verifyIncidentTypeAnswers("19/02/2025", "11:26:07", "Death of Staff", "Details and circumstances of incident");
//		journal.verifyIncidentTypeAnswers("19/02/2025", "11:26:07", "Labour Activities", "Local union consultation");
	}
	
}
