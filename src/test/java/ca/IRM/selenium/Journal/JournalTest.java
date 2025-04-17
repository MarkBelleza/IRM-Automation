package ca.IRM.selenium.Journal;

import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
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
import ca.IRM.selenium.utils.WebUtils;

public class JournalTest {
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
	private SearchTables table;
	
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
		table = new SearchTables(driver);
		
		WebUtils.setUpIrmPage(driver);
		
//		Set user to Staff Sergeant and ALGOMA
//		user.changeUserType(user.staff, user.algo);
//		System.out.println("Before Test USKJDSFJFFSD");		
	}
	
	
//	@AfterTest(groups="testing")
//	public void close() {
//		driver.quit();
//		System.out.println("After Test");
//	}

	
	
//	TestCase ID: 
	@Test(groups="testing")
	public void addInvolved() {
		nav.createNewReport();
		
//		Fill in the appropriate fields in Notification (set location to within the user location, ALGOMA)
		notificationFields.selectPriority("One");
		notificationFields.selectLocation("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)");
		notificationFields.selectArea("Washroom");
		notificationFields.clickNext();
		
		String currDate = DateTimeUI.getCurrentDate(""); // dd/MM/yyyy
		String currDate2 = DateTimeUI.getCurrentDate("calendar"); // M/dd/yyyy
		String currTimeNoti = DateTimeUI.getCurrentTime(""); // "hh:mm"
		System.out.println(currTimeNoti);
		
//		** Store the Incident Report ID
		regionalFields.verifyPage();
		String IncidentID = regionalFields.getIncidentID();
		System.out.println("Created Incident ID: " + IncidentID);
		
		regionalFields.selectRegionalOfficeContactedVia("Phone");
		regionalFields.selectRegionalOfficer("Belleza", "Mark");
		regionalFields.clickNext();
		
		String currTimeRegional = DateTimeUI.getCurrentTime(""); // "hh:mm"
		System.out.println(currTimeNoti);
		
		mediaFields.verifyPage();
		mediaFields.selectYes("CCTV");
		mediaFields.clickNext();
		
		String currTimeMedia = DateTimeUI.getCurrentTime(""); // "hh:mm"
		System.out.println(currTimeNoti);
		
		incidentFields.verifyPage();
		incidentFields.expandItem("IIR");
		incidentFields.expandItem("Assault");
		incidentFields.expandItem("(P1) Serious Inmate on Inmate");
		incidentFields.expandItem("Item thrown/contact");
		incidentFields.selectItem("Bodily substance");
		
		incidentFields.expandItem("EOIR");
		incidentFields.expandItem("Death of Staff");
		incidentFields.selectItem("Off Duty");
		incidentFields.clickNext();
		
		String currTimeIncident = DateTimeUI.getCurrentTime(""); // "hh:mm"
		System.out.println(currTimeIncident);
		
		checklist.verifyPage();
		checklist.expandItem("IIR");
		checklist.expandItem("Assault");
		checklist.selectChecklistItem("Advised of right to pursue/decline criminal charges", "Yes");
		checklist.selectChecklistItem("CCRL notified if racially motivated", "Yes");
		checklist.clickNext();
		
		String currTimeChecklist = DateTimeUI.getCurrentTime(""); // "hh:mm"
		System.out.println(currTimeChecklist);
		
		support.verifyPage();
		support.uploadFile("Death of Staff", "MOL Order", "UploadFileTest.docx");
		support.clickNext();
		
		String currTimeSupport = DateTimeUI.getCurrentTime(""); // "hh:mm"
		System.out.println(currTimeSupport);
		
		details.verifyPage();
		details.clickNext();
		
		contacted.verifyPage();
		contacted.selectPoliceContacted("Yes");
		contacted.selectWillPoliceBeAttending("Yes");
//		contacted.selectCriminalCharges("Unknown"); //Unknown already selected by default
		contacted.fillPersonContacted("Mark");
		contacted.fillPersonContactedBy("Kathy Travis");
		contacted.fillPoliceService("SomeService");
		contacted.fillPoliceContactedMethod("Phone");
		contacted.fillPoliceTelephone("1111111111");
		contacted.fillPoliceCase("4");
		contacted.clickNext();
		
		String currTimeContacted = DateTimeUI.getCurrentTime(""); // "hh:mm"
		System.out.println(currTimeContacted);
		
		involve.verifyPage();
		
//		Add multiple Inmates, Employees and Others
		
//		involve.searchInmate("JOHN", "SMITH");
//		table.selectUserFromTable("JOHN", "SMITH");
//		String currTimeInmateSelected = DateTimeUI.getCurrentTime(""); // "hh:mm"
//		System.out.println("Time inmate selected: " + currTimeInmateSelected);
//		
//		involve.editInmateByName("JOHN", "SMITH", "Participant", "2", true);
//		String currTimeInmateInfoAddded = DateTimeUI.getCurrentTime(""); // "hh:mm"
//		System.out.println("Time inmate info added: " + currTimeInmateInfoAddded);
	
	
//		TODO: Known bug, when adding an Other, the Journal page will not display the Total numbers (only after another involved person is added the total is fixed)
//		involve.addOthers("Will", "Lyan", "Vendor", "Participant", "0", false);
//		String currTimeOther1 = DateTimeUI.getCurrentTime(""); // "hh:mm"
//		
//		involve.addOthers("Jason", "Smith", "AgencyStaff", "Other", "1", true);
//		String currTimeOther2 = DateTimeUI.getCurrentTime(""); // "hh:mm"
//		
//		involve.addOthers("Mark", "Bell", "Visitor", "Witness", "0", false);
//		String currTimeOther3 = DateTimeUI.getCurrentTime(""); // "hh:mm"
//		
//		involve.addOthers("Julian", "Da", "Volunteer", "Participant", "2", true);
//		String currTimeOther4 = DateTimeUI.getCurrentTime(""); // "hh:mm"
//	
//		involve.addOthers("May", "Silva", "Other", "Other", "3", false);
//		String currTimeOther5 = DateTimeUI.getCurrentTime(""); // "hh:mm"
//		
//		String[][] emp = {
//				{"Mark Belleza", "Other 2 true"},
//				{"Derek Dao", "Witness 3 true"},
//				{"Travis Wong", "Participant 3 false"},
//				{"Roy Franck", "Other 1 true"},
//				{"Cathy Marcotte", "Witness 2 false"}
//			};
//		String[][] empTime = new String[5][2];
//		
//		for (int i = 0; i < emp.length; i++) {
//			String name = emp[i][0];
//			String info = emp[i][1];
//			String[] fullName = name.split(" ");
//			String[] details = info.split(" ");
//			
//			involve.searchEmployee(fullName[0], fullName[1]);
//			table.selectUserFromTable(fullName[0], fullName[1]);
//			empTime[i][0] = DateTimeUI.getCurrentTime(""); // "hh:mm"
//			
//			involve.editEmployee(fullName[0], fullName[1] , details[0], details[1], Boolean.parseBoolean(details[2]));
//			empTime[i][1] = DateTimeUI.getCurrentTime(""); // "hh:mm"
//		}
		
//		Add 5 inmates in the Involved section
//		String[][] inmate = {
//				{"JOHN SMITH", "Witness 4 true"},
//				{"WILLIAM BEST", "Other 3 true"},
//				{"AARON VASSCOUNT", "Participant 1 true"},
//				{"SMITH TEST", "Witness 0 false"},
//				{"KENO MARTYN", "Other 2 false"}
//			};
//		
//		String[][] inmateTime = new String[5][2];
//		
//		for (int i = 0; i < inmate.length; i++) {
//			String name = inmate[i][0];
//			String info = inmate[i][1];
//			String[] fullName = name.split(" ");
//			String[] details = info.split(" ");
//			
//			involve.searchInmate(fullName[0], fullName[1]);
//			table.selectUserFromTable(fullName[0], fullName[1]);
//			inmateTime[i][0] = DateTimeUI.getCurrentTime(""); // "hh:mm"
//			
//			involve.editInmateByName(fullName[0], fullName[1] , details[0], details[1], Boolean.parseBoolean(details[2]));
//			inmateTime[i][1] = DateTimeUI.getCurrentTime(""); // "hh:mm"
//		}
		
//		Verify Incident Report is saved
		search.searchIncidentReport(IncidentID);
		search.openIncidentReport(IncidentID);
		
//		Verify change Journal
		Summary summary = new Summary(driver);
		Journal journal = new Journal(driver);
		
		summary.openChangeJournal();
		journal.verifyPage();
		journal.selectRowsPerPage("100");
		
		journal.verifyIncident(currDate, currTimeNoti, currDate2, "12:00", "ALGOMA TREATMENT & REMAND CTR-ADULT","One", "Washroom", "", "");
		journal.verifyRegionalOfficeContacted(currDate, currTimeRegional, "Phone", "", currDate2, "12:00", "Mark Belleza");
		journal.verifyMediaAware(currDate, currTimeMedia, "Yes", "CCTV");
		
		String[] incidents = {"IIR", "Assault", 
				"(P1) Serious Inmate on Inmate",
				"Item thrown/contact",
				"Bodily substance", "EOIR", 
				"Death of Staff",
				"Off Duty"};
		
		for (String incident : incidents) {			
			journal.verifyInvolvedIncidentTypes(currDate, currTimeIncident, incident);
		}
		
		journal.verifyIncidentTypeAnswers(currDate, currTimeChecklist, "Assault", "Advised of right to pursue/decline criminal charges");
		journal.verifyIncidentTypeAnswers(currDate, currTimeChecklist, "Assault", "CCRL notified if racially motivated");
		journal.verifySupportingDocument(currDate, currTimeSupport, "Death of Staff", "MOL Order", "UploadFileTest.docx");
		journal.verifyPoliceContacted(currDate, currTimeContacted, "True", "", "True", "", "", "");
		journal.verifyPoliceContacted2(currDate, currTimeContacted, "Mark", "Kathy Travis", "SomeService", "Phone", "1111111111", "4");
		
////		Verify Inmates added
//		int totalInvolved = 0;
//		int totalDosesAdministered = 0;
//		int totalHospitalized = 0;
//		
//		int totalInmateInvolved = 0;
//		int totalInmatesDosesAdministered = 0;;
//		int totalInmatesHospitalized = 0;;
//		
//		for (int i = 0; i < inmate.length; i++) {
////			reference.. {"JOHN SMITH", "Witness 4 true"}
//			String[] fullName = inmate[i][0].split(" ");
//			String[] details = inmate[i][1].split(" ");
//				
////			Count the total numbers
//			totalInmateInvolved++; 
//			totalInvolved++;
//			
//			String dummyTotalH = ""; String dummyTotalInmateH = ""; String dummyTotalD = ""; String dummyTotalInmateD = "";
//			String hosp = "";
//			
////			TODO: if hospitalized or doses are false or 0 respectively, they will not be displayed in the Journal, since their counts did not change
//			if (details[2].equals("true")) {
//				
//				totalHospitalized++; 
//				totalInmatesHospitalized++;
//				
//				dummyTotalInmateH = String.valueOf(totalInmatesHospitalized);
//				dummyTotalH = String.valueOf(totalHospitalized);
//				
////				Capitalized the first char of "true"
//				hosp = details[2].substring(0, 1).toUpperCase() + details[2].substring(1); 
//			}
//			
//			if (details[1].equals("0")) details[1] = "";
//			else {
//				
//				totalInmatesDosesAdministered += Integer.parseInt(details[1]); 
//				totalDosesAdministered += Integer.parseInt(details[1]);
//				
//				dummyTotalInmateD = String.valueOf(totalInmatesDosesAdministered);
//				dummyTotalD = String.valueOf(totalDosesAdministered);
//			}
//			
//			journal.verifyInvolvedInmate(currDate, inmateTime[i][0], fullName[1] + ", " + fullName[0], "");
//			journal.verifyInvolvedInmateDetails(currDate, inmateTime[i][1], details[1], hosp, details[0]);
//			journal.verifyInvolvedInmateTotal(currDate, inmateTime[i][1], 
//					String.valueOf(totalInmateInvolved), 
//					String.valueOf(totalInvolved), 
//					dummyTotalInmateD, 
//					dummyTotalD, 
//					dummyTotalInmateH, 
//					dummyTotalH);
//		}
	}
	
}
