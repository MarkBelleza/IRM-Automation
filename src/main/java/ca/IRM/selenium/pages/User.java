package ca.IRM.selenium.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ca.IRM.selenium.utils.NavBar;

public class User {
	WebDriver driver;
	WebDriverWait wait;
	
	By fields = By.xpath("//input[@class='mud-input-slot mud-input-root mud-input-root-text']");
	By lastNameField = By.xpath("(//input[@class='mud-input-slot mud-input-root mud-input-root-text'])[2]");
	By firstNameField = By.xpath("(//input[@class='mud-input-slot mud-input-root mud-input-root-text'])[3]");
	
	By radioButtons = By.xpath("//input[@class='mud-radio-input']");
	By radioName = By.xpath("(//span[@class='mud-radio-button'])[2]");
	
	By searchButton = By.xpath("//span[@class='mud-button-label' and text()='Search']");
	By clearButton = By.xpath("//span[@class='mud-button-label' and text()='Clear']");
	By cancelButton = By.xpath("//span[@class='mud-button-label' and text()='Cancel']");
	By submitButton = By.xpath("//span[@class='mud-button-label' and text()='Submit']");

	By tableBody = By.xpath("//tbody[@class='mud-table-body']");
	By tableSelectButton = By.xpath("//button[@class='mud-button-root mud-fab mud-fab-primary mud-fab-size-small mud-ripple']");
	By tableRow = By.xpath("//tr[@class='mud-table-row']");
	
	By updateDialog = By.className("mud-dialog-content");
	By updateDialogDropdown = By.xpath("//div[@class='mud-list mud-list-padding']");
	
	String tableSelectButtonClass = "mud-button-root mud-fab mud-fab-primary mud-fab-size-small mud-ripple";
	String labelFields = "mud-input-label mud-input-label-animated mud-input-label-text mud-input-label-inputcontrol";
	String header = "mud-typography mud-typography-h6";
	String updateDialogLabel = "mud-input-label mud-input-label-animated mud-input-label-text mud-input-label-margin-dense mud-input-label-inputcontrol";
	String updateDialogDropdownOptions = "mud-typography mud-typography-body1";
	
	public String staff = "Staff Sergeant";
	public String admin = "Admin to the Superintendent";
	public String deputy = "Deputy Superintendent";
	public String superin = "Superintendent";
	public String regional = "Regional Office";
	public String depart1 = "Department 1";
	public String depart2 = "Department 2";
	public String depart3 = "Department 3";
	
	public String algo = "ALGOMA TREATMENT & REMAND CTR-ADULT";
	public String brock = "BROCKVILLE JAIL - ADULT";
	public String central = "Central Regional Office"; 
	public String eastern = "Eastern Regional Office"; 
	public String northern = "Northern Regional Office"; //Within Algoma
	
	
	public User(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'User Search')]")));
		System.out.println("In User Search page");
	}
	
	public void changeUserType(String userType, String location) {
		NavBar nav = new NavBar(driver);
		nav.navigateNavOption("Administration", "Users");
		Actions actions = new Actions(driver);
		
		verifyPage();
		searchUser();
		
//		Change user type and location
		wait.until(ExpectedConditions.visibilityOfElementLocated(updateDialog));
		
        // Use JavaScript to set zoom level to 80% (workaround to see all locations/user types)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='80%'");
		
        
//		Select User Type Drop-down
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@class='" + updateDialogLabel + "' and text()= 'User Type']/.."))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(updateDialogDropdown));
		
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='" + updateDialogDropdownOptions + "' and text()= '" + userType + "']"))).click();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[@class='" + updateDialogDropdownOptions + "' and text()= '" + userType + "']"))))
				.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Select Location Drop-down
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@class='" + updateDialogLabel + "' and text()= 'Primary Location']/.."))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(updateDialogDropdown));
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//p[@class='" + updateDialogDropdownOptions + "' and text()= '" + location + "']"))))
			.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Submit changes
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(submitButton))).click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialog));
		
		driver.navigate().refresh();
	
		
//		Make sure User Type and Location is set to @userType and @location
		String[] userNavBarInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/header/div/p[1]"))).getText().split(", ");
		
		String actualUserTitle = userNavBarInfo[1];
		String actualUserLocation = userNavBarInfo[2];
		
		System.out.println("Actual User Title: " + actualUserTitle);
		System.out.println("Actual User Location: " + actualUserTitle);
		
		Assert.assertEquals(actualUserTitle, userType);
		Assert.assertEquals(actualUserLocation, location.stripTrailing()); //work around for "Data Insights and Strategic Initiatives Division "
		
	}
	
	public void searchUser() {
		String[] userNavBarInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/header/div/p[1]"))).getText().split(", ");
		String firstName = userNavBarInfo[0].split(" ")[1];
		String lastName = userNavBarInfo[0].split(" ")[2]; 
		System.out.println("User Name: " + firstName + " " + lastName);
		
		wait.until(ExpectedConditions.elementToBeClickable(radioName)).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(lastNameField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(lastNameField)).sendKeys(lastName);
		
		wait.until(ExpectedConditions.elementToBeClickable(firstNameField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(firstNameField)).sendKeys(firstName);
		
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(searchButton))).click().perform();
		
		
		try {
//			Wait for the search results (fetching from db)
			wait.until(ExpectedConditions.visibilityOfElementLocated(tableBody));
			
			WebElement userTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "']/following-sibling::td[text()='" + firstName + "']/following-sibling::td//button[@class='" + tableSelectButtonClass + "']")));
			actions.moveToElement(userTableRow).click().perform();
		}catch(TimeoutException e) {
			throw new NoSuchElementException("User with name " + lastName + " " + firstName + " not found.");
		}
	}
	
	public void searchUser(String firstName, String lastName) {
		wait.until(ExpectedConditions.elementToBeClickable(radioName)).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(lastNameField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(lastNameField)).sendKeys(lastName);
		
		wait.until(ExpectedConditions.elementToBeClickable(firstNameField)).click();
		wait.until(ExpectedConditions.elementToBeClickable(firstNameField)).sendKeys(firstName);
		
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(searchButton))).click().perform();
		
		
		try {
//			Wait for the search results (fetching from db)
			wait.until(ExpectedConditions.visibilityOfElementLocated(tableBody));
			
			WebElement userTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "']/following-sibling::td[text()='" + firstName + "']/following-sibling::td//button[@class='" + tableSelectButtonClass + "']")));
			actions.moveToElement(userTableRow).click().perform();
		}catch(TimeoutException e) {
			throw new NoSuchElementException("User with name " + lastName + " " + firstName + " not found.");
		}
	}
}
