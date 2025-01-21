package ca.IRM.selenium.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ca.IRM.selenium.components.SearchTables;

public class RegionalOfficeDetails {
	
	WebDriver driver;
	WebDriverWait wait;
	
	By headerPage = By.xpath("//h6[@class='mud-typography mud-typography-h6']");
	By contactedViaField = By.xpath("//div[@class='mud-input mud-input-outlined mud-input-adorned-end mud-select-input']");
	By regionalOfficeLastName = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div/div[5]/div/div/div/input");
	By regionalOfficeFirstName = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div/div[6]/div/div/div/input");
	By searchNameButton = By.xpath("//span[@class='mud-button-label' and text()='Search']");
	By tableBody = By.xpath("//tbody[@class='mud-table-body']");
	By tableRow = By.xpath("//tr[@class='mud-table-row']");
	By tableCell = By.xpath("//td[@class='mud-table-cell']");
	By tableSelectButton = By.xpath("//button[@class='mud-button-root mud-fab mud-fab-primary mud-fab-size-small mud-ripple']");
	By regionalOfficeNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div/div[9]/div/div/div/div[1]");
	By lastNameCell = By.xpath("//td[@data-label='LastName']");
	By firstNameCell = By.xpath("//td[@data-label='FirstName']");
	By othersField = By.xpath("//textarea[contains(@class, 'mud-input-slot') and @rows='3']");
	
	
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	By updateButton = By.xpath("//span[@class='mud-button-label' and text()='Update']");
	By cancelButton = By.xpath("//span[@class='mud-button-label' and text()='Cancel']");
	
	String contactedViaDropdown = "mud-typography";
	String tableSelectButtonClass = "mud-button-root mud-fab mud-fab-primary mud-fab-size-small mud-ripple";
	
	public RegionalOfficeDetails(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='mud-typography mud-typography-h6' and contains(text(), 'Regional Office Details')]")));
//		wait.until(ExpectedConditions.visibilityOfElementLocated(headerPage)).getText().contains("Regional Office Details");
//		System.out.println("In Regional Office Details page");
	}
	
	public String getIncidentID() {
		String[] info = wait.until(ExpectedConditions.visibilityOfElementLocated(headerPage)).getText().split(" ");
		String IncidentID = info[info.length - 1];
		return IncidentID;
	}
			
	public void selectRegionalOfficeContactedVia(String contacted) {
		verifyPage();
		wait.until(ExpectedConditions.elementToBeClickable(contactedViaField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(@class, '" + contactedViaDropdown +  "') and text()='" + contacted + "']"))).click();
	}
	
	public void selectRegionalOfficer(String last, String first) {
		verifyPage();
		wait.until(ExpectedConditions.elementToBeClickable(regionalOfficeLastName)).click();
		wait.until(ExpectedConditions.elementToBeClickable(regionalOfficeLastName)).sendKeys(last);

		wait.until(ExpectedConditions.elementToBeClickable(regionalOfficeFirstName)).click();
		wait.until(ExpectedConditions.elementToBeClickable(regionalOfficeFirstName)).sendKeys(first);

		wait.until(ExpectedConditions.elementToBeClickable(searchNameButton)).click();
		
		SearchTables table = new SearchTables(driver);
		table.selectUserFromTable(first, last);
		
//		Verify regional office name is visible and correct
		String expectedName = first + " " + last;
		String actualName = wait.until(ExpectedConditions.visibilityOfElementLocated(regionalOfficeNameField)).getText();
		
		Assert.assertEquals(expectedName, actualName);
	}
	
	public void fillAdditionalDetails(String details) {
		verifyPage();
		wait.until(ExpectedConditions.elementToBeClickable(othersField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(othersField)).sendKeys(details);
	}
	
	public void clickNext() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(nextButton))).click().perform();
	}
	
	public void clickPrevious() {
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(previousButton)).click().perform();
	}
	
	public void clickUpdate() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(updateButton))).click().perform();
	}
	
	public void clickCancel() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(cancelButton))).click().perform();
	}
	
}
