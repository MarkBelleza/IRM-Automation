package ca.IRM.selenium.components;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchTables {

	WebDriver driver;
	WebDriverWait wait;
	
	By tableBody = By.xpath("//tbody[@class='mud-table-body']");
	By tableRow = By.xpath("//tr[@class='mud-table-row']");
	By tableCell = By.xpath("//td[@class='mud-table-cell']");
	By tableSelectButton = By.xpath("//button[@class='mud-button-root mud-fab mud-fab-primary mud-fab-size-small mud-ripple']");
	
	String tableSelectButtonClass = "mud-button-root mud-fab mud-fab-primary mud-fab-size-small mud-ripple";
	//Constructor
	public SearchTables(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	
	public void selectUserFromTable(String first, String last) {
//		Wait for the search results (fetching from db)
		wait.until(ExpectedConditions.visibilityOfElementLocated(tableBody));
		
//		Find the row with the appropriate name
		Actions actions = new Actions(driver);
		try {			
			WebElement userTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + first + "']/following-sibling::td[text()='" + last + "']/following-sibling::td//button[@class='" + tableSelectButtonClass + "']")));
			actions.moveToElement(userTableRow).perform();
			actions.moveToElement(userTableRow).click().perform();
			wait.until(ExpectedConditions.invisibilityOf(userTableRow));
		}catch(TimeoutException e) {
			throw new NoSuchElementException("User with name " + first + " " + last + " not found.");
		}
	}
	
}
