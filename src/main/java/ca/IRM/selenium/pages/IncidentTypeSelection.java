package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class IncidentTypeSelection {
	
	WebDriver driver;
	WebDriverWait wait;

	By headerPage = By.xpath("//h6[@class='mud-typography mud-typography-h6']");
	By errorPopUp = By.xpath("//div[@class='mud-snackbar mud-alert-filled-error']");
	By checkBox = By.xpath("mud-input-control mud-input-control-boolean-input");
	By itemIIR = By.xpath("//p[contains(@class, 'mud-typography mud-typography-body1') and text()= 'IIR']");
	By itemEOIR = By.xpath("//p[contains(@class, 'mud-typography mud-typography-body1') and text()= 'EOIR']");
	
	String item = "mud-treeview-item-content cursor-pointer"; //Clickable
	String incidentNames = "mud-typography mud-typography-body1";
	String checkBoxStr = "mud-input-control mud-input-control-boolean-input";
	
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	By updateButton = By.xpath("//span[@class='mud-button-label' and text()='Update']");
	By cancelButton = By.xpath("//span[@class='mud-button-label' and text()='Cancel']");
	
	
	public IncidentTypeSelection(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='mud-typography mud-typography-h6' and contains(text(), 'Incident Types')]")));
	}
	
	public String getItemSelector(String itemName) {
		return "//p[contains(@class, '" + incidentNames + "') and text()= '" + itemName + "']";
	}
	
	public boolean verifyIIR() {
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		try {			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getItemSelector("IIR"))));
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public boolean verifyEOIR() {
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		try {			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getItemSelector("EOIR"))));
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public void expandItem(String incident) {
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(getItemSelector(incident) + "/..")))).click().perform();
	}
	
	public void selectItem(String incident) {
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(getItemSelector(incident) + "/preceding-sibling::div[@class='" + checkBoxStr + "']")))).click().perform();
	}
	
//	TODO: for now hard coded, but find away to make this more dynamic and able to select item based on parameter
	public void selectIIRExample() {
		verifyPage();
		expandItem("IIR");
		expandItem("Assault");
		expandItem("(P1) Serious Inmate on Inmate");
		expandItem("Item thrown/contact");
		selectItem("Bodily substance");
//		expandItem("IIR");
		
	}
	
	public void selectEOIRExample() {
		expandItem("EOIR");
		expandItem("Death of Staff");
		expandItem("On Duty");
		selectItem("Off site");
		
		expandItem("Serious Contrabnd (Staff)");
		selectItem("Alcohol");
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
