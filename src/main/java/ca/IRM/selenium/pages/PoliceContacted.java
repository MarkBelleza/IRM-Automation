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
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	
	String dropDownFields = "mud-input-slot mud-input-root mud-input-root-outlined mud-input-root-adorned-end mud-select-input";
	
	String header = "mud-typography mud-typography-h6";
	String selectDropdownOption = "mud-typography";
	
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
	
	public void clickNext() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(nextButton))).click().perform();
	}
	
	public void clickPrevious() {
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(previousButton)).click().perform();
	}

}
