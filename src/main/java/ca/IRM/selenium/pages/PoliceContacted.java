package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ca.IRM.selenium.components.SearchTables;

public class PoliceContacted {

	WebDriver driver;
	WebDriverWait wait;
	
	By reasonPoliceNotContacted = By.xpath("//div[@class='mud-input mud-input-outlined mud-input-adorned-end mud-select-input']");
	By policeContacted = By.xpath("(//div[@class='mud-input mud-input-outlined mud-input-adorned-end mud-shrink mud-select-input'])[1]");
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	
	By cancelButton = By.xpath("//span[@class='mud-button-label' and text()='Cancel']");
	By updateButton = By.xpath("//span[@class='mud-button-label' and text()='Update']");
	By searchNameButton = By.xpath("//span[@class='mud-button-label' and text()='Search']");
	
	By policeContactedField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[7]/div/div/div/input");
	By policeContactedByField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[8]/div/div/div/input");
	By policeServiceField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[10]/div/div/div/input");
	By policeContactedMethod = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[11]/div/div/div/input");
	By policeTelephoneField = By.xpath("//input[@class='mud-input-slot mud-input-root mud-input-root-outlined' and @type='tel']");
	By policeCaseField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[13]/div/div/div/input");
	
	
	String dropDownFields = "mud-input-slot mud-input-root mud-input-root-outlined mud-input-root-adorned-end mud-select-input";
	
	String header = "mud-typography mud-typography-h6";
	String selectDropdownOption = "mud-typography";
	
	String radioTitle = "mud-typography mud-typography-body1 pt-5";
	String radioContainer = "mud-input-control mud-input-control-boolean-input";
	String radioLabel = "mud-radio-content mud-typography mud-typography-body1";
	String radioButton = "mud-button-root mud-icon-button mud-ripple mud-ripple-radio mud-default-text hover:mud-default-hover";
	
	public String notPoliceMatter = "Not a Police matter";
	public String decisionNoCharges = "Decision not to lay charges";

	public PoliceContacted(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Police Contacted')]")));
	}
	
	public void selectReason(String reason) {
		verifyPage();
		wait.until(ExpectedConditions.elementToBeClickable(reasonPoliceNotContacted)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + reason + "']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='" + dropDownFields + "' and text() = '" + reason + "']")));
	}
	
	public void selectPoliceContacted(String yesNo) {
		verifyPage();
		wait.until(ExpectedConditions.elementToBeClickable(policeContacted)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + yesNo + "']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='" + dropDownFields + "' and text() = '" + yesNo + "']")));
	}
	
	public void selectWillPoliceBeAttending(String yesNo) {
		Actions actions = new Actions(driver);
		String label = "//p[@class='" + radioTitle + "' and text()='Will Police be Attending']";
		String radio = "/following-sibling::div[@class='" + radioContainer + "']//span[@class='" + radioLabel 
					+ "' and text()='" + yesNo + "']/preceding-sibling::span[@class='" + radioButton + "']";

		try {
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(label + radio)))).perform();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(label + radio))).click();
		}catch(TimeoutException e) {
			throw new NoSuchElementException("'" + yesNo + "' may have already been selected in 'Will Police be Attending' field from the Police Contacted page. Please double check the radio status or selector used!");
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(label + radio)));
	}
	
	public void selectCriminalCharges(String info) {
		Actions actions = new Actions(driver);
		String label = "//p[@class='" + radioTitle + "' and text()='Criminal Charges']";
		String radio = "/following-sibling::div[@class='" + radioContainer + "']//span[@class='" + radioLabel 
					+ "' and text()='" + info + "']/preceding-sibling::span[@class='" + radioButton + "']";
		try {
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(label + radio)))).perform();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(label + radio))).click();
		}catch(TimeoutException e) {
			throw new NoSuchElementException("'" + info + "' may have already been selected in 'Criminal Charges' field from the Police Contacted page. Please double check the radio status or selector used!");
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(label + radio)));
	}
	
	public void fillPersonContacted(String details) {
		Actions actions = new Actions(driver);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(policeContactedField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(policeContactedField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();                         // .clear() was not working
		wait.until(ExpectedConditions.elementToBeClickable(policeContactedField)).sendKeys(details);
		
	}
	
	public void fillPersonContactedBy(String name) {
		Actions actions = new Actions(driver);
		SearchTables table = new SearchTables(driver);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(policeContactedByField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(policeContactedByField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();                         // .clear() was not working
		wait.until(ExpectedConditions.elementToBeClickable(policeContactedByField)).sendKeys(name);
		
		wait.until(ExpectedConditions.elementToBeClickable(searchNameButton)).click();
		
		
		String[] parts = name.split(" ");
		String first = parts[0];
		String last;
		
		if (parts.length == 1) {
			last = "";
		}else {
			last = parts[1];
		}
		
		table.selectUserFromTable(first, last);
	}
	
	public void fillPoliceService(String details) {
		Actions actions = new Actions(driver);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(policeServiceField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(policeServiceField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();                         // .clear() was not working
		wait.until(ExpectedConditions.elementToBeClickable(policeServiceField)).sendKeys(details);
		
	}
	
	
	public void fillPoliceContactedMethod(String details) {
		Actions actions = new Actions(driver);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(policeContactedMethod))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(policeContactedMethod)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();                         // .clear() was not working
		wait.until(ExpectedConditions.elementToBeClickable(policeContactedMethod)).sendKeys(details);
		
	}
	
	/**
	 * Fill Police Telephone Field
	 * 
	 * @param details (String: ie. "6471231234". Only string numerals)
	 */
	public void fillPoliceTelephone(String details) {
		Actions actions = new Actions(driver);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(policeTelephoneField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(policeTelephoneField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();                         // .clear() was not working
        wait.until(ExpectedConditions.elementToBeClickable(policeTelephoneField)).click();
        wait.until(ExpectedConditions.elementToBeClickable(policeTelephoneField)).sendKeys(details);
		
	}
	
	public void fillPoliceCase(String details) {
		Actions actions = new Actions(driver);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(policeCaseField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(policeCaseField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();                         // .clear() was not working
		wait.until(ExpectedConditions.elementToBeClickable(policeCaseField)).sendKeys(details);
		
	}
	
	public void clickNext() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(nextButton))).click().perform();
	}
	
	public void clickUpdate() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(updateButton))).click().perform();
	}
	
	public void clickPrevious() {
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(previousButton)).click().perform();
	}
	
	public void clickCancel() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(cancelButton)).click().perform();
	}

}
