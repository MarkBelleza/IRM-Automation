package UI;

import java.time.Duration;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
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
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		search.searchIncidentReport("28471");
		search.openIncidentReport("28471");
		
		Summary sum = new Summary(driver);
		
		sum.editInvolved();
//		involve.selectInmateByName("JOHN", "SMITH", "Witness", "1", true);
//		involve.selectEmployee("Mark", "Belleza", "Other", "2", true);
//		involve.addOthers("Mark", "Belleza", "Vendor", "Participant", "3", true);
		involve.deleteInmateByName("JOHN", "SMITH");
		involve.deleteEmployee("Mark", "Belleza");
		involve.deleteOther("Mark", "Belleza");
		involve.clickUpdate();
//		regionalFields.selectRegionalOfficer("Belleza", "Mark");
//		regionalFields.clickUpdate();
		
	}
	
}
