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

import ca.IRM.selenium.components.SearchTables;

public class ReportPreparation {

	WebDriver driver;
	WebDriverWait wait;
	
	By contactPerson = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div/div[3]/div/div[1]/validation/div/div[1]/div/input");
	By searchIconButton = By.xpath("//button[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-icon mud-icon-button-edge-end']");
	By tableBody = By.xpath("//tbody[@class='mud-table-body']");
	By tableSelectButton = By.xpath("//button[@class='mud-button-root mud-fab mud-fab-primary mud-fab-size-small mud-ripple']");
	By tableRow = By.xpath("//tr[@class='mud-table-row']");
	
	By radioButtonFinalYes = By.xpath("//span[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-radio mud-tertiary-text hover:mud-tertiary-hover']");
	By radioButtonFinalNo = By.xpath("//span[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-radio mud-secondary-text hover:mud-secondary-hover']");
	By selectedRadioButtonFinalYes = By.xpath("//span[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-radio mud-tertiary-text hover:mud-tertiary-hover mud-checked']");
	
	By confidentialPrompt = By.xpath("//p[@class='mud-typography mud-typography-body1' and text()='Confidential Incident Report']");
	By radioButtonConfidentialNotSelected = By.xpath("//span[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-radio mud-primary-text hover:mud-primary-hover']");
	By radioButtonConfidentialSelected = By.xpath("//span[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-radio mud-primary-text hover:mud-primary-hover mud-checked']");
	By radioButtonConfidentialUnmarkable = By.xpath("//span[@class='mud-button-root mud-icon-button mud-primary-text hover:mud-primary-hover mud-disabled mud-checked']");
	
	
	By selectedRadioButton = By.xpath("//span[@class='mud-radio-icons mud-checked']");
	
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By submitButton = By.xpath("//span[@class='mud-button-label' and text()='Submit']");
	By submitButtonDisabled = By.xpath("//button[@class='mud-button-root mud-button mud-button-filled mud-button-filled-primary mud-button-filled-size-medium mud-ripple' and @disabled]");
	
	String selectedRadioButtonS = "//span[@class='mud-radio-icons']";
	String header = "mud-typography mud-typography-h6";
	String tableSelectButtonClass = "mud-button-root mud-fab mud-fab-primary mud-fab-size-small mud-ripple";

	public ReportPreparation(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Report Preparation Detail')]")));
	}
	
	public boolean verifyConfidentialUnmarkable() {
		verifyPage();
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		try{
			wait2.until(ExpectedConditions.visibilityOfElementLocated(radioButtonConfidentialUnmarkable));
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public void setConfidential() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(radioButtonConfidentialNotSelected))).perform();
		wait.until(ExpectedConditions.elementToBeClickable(radioButtonConfidentialNotSelected)).click();
	}
	
	public boolean confidentialPromptCheck() {
		verifyPage();
		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		try {
			wait2.until(ExpectedConditions.visibilityOfElementLocated(confidentialPrompt));
		}
		catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public void selectContactPerson(String first, String last) {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(contactPerson))).click().perform();
		wait.until(ExpectedConditions.elementToBeClickable(contactPerson)).sendKeys(first + " " + last);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(searchIconButton))).click().perform();
		
		SearchTables table = new SearchTables(driver);
		table.selectUserFromTable(first, last);
	}
	
	public void finalize() {
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(radioButtonFinalYes))).click().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(selectedRadioButtonFinalYes));
		
//		clickSubmit();
//		TODO: Check visibility of the confirmation popup
		
	}
	
	
	public void clickSubmit() {
//		Verify the submit button is not disabled (checking if element is clickable seem to not be enough)
		wait.until(ExpectedConditions.invisibilityOfElementLocated(submitButtonDisabled));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(submitButton))).click().perform();
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(submitButton));
	}
	
	public void clickPrevious() {
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(previousButton)).click().perform();
	}

}
