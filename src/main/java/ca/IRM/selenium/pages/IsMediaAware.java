package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class IsMediaAware {
	
	WebDriver driver;
	WebDriverWait wait;
	
	By headerPage = By.xpath("//h6[@class='mud-typography mud-typography-h6']");
	By radioButtonYes = By.xpath("(//span[@class='mud-radio-button'])[1]");
	By radioButtonNo = By.xpath("(//span[@class='mud-radio-button'])[2]");
	By radioButtonUnknown = By.xpath("(//span[@class='mud-radio-button'])[3]");
	
	By textField = By.xpath("//textarea[@class='mud-input-slot mud-input-root mud-input-root-outlined']");
	
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	
	
	public IsMediaAware(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='mud-typography mud-typography-h6' and contains(text(), 'Media Aware Detail')]")));
	}
	
	public void selectYes(String media) {
		wait.until(ExpectedConditions.elementToBeClickable(radioButtonYes)).click();
		wait.until(ExpectedConditions.elementToBeClickable(radioButtonYes)).isSelected();
		
		wait.until(ExpectedConditions.elementToBeClickable(textField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(textField)).sendKeys(media);	
	}
	
	public void selectNo() {
		wait.until(ExpectedConditions.elementToBeClickable(radioButtonNo)).click();
		wait.until(ExpectedConditions.elementToBeClickable(radioButtonNo)).isSelected();
	}
	
	public void selectUnknown() {
		wait.until(ExpectedConditions.elementToBeClickable(radioButtonUnknown)).click();
		wait.until(ExpectedConditions.elementToBeClickable(radioButtonUnknown)).isSelected();
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
