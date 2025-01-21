package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import ca.IRM.selenium.components.NavBar;

public class ReportSearch {
	
	public By radioButtons = By.xpath("//span[@class ='mud-radio-button']");
	
	public By radioIncidentIdButton = By.xpath("//span[@class ='mud-radio-button'][1]");
	public By incidentIdField = By.xpath("//input[@class='mud-input-slot mud-input-root mud-input-root-text'][1]");
	
	public By searchButton = By.xpath("//span[@class='mud-button-label' and text()='Search']");
	public By clearButton = By.xpath("//span[@class='mud-button-label' and text()='Clear']");
	
	public String header = "mud-typography mud-typography-h6";

	WebDriver driver;
	WebDriverWait wait;
//	FluentWait<WebDriver> wait;
	
	public ReportSearch(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		this.wait = new FluentWait<>(driver)
//			    .withTimeout(Duration.ofSeconds(10))
//			    .pollingEvery(Duration.ofMillis(500))
//			    .ignoring(NoSuchElementException.class);
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Report Search')]")));
	}
	
	public void searchIncidentReport(String id) {
		
		NavBar nav = new NavBar(driver);
		Actions actions = new Actions(driver);
		
//		View the Incident Report created
		nav.searchReport();
		verifyPage();
		
//		**Search for the Incident Report that we just created
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(radioIncidentIdButton))).click().perform();
		wait.until(ExpectedConditions.elementToBeClickable(incidentIdField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(incidentIdField)).sendKeys(id);
		
		actions.moveToElement(driver.findElement(searchButton)).click().perform();
	}
	
	public void openIncidentReport(String id) {
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class ='mud-table-cell' and text()='" + id + "']"))))
		.click().perform();
	}
	
	public void verifyIncident(String id) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class ='mud-table-cell' and text()='" + id + "']")));
	}
	
	public void verifyIncident(String id, String incidentType) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class ='mud-table-cell' and text()='" + id + "']")));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[@class ='mud-table-cell' and text()='" + id + "']/following-sibling::td[text()='" + incidentType + "']")));
	}
}
