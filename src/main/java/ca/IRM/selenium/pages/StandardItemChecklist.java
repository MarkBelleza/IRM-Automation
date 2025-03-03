package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StandardItemChecklist {

	WebDriver driver;
	WebDriverWait wait;
	
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	
	String incidentNames = "mud-typography mud-typography-body1";
	String buttonClass = "mud-button-root mud-icon-button mud-ripple mud-ripple-icon mud-treeview-item-arrow-expand";
	String mudRadioContent = "mud-radio-content mud-typography mud-typography-body1";
	String mudRadioButtonYes = "mud-button-root mud-icon-button mud-ripple mud-ripple-radio mud-default-text hover:mud-default-hover mud-radio-dense";
	String mudRadioButtonNo = "mud-button-root mud-icon-button mud-ripple mud-ripple-radio mud-default-text hover:mud-default-hover";
	String header = "mud-typography mud-typography-h6";

	public StandardItemChecklist(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Standard CheckList Items')]")));
	}
	
	public String getItemSelector(String itemName) {
		return "//p[contains(@class, '" + incidentNames + "') and text()= '" + itemName + "']";
	}
	
	public void expandItem(String incident) {
		String arrowButton = "/../preceding-sibling::div[@class='mud-treeview-item-arrow']/button[@class='" + buttonClass + "']";
		
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(getItemSelector(incident) + arrowButton)))).click().perform();
	}
	
	/**
	  * Select Standard Checklist Item 
	  *
	  * @param incident (String: Item name ie. "Advised of right to pursue/decline criminal charges")
	  * @param yesNo (String: "yes"/"No")
	  */
	public void selectChecklistItem(String incident, String yesNo) {
		String radioButton;
		
		if (yesNo.equals("Yes")) {			
			radioButton = "/following-sibling::div[@style='justify-self: end;']//span[@class='" + mudRadioContent 
					+ "' and text()='Yes']/preceding-sibling::span[@class='" + mudRadioButtonYes + "']";
		}else {
			radioButton = "/following-sibling::div[@style='justify-self: end;']//span[@class='" + mudRadioContent 
					+ "' and text()='No']/preceding-sibling::span[@class='" + mudRadioButtonNo + "']";
		}
		
		Actions actions = new Actions(driver);
		try {			
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(getItemSelector(incident) + radioButton)))).click().perform();
		}catch(TimeoutException e) {
			throw new NoSuchElementException("'" + yesNo + "' may have already been selected in item '" + incident + "'. Please double check the item's status or selector used!");
		}
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
