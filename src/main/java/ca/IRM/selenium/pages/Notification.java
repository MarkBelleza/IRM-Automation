package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ca.IRM.selenium.utils.WebUtils;

public class Notification {
	
	WebDriver driver;
	WebDriverWait wait;
//	FluentWait<WebDriver> wait;
	
	By headerPage = By.xpath("//h6[@class='mud-typography mud-typography-h6']");
	By priorityField = By.xpath("//div[contains(@class, 'mud-input-slot') and text()='Select Priority']");
	By locationField = By.xpath("//div[contains(@class, 'mud-input-slot') and text()='Select Location']");
	By areaUnitRangeField = By.xpath("//div[@class='mud-input mud-input-outlined mud-input-adorned-end mud-select-input']");
	By othersField = By.xpath("//textarea[contains(@class, 'mud-input-slot') and @rows='3']");
	By updateDialogDropdown = By.xpath("//div[@class='mud-list mud-list-padding']");
	By cancelButton = By.xpath("//span[@class='mud-button-label' and text()='Cancel']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
//	By nextButton = By.xpath("//button[@class='mud-button-root mud-button mud-button-filled mud-button-filled-primary mud-button-filled-size-medium mud-ripple' and @type='submit']");
	
	String selectDropdownOption = "mud-typography";
	
	public static String[] locations = {"ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)", "BROCKVILLE JAIL - ADULT (Institution)"};
	public static String[] areas = {"Unit Range", "Shower", "Other"};
	public static String[] priorities = {"One", "Two"};
	
	public Notification(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		this.wait = new FluentWait<>(driver)
//			    .withTimeout(Duration.ofSeconds(10))
//			    .pollingEvery(Duration.ofMillis(500))
//			    .ignoring(NoSuchElementException.class);
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='mud-typography mud-typography-h6' and contains(text(), 'Notification')]")));
		System.out.println("In Notification page");
	}
	
	public void selectPriority(String priority) {
		verifyPage();
		wait.until(ExpectedConditions.elementToBeClickable(priorityField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + priority + "']"))).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
	}
	
	public void selectLocation(String location) {
		verifyPage();
		wait.until(ExpectedConditions.elementToBeClickable(locationField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + location + "']"))).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
	}
	
	public void selectArea(String area) {
		verifyPage();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("mud-picker-input-text"))).click(); //work around
		wait.until(ExpectedConditions.elementToBeClickable(areaUnitRangeField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + area + "']"))).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
	}
	
	public void fillLocationDetails(String detail) {
		verifyPage();
		wait.until(ExpectedConditions.elementToBeClickable(By.className("mud-picker-input-text"))).click(); //work around
		wait.until(ExpectedConditions.elementToBeClickable(othersField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(othersField)).sendKeys(detail);
	}
	
	public void clickNext() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(nextButton))).click().perform();
	}

	public void clickCancel() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(cancelButton)).click().perform();
	}

	
	
	
//	public enum Location{
//		NOTC_A("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)"),
//		BROK_A("BROCKVILLE JAIL - ADULT (Institution)");
//
//		Location(String string) {
//			// TODO Auto-generated constructor stub
//		}
//		
//	}
	
	
	

	
	
}
