package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SupportingDocuments {

	WebDriver driver;
	WebDriverWait wait;
	
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	
	
	String header = "mud-typography mud-typography-h6";

	public SupportingDocuments(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Supporting Documents')]")));
//		System.out.println("In Supporting Documents page");
	}
	
	/**
	  * Upload file in Supporting Documents page 
	  *
	  * @param incident (String: "Death of Staff"/"Labour Activities"/"Employee Medical Emergency"/"External Media Inquiries")
	  * @param uploadName (String: "Field Visit Report"/"MOL Order"/"Copy of media article")
	  * @param fileName (String: "UploadFileTest.docx"/"UploadFileTest2.docx"/ ... "UploadFileTest6.docx")
	  */
	public void uploadFile(String incident, String uploadName, String fileName) {
		Actions actions = new Actions(driver);
		
		String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + fileName;
		String deathofStaffRow = "//td[text()='" + incident + "' and @data-label='Incident Type']/following-sibling::td[@class='justify-begin mud-table-cell']";
		String selectorMolOrder = deathofStaffRow + "//span[@class='mud-button-label' and text()='" + uploadName + "']";
		String selectorDeathofStaff = selectorMolOrder + "/../preceding-sibling::input[@type='file']";
		
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectorMolOrder)))).perform();
		driver.findElement(By.xpath(selectorDeathofStaff)).sendKeys(filePath);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deathofStaffRow + "//a[text()='" + fileName + "']")));
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
