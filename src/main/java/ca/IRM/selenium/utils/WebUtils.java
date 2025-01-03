package ca.IRM.selenium.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ca.IRM.selenium.pages.Notification;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebUtils {
	
	WebDriver driver;
	WebDriverWait wait;
	
	By accessDenied = By.xpath("//h3[text()='AccessDenied']");
	
	//Constructor
	public WebUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public static void setUpIrmPage(WebDriver driver) {
		driver.manage().window().maximize();
		driver.navigate().to("http://jtsazistcirm01/");
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
	public void accessDeniedVerify() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(accessDenied));
	}

	public void createGenericIncidentReport(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
		// Check for the Incident Report button and Create an Incident Report
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'mud-button-label') and text()='Incident Report']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(@class, 'mud-typography') and text()='Create New Report']"))).click();
		
	
//		Fill in the Notification section
		Notification fields = new Notification(driver);
		
//		** Select Incident Time (manually type)
//		wait.until(ExpectedConditions.elementToBeClickable(By.className("mud-picker-input-text"))).click();
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='mud-input-slot mud-input-root mud-input-root-outlined mud-input-root-adorned-end'][2]"))).clear();
//		wait.until(ExpectedConditions.elementToBeClickable(By.className("mud-picker-input-text"))).sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
//		wait.until(ExpectedConditions.elementToBeClickable(By.className("mud-picker-input-text"))).sendKeys("00:00");
		
		
//		** Select Priority ~~~~~
		fields.selectPriority("One");
		
//		** Select Location
//		fields.selectLocation(Notification.locations[0]);
		fields.selectLocation("ALGOMA TREATMENT & REMAND CTR-ADULT (Institution)");
		
//		** Select Area and Select Other
		fields.selectArea("Other");
		
//		** Fill in Other text field
		fields.fillLocationDetails("Somewhere anywhere");
		
//		** Click next
		fields.clickNext();
		duplicatePopUpCheck();
	}
	
	public void duplicatePopUpCheck() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		
		try {
		    WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//div[contains(@class, 'mud-dialog-title') and contains(text(), 'Duplicate Incident')]")));
		    
		    // Check if the dialog is displayed and click the 'Proceed' button
		    if (dialog.isDisplayed()) {
		    	wait.until(ExpectedConditions.elementToBeClickable(
		                By.xpath("//span[contains(@class, 'mud-button-label') and text()='Proceed']"))).click();
		        System.out.println("Duplicate found, Proceed button clicked.");
		    }
		} catch (TimeoutException e) {
		    System.out.println("No Duplicate Incident found");
		}
	}
	
}
