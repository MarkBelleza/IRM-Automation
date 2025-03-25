package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DetailsAndCircumstances {
	
	WebDriver driver;
	WebDriverWait wait;
	
	By iirDetailsTextField = By.xpath("//p[@class='mud-typography mud-typography-body1 pt-5' and text()='IIR']/../following-sibling::div[@class='mud-grid-item mud-grid-item-md-6']//div[@class='mud-input-control mud-input-input-control mt-2 mb-2']");
	By iirAddButton = By.xpath("//p[@class='mud-typography mud-typography-body1 pt-5' and text()='IIR']/../following-sibling::div[@class='mud-grid-item mud-grid-item-md-6']//span[text()='Add']/..");
	By iirClearButton = By.xpath("//p[@class='mud-typography mud-typography-body1 pt-5' and text()='IIR']/../following-sibling::div[@class='mud-grid-item mud-grid-item-md-6']//span[text()='Clear']/..");
	By iirTitle = By.xpath("//p[@class='mud-typography mud-typography-body1 pt-5' and text()='IIR']");
	By eoirTitle = By.xpath("//p[@class='mud-typography mud-typography-body1 pt-5' and text()='EOIR']");
	
	By eoirDetailsTextField = By.xpath("//p[@class='mud-typography mud-typography-body1 pt-5' and text()='EOIR']/../following-sibling::div[@class='mud-grid-item mud-grid-item-md-6']//div[@class='mud-input-control mud-input-input-control mt-2 mb-2']");
	By eoirAddButton = By.xpath("//p[@class='mud-typography mud-typography-body1 pt-5' and text()='EOIR']/../following-sibling::div[@class='mud-grid-item mud-grid-item-md-6']//span[text()='Add']/..");
	By eoirClearButton = By.xpath("//p[@class='mud-typography mud-typography-body1 pt-5' and text()='EOIR']/../following-sibling::div[@class='mud-grid-item mud-grid-item-md-6']//span[text()='Clear']/..");
	
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	By updateButton = By.xpath("//span[@class='mud-button-label' and text()='Update']");
	By cancelButton = By.xpath("//span[@class='mud-button-label' and text()='Cancel']");;
	
	String textArea= "mud-input-slot mud-input-root mud-input-root-outlined";
	String header = "mud-typography mud-typography-h6";

	public DetailsAndCircumstances(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Details and Circumstances')]")));
//		System.out.println("In Details and Circumstances page");
	}
	
	public void addIIRDetails(String details) {
		verifyPage();
		String textView = "//textarea[@class='mud-input-slot mud-input-root mud-input-root-text' and text()='" + details + "']";
		Actions actions = new Actions(driver);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(iirClearButton))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(iirDetailsTextField))).click().sendKeys(details).perform();
		wait.until(ExpectedConditions.elementToBeClickable(iirAddButton)).click();

//		Verify text is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(textView)));
	}
	
	public void addEOIRDetails(String details) {
		verifyPage();
		String textView = "//textarea[@class='mud-input-slot mud-input-root mud-input-root-text' and text()='" + details + "']";
		Actions actions = new Actions(driver);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(eoirClearButton))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(eoirDetailsTextField))).click().sendKeys(details).perform();
		wait.until(ExpectedConditions.elementToBeClickable(eoirAddButton)).click();
		
//		Verify text is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(textView)));
	}
	
	public boolean verifyIIRDetails() {
		
		WebDriverWait wait2 =  new WebDriverWait(driver, Duration.ofSeconds(3));
		try {			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(iirTitle));
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean verifyEOIRDetails() {
		
		WebDriverWait wait2 =  new WebDriverWait(driver, Duration.ofSeconds(3));
		try {			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(eoirTitle));
			return true;
		}catch(Exception e) {
			return false;
		}
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
	
	public void clickCancel() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(cancelButton))).click().perform();
	}
	
	public void clickPrevious() {
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(previousButton)).click().perform();
	}

}
