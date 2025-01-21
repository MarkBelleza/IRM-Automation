package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import ca.IRM.selenium.components.SearchTables;

public class Involved {

	WebDriver driver;
//	WebDriverWait wait;
	FluentWait<WebDriver> wait;
	
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	By updateButton = By.xpath("//span[@class='mud-button-label' and text()='Update']");
	By cancelButton = By.xpath("//span[@class='mud-button-label' and text()='Cancel']");
	
	By employeeLastNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[4]/div[2]/div/div[1]/div/div/div[2]/div/div/div/input");
	By employeeFirstNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[4]/div[2]/div/div[1]/div/div/div[3]/div/div/div/input");
	By employeeSearchButton = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[4]/div[2]/div/div[1]/div/div/div[4]/button/span");
	
	By inmateLastNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[2]/div[1]/div/div/div[2]/div/div/div/input");
	By inmateFirstNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[2]/div[1]/div/div/div[3]/div/div/div/input");
	By inmateSearchButton = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[2]/div[1]/div/div/div[4]/button");
	
	By employeeRow = By.xpath("//td[@data-label='Employee']");
	By employeeRoleDropdown = By.xpath("//div[@class='mud-input-control mud-input-control-margin-dense mud-select']");
	By numberDosesField = By.xpath("//div[@class='mud-input mud-input-text mud-input-underline mud-shrink']");
	By hospitalizedCheckbox = By.xpath("//span[@class='mud-button-root mud-icon-button mud-default-text hover:mud-default-hover mud-ripple mud-ripple-checkbox']");
	By commitButton = By.xpath("(//button[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-icon mud-icon-button-size-small pa-0'])[1]");
	By updateDialogDropdown = By.xpath("//div[@class='mud-list mud-list-padding']");
	
	By incrementDose = By.xpath("(//*[@aria-label='Increment' and @class='mud-icon-root mud-svg-icon mud-icon-size-medium'])[1]");
	
	String header = "mud-typography mud-typography-h6";
	String selectDropdownOption = "mud-typography";
	String buttonClass = "mud-button-root mud-icon-button mud-ripple mud-ripple-icon mud-icon-button-size-small";
	
	public Involved(WebDriver driver) {
		this.driver = driver;
//		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.wait = new FluentWait<>(driver)
	    .withTimeout(Duration.ofSeconds(10))
	    .pollingEvery(Duration.ofMillis(500))
	    .ignoring(NoSuchElementException.class);
	}
	
	public void verifyPage(){
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Involved')]")));
	}
	
	
	public void selectEmployee(String firstName, String lastName, String role, String dosesNum, Boolean hospitalized) {
		verifyPage();
//		Fill in name fields
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(employeeFirstNameField))).click().perform();
		wait.until(ExpectedConditions.elementToBeClickable(employeeFirstNameField)).sendKeys(firstName);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(employeeLastNameField))).click().perform();
		wait.until(ExpectedConditions.elementToBeClickable(employeeLastNameField)).sendKeys(lastName);
		
		wait.until(ExpectedConditions.elementToBeClickable(employeeSearchButton)).click();
		
//		Select the employee from the table
		SearchTables table = new SearchTables(driver);
		table.selectUserFromTable(firstName, lastName);
		
//		Select the Added Employee in the table below
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']")))).click().perform();
		
//		Fill in the employee information:
//		Role
		wait.until(ExpectedConditions.visibilityOfElementLocated(employeeRoleDropdown)).click();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + role + "']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Doses
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(numberDosesField))).click().sendKeys(dosesNum).perform();
		
//		Hospitalized
		if (hospitalized) {
			wait.until(ExpectedConditions.elementToBeClickable(hospitalizedCheckbox)).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(commitButton)).click();
	}
	
	public void deleteEmployee(String firstName, String lastName) {
		verifyPage();
		Actions actions = new Actions(driver);
		try {			
			WebElement userTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/ancestor::tr//td[last()-1]/button[@class='" + buttonClass + "']")));
			actions.moveToElement(userTableRow).perform();
			actions.moveToElement(userTableRow).click().perform(); 
			//Work around. When entering Involved section in Update mode, there is a scrolling animation which messes with the script.
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Employee with name " + firstName + " " + lastName + " not found.");
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']")));
	}
	
	public void selectInmateByName(String firstName, String lastName, String role, String dosesNum, Boolean hospitalized) {
		verifyPage();
//		Fill in name fields
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(inmateFirstNameField))).click().perform();
		wait.until(ExpectedConditions.elementToBeClickable(inmateFirstNameField)).sendKeys(firstName);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(inmateLastNameField))).click().perform();
		wait.until(ExpectedConditions.elementToBeClickable(inmateLastNameField)).sendKeys(lastName);
		
		wait.until(ExpectedConditions.elementToBeClickable(inmateSearchButton)).click();
		
//		Select the employee from the table
		SearchTables table = new SearchTables(driver);
		table.selectUserFromTable(firstName, lastName);
		
//		Select the Added Inmate in the table below
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Name']")))).click().perform();
		
//		Fill in the employee information:
//		Role
		wait.until(ExpectedConditions.visibilityOfElementLocated(employeeRoleDropdown)).click();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + role + "']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Doses
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(numberDosesField))).click().sendKeys(dosesNum).perform();
		
//		Hospitalized
		if (hospitalized) {
			wait.until(ExpectedConditions.elementToBeClickable(hospitalizedCheckbox)).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(commitButton)).click();
	}
	
	public void deleteInmateByName(String firstName, String lastName) {
		verifyPage();
		Actions actions = new Actions(driver);
		try {			
			WebElement userTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Name']/ancestor::tr//td[last()-1]/button[@class='" + buttonClass + "']")));
			actions.moveToElement(userTableRow).perform();
			actions.moveToElement(userTableRow).click().perform(); 
			//Work around. When entering Involved section in Update mode, there is a scrolling animation which messes with the script.
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Employee with name " + firstName + " " + lastName + " not found.");
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Name']")));
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
}
;