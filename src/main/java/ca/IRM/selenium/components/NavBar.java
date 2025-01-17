package ca.IRM.selenium.components;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavBar {

	WebDriver driver;
	WebDriverWait wait;
//	FluentWait<WebDriver> wait;
	
	By hamburger = By.xpath("//button[@class='mud-button-root mud-icon-button mud-inherit-text hover:mud-inherit-hover mud-ripple mud-ripple-icon mud-icon-button-edge-start']");
	By menuPanelOpen = By.xpath("//aside[@class='mud-drawer mud-drawer-fixed mud-drawer-pos-left mud-drawer--open mud-drawer-md mud-drawer-clipped-never mud-elevation-1 mud-drawer-responsive']");
	By menuPanelClose = By.xpath("//aside[@class='mud-drawer mud-drawer-fixed mud-drawer-pos-left mud-drawer--closed mud-drawer-md mud-drawer-clipped-never mud-elevation-1 mud-drawer-responsive']");
	By incidentReport = By.xpath("//span[contains(@class, 'mud-button-label') and text()='Incident Report']");
	By createNewReport = By.xpath("//p[contains(@class, 'mud-typography') and text()='Create New Report']");
	By searchIncidentReport = By.xpath("//p[contains(@class, 'mud-typography') and text()='Search Reports']");
	By userInfo = By.xpath("/html/body/div[3]/header/div/p[1]");
	By optionContainer = By.xpath("//div[contains(@class, 'mud-list mud-list-padding')]");
	
	By userDetails = By.xpath("//h6[@class='mud-typography mud-typography-h6']");
	
	String navLinks = "mud-nav-link-text";
	
	
	public NavBar(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
//		this.wait = new FluentWait<>(driver)
//			    .withTimeout(Duration.ofSeconds(10))
//			    .pollingEvery(Duration.ofMillis(500))
//			    .ignoring(NoSuchElementException.class);
	}
	
	public boolean verifyMenuVisible() {
		try {			
			wait.until(ExpectedConditions.visibilityOfElementLocated(menuPanelOpen));
		}catch(TimeoutException e) {
			return false;
		}
		return true;
	}
	
	public void openMenu() {
		wait.until(ExpectedConditions.elementToBeClickable(hamburger)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(menuPanelOpen));
	}
	
	public void closeMenu() {
		wait.until(ExpectedConditions.elementToBeClickable(hamburger)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(menuPanelClose));
	}
		
	
	public void createNewReport() {
		// Check for the Incident Report button and Create an Incident Report
		wait.until(ExpectedConditions.elementToBeClickable(incidentReport)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(optionContainer));
		wait.until(ExpectedConditions.elementToBeClickable(createNewReport)).click();
	}
	
	public void searchReport() {
		wait.until(ExpectedConditions.elementToBeClickable(incidentReport)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(optionContainer));
		wait.until(ExpectedConditions.elementToBeClickable(searchIncidentReport)).click();
	}
	
	/**
	  * Select Menu Option
	  *
	  * @param option (Home, Incident Report, Randomizer Report, Administrator)
	  * @param option (Create New Report, Search, Users)
	  */
	public void navigateNavOption(String option, String dropDownOption) {
		if(!verifyMenuVisible()) {
			openMenu();
		}
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='" + navLinks + "' and text()= '"+ option +"']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='" + navLinks + "' and text()= '"+ dropDownOption +"']"))).click();
		closeMenu();
	}
	
}
