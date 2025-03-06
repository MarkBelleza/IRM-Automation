package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PoliceContacted {

	WebDriver driver;
	WebDriverWait wait;
	
	By reasonPoliceNotContacted = By.xpath("//div[@class='mud-input mud-input-outlined mud-input-adorned-end mud-select-input']");
	By policeContacted = By.xpath("(//div[@class='mud-input mud-input-outlined mud-input-adorned-end mud-shrink mud-select-input'])[1]");
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	
	By cancelButton = By.xpath("//span[@class='mud-button-label' and text()='Cancel']");
	By updateButton = By.xpath("//span[@class='mud-button-label' and text()='Update']");
	
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

		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(label + radio)))).perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(label + radio))).click();
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(label + radio)));
	}
	
	public void selectCriminalCharges(String info) {
		Actions actions = new Actions(driver);
		String label = "//p[@class='" + radioTitle + "' and text()='Criminal Charges']";
		String radio = "/following-sibling::div[@class='" + radioContainer + "']//span[@class='" + radioLabel 
					+ "' and text()='" + info + "']/preceding-sibling::span[@class='" + radioButton + "']";

		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(label + radio)))).perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(label + radio))).click();
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(label + radio)));
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
