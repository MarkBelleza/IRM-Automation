package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Summary {
	
	By sections = By.xpath("//div[@class='mud-paper mud-paper-outlined mud-card ma-4']");
	By sectionsHeader = By.xpath("//div[@class='mud-typography mud-typography-h6']");
	By sectionsEditButton = By.xpath("//div[@class='mud-card-header-actions']");
	By sectionsContentContainer = By.xpath("//div[@class='mud-card-content']");
	By sectionsContent = By.xpath("//p[@class='mud-typography mud-typography-body1']");
	
	By button = By.xpath("//button[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-icon']");
	By buttonIncidentType = By.xpath("/html/body/div[3]/div/div/div[5]/div[1]/div[2]/button");
	By buttonReportPreparation = By.xpath("/html/body/div[3]/div/div/div[11]/div[1]/div[2]/button");
	By buttonRegionalOffice = By.xpath("/html/body/div[3]/div/div/div[3]/div[1]/div[2]/button");
	By buttonInvolved = By.xpath("/html/body/div[3]/div/div/div[10]/div[1]/div[2]/button");
	By buttonStandItemChecklist = By.xpath("/html/body/div[3]/div/div/div[6]/div[1]/div[2]/button");
	By buttonNotification = By.xpath("/html/body/div[3]/div/div/div[2]/div[1]/div[2]/button");
	By buttonSupportingDocuments = By.xpath("/html/body/div[3]/div/div/div[7]/div[1]/div[2]/button");
	By buttonPoliceContacted = By.xpath("/html/body/div[3]/div/div/div[9]/div[1]/div[2]/button");
	By buttonDetailsCircumstances = By.xpath("/html/body/div[3]/div/div/div[8]/div[1]/div[2]/button");
	By changeJournalButton = By.xpath("//button[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-icon' and @title='Open Change Journal']");
	
	By notificationArea = By.xpath("/html/body/div[3]/div/div/div[2]/div[2]/div/div[7]/p");
	By notificationLocation = By.xpath("/html/body/div[3]/div/div/div[2]/div[2]/div/div[5]/p");
	By notificationUnitRange = By.xpath("/html/body/div[3]/div/div/div[2]/div[2]/div/div[8]/p");
	
	String header = "mud-typography mud-typography-h6";
	
	WebDriver driver;
	WebDriverWait wait;
	
	public Summary(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}
	
	public void verifyPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Summary for Incident ID :')]")));
	}
	
	public boolean editNotification() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Notification')]/../../..")));
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait2.until(ExpectedConditions.elementToBeClickable(buttonNotification))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editIncidentType() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Incident Type Selection Summary')]/../../..")));
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait2.until(ExpectedConditions.elementToBeClickable(buttonIncidentType))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editReportPreparation() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Report Preparation')]/../../..")));
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait2.until(ExpectedConditions.elementToBeClickable(buttonReportPreparation))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editRegionalOfficeDetails() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Regional Office Details')]/../../..")));
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait2.until(ExpectedConditions.elementToBeClickable(buttonRegionalOffice))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editInvolved() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Involved')]/../../..")));
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait2.until(ExpectedConditions.elementToBeClickable(buttonInvolved))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editStandardItemChecklist() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Standard Item Checklist')]/../../..")));
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait2.until(ExpectedConditions.elementToBeClickable(buttonStandItemChecklist))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editSupportingDocuments() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Supporting Documents')]/../../..")));
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait2.until(ExpectedConditions.elementToBeClickable(buttonSupportingDocuments))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editDetailsAndCircumstances() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Details and Circumstances of Incident')]/../../..")));
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait2.until(ExpectedConditions.elementToBeClickable(buttonDetailsCircumstances))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editPoliceContacted() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Police Contacted')]/../../..")));
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait2.until(ExpectedConditions.elementToBeClickable(buttonPoliceContacted))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public void verifyNotificationLocation(String location) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Notification')]/../../..")));
		
		String content = wait.until(ExpectedConditions.visibilityOfElementLocated(notificationLocation)).getText();
		System.out.println(content);
		if (!content.equals("Facility : " + location)) {
			throw new NoSuchElementException("Location: " + location + " not found in Notification.");
		}
	}
	
	public void verifyNotificationArea(String area) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Notification')]/../../..")));
		
		String content = wait.until(ExpectedConditions.visibilityOfElementLocated(notificationArea)).getText();
		System.out.println(content);
		if (!content.equals("Area : " + area)) {
			throw new NoSuchElementException("Area: " + area + " not found in Notification.");
		}
	}
	
	public void verifyNotificationUnitRange(String unit) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Notification')]/../../..")));
		
		String content = wait.until(ExpectedConditions.visibilityOfElementLocated(notificationUnitRange)).getText();
		System.out.println(content);
		
		String header = "Unit Range : ";
		if (unit.length() == 0) {
			header = "Unit Range :";
		}

		if (!content.equals(header + unit)) {
			throw new NoSuchElementException("Unit Range: " + unit + " not found in Notification.");
		}
	}
	
	
	public void verifyIncidentTypes(String incidentTypes) {
		WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Incident Type Selection Summary')]/../../..")));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(section).perform();
		
//		Get the textContent
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String textContent = (String) jsExecutor.executeScript("return arguments[0].textContent;", section);
		
		if (!textContent.contains(incidentTypes)) {
			throw new NoSuchElementException(incidentTypes + " not found.");
		}
	}
	
	public void verifyIncidentTypesNotVisible(String incidentTypes) {
		WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Incident Type Selection Summary')]/../../..")));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(section).perform();
		
//		Get the textContent
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String textContent = (String) jsExecutor.executeScript("return arguments[0].textContent;", section);
		
		if (textContent.contains(incidentTypes)) {
			throw new NoSuchElementException(incidentTypes + " is visible, but should not be.");
		}
	}
	
	/**
	  * Verify Inmate is visible in summary view (NOTE: Assuming each person's name in "Inmate" are unique)
	  *
	  * @param firstName
	  * @param lastName
	  * @param role
	  */
	public void verifyInmateByNameInInvolved(String firstName, String lastName, String role) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Involved')]/../../..")));
		
		try {		
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Name']/following-sibling::td[@data-label='OTSID']/following-sibling::td[text()='" + role + "']/ancestor::tr")));
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Inmate with name " + firstName + " " + lastName + " not found in Summary View.");
		}
	}
	
	/**
	  * Verify Inmate is NOT visible in summary view (NOTE: Assuming each person's name in "Inmate" are unique)
	  *
	  * @param firstName
	  * @param lastName
	  * @param role
	  */
	public void verifyInmateByNameNotInvolved(String firstName, String lastName, String role) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Involved')]/../../..")));
		
		try {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Name']/following-sibling::td[@data-label='OTSID']/following-sibling::td[text()='" + role + "']/ancestor::tr")));
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Inmate with name " + firstName + " " + lastName + " found in Summary view, but expected to not be visible.");
		}
	}
	
	/**
	  * Verify Employee is visible in summary view (NOTE: Assuming each person's name in "Employee" are unique)
	  *
	  * @param firstName
	  * @param lastName
	  * @param role
	  */
	public void verifyEmployeeInInvolved(String firstName, String lastName, String role) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Involved')]/../../..")));
		
		try {			
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/following-sibling::td[@data-label='Location']/following-sibling::td[text()='" + role + "']/ancestor::tr")));
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Employee with name " + firstName + " " + lastName + " not found in Summary View.");
		}
	}
	
	/**
	  * Verify Employee is NOT visible in summary view (NOTE: Assuming each person's name in "Employee" are unique)
	  *
	  * @param firstName
	  * @param lastName
	  * @param role
	  */
	public void verifyEmployeeNotInvolved(String firstName, String lastName, String role) {	
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Involved')]/../../..")));
		
		try {			
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/following-sibling::td[@data-label='Location']/following-sibling::td[text()='" + role + "']/ancestor::tr")));
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Employee with name " + firstName + " " + lastName + " found in Summary view, but expected to not be visible.");
		}
	}
	
	/**
	  * Verify Other is visible in summary view (NOTE: Assuming each person's name in "Other" are unique)
	  *
	  * @param firstName
	  * @param lastName
	  * @param role
	  */
	public void verifyOtherInInvolved(String firstName, String lastName, String role) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Involved')]/../../..")));
		
		try {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/ancestor::tr/td[3 and text()='" + role + "']/ancestor::tr")));
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Other with name " + firstName + " " + lastName + " not found.");
		}
	}
	
	/**
	  * Verify Other is NOT visible in summary view (NOTE: Assuming each person's name in "Other" are unique)
	  *
	  * @param firstName
	  * @param lastName
	  * @param role
	  */
	public void verifyOtherNotInvolved(String firstName, String lastName, String role) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Involved')]/../../..")));
		
		try {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/ancestor::tr/td[3 and text()='" + role + "']/ancestor::tr")));
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Other with name " + firstName + " " + lastName + " found in Summary view, but expected to not be visible.");	
		}
	}
	
	public void openChangeJournal() {
		verifyPage();
		
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(changeJournalButton))).click().perform();
	}

}
