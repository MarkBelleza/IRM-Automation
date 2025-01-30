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
	
	public boolean editIncidentType() {
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(buttonIncidentType))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editReportPreparation() {
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(buttonReportPreparation))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editRegionalOfficeDetails() {
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(buttonRegionalOffice))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean editInvolved() {
		Actions actions = new Actions(driver);
		try {
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(buttonInvolved))).click().perform();
		}catch(TimeoutException e) {
			return false;
		}
		return true;
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
		Actions actions = new Actions(driver);
		
		try {		
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Name']/following-sibling::td[@data-label='OTSID']/following-sibling::td[text()='" + role + "']/ancestor::tr")))).perform();
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
		Actions actions = new Actions(driver);
		
		try {			
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/following-sibling::td[@data-label='Location']/following-sibling::td[text()='" + role + "']/ancestor::tr")))).perform();
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
		Actions actions = new Actions(driver);
		
		try {
			
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/ancestor::tr/td[3 and text()='" + role + "']/ancestor::tr")))).perform();
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
		try {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/ancestor::tr/td[3 and text()='" + role + "']/ancestor::tr")));
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Other with name " + firstName + " " + lastName + " found in Summary view, but expected to not be visible.");	
		}
	}
	

}
