package ca.IRM.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
	
	By employeeLastNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[4]/div[2]/div/div[1]/div/div/div[2]/div/div/div/input");
	By employeeFirstNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[4]/div[2]/div/div[1]/div/div/div[3]/div/div/div/input");
	By employeeSearchButton = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[4]/div[2]/div/div[1]/div/div/div[4]/button/span");
	
	By inmateLastNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[2]/div[1]/div/div/div[2]/div/div/div/input");
	By inmateFirstNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[2]/div[1]/div/div/div[3]/div/div/div/input");
	By inmateSearchButton = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[1]/div[2]/div[1]/div/div/div[4]/button");
	
	By otherLastNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[7]/div[2]/div/div[1]/div/div/div/input");
	By otherFirstNameField = By.xpath("/html/body/div[3]/div/div/div/div[2]/form/div[7]/div[2]/div/div[2]/div/div/div/input");
	By otherCategoryField = By.xpath("(//input[@class='mud-input-slot mud-input-root mud-input-root-outlined mud-input-root-adorned-end mud-select-input'])[1]");
	By otherCategoryFilled = By.xpath("(//div[@class='mud-input-slot mud-input-root mud-input-root-outlined mud-input-root-adorned-end mud-select-input'])[1]");
	By otherRoleField = By.xpath("(//input[@class='mud-input-slot mud-input-root mud-input-root-outlined mud-input-root-adorned-end mud-select-input'])[2]");
	By otherRoleFilled = By.xpath("(//div[@class='mud-input-slot mud-input-root mud-input-root-outlined mud-input-root-adorned-end mud-select-input'])[2]");
	By otherNumberDosesField = By.xpath("//label[@class='mud-input-label mud-input-label-animated mud-input-label-outlined mud-input-label-inputcontrol' and text()='Number Of Anti Opioid Administered']");
	By otherHospitalizedCheckbox = By.xpath("//span[@class='mud-button-root mud-icon-button mud-default-text hover:mud-default-hover mud-checkbox-dense mud-ripple mud-ripple-checkbox']");
	
	By otherCatEditDropdown = By.xpath("(//div[@class='mud-input-control mud-input-control-margin-dense mud-select'])[1]");
	By otherRoleEditDropdown = By.xpath("(//div[@class='mud-input-control mud-input-control-margin-dense mud-select'])[2]");
	
	By employeeRow = By.xpath("//td[@data-label='Employee']");
	By employeeRoleDropdown = By.xpath("//div[@class='mud-input-control mud-input-control-margin-dense mud-select']");
	By numberDosesFieldEdit = By.xpath("//div[@class='mud-input mud-input-text mud-input-underline mud-shrink']");
	By hospitalizedCheckbox = By.xpath("//span[@class='mud-button-root mud-icon-button mud-default-text hover:mud-default-hover mud-ripple mud-ripple-checkbox']");
	By commitButton = By.xpath("(//button[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-icon mud-icon-button-size-small pa-0'])[1]");
	By updateDialogDropdown = By.xpath("//div[@class='mud-list mud-list-padding']");
	
	By incrementDose = By.xpath("(//*[@aria-label='Increment' and @class='mud-icon-root mud-svg-icon mud-icon-size-medium'])[1]");
	
	By previousButton = By.xpath("//span[@class='mud-button-label' and text()='Previous']");
	By nextButton = By.xpath("//span[@class='mud-button-label' and text()='Next']");
	By updateButton = By.xpath("//span[@class='mud-button-label' and text()='Update']");
	By cancelButton = By.xpath("//span[@class='mud-button-label' and text()='Cancel']");
	By addToOtherButton = By.xpath("//span[@class='mud-button-label' and text()='Add To Other']");
	
	String header = "mud-typography mud-typography-h6";
	String selectDropdownOption = "mud-typography";
	String buttonClass = "mud-button-root mud-icon-button mud-ripple mud-ripple-icon mud-icon-button-size-small";
	String alertErrorClass = "mud-snackbar mud-alert-filled-error";
	String alertErrorMessageClass = "mud-snackbar-content-message";
	
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
	
	public void inmateSearchButtonClick() {
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(inmateSearchButton))).click().perform();
	}
	
	public void employeeSearchButtonClick() {
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(employeeSearchButton))).click().perform();
	}
	
	/**
	  * Add Inmate by name in Involved section
	  *
	  * @param firstName
	  * @param lastName
	  * @param role (Witness, Participant, Other)
	  * @param dosesNum
	  * @param hospitalized
	  */
	public void addInmateByName(String firstName, String lastName, String role, String dosesNum, Boolean hospitalized) {
		verifyPage();
//		Fill in name fields
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(inmateFirstNameField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(inmateFirstNameField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();          
		wait.until(ExpectedConditions.elementToBeClickable(inmateFirstNameField)).sendKeys(firstName);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(inmateLastNameField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(inmateLastNameField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();          
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
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(employeeRoleDropdown))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + role + "']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Doses
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit))).click().sendKeys(dosesNum).perform();
		
//		Hospitalized
		String checkbox = "No";
		if (hospitalized) {
			wait.until(ExpectedConditions.elementToBeClickable(hospitalizedCheckbox)).click();
			checkbox = "Yes";
		}
		wait.until(ExpectedConditions.elementToBeClickable(commitButton)).click();
		
//		TODO: Check Inmate is saved
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Name']/following-sibling::td[text()='" + role + "']/following-sibling::td[text()='" + dosesNum + "' and @data-label='Sign']/following-sibling::td[text()='" + checkbox + "']/ancestor::tr")));
		
	}
	
	/**
	  * Edit Inmate information by name in Involved section
	  *
	  * @param firstName
	  * @param lastName
	  * @param role (Witness, Participant, Other)
	  * @param dosesNum
	  * @param hospitalized
	  */
	public void editInmateByName(String firstName, String lastName, String role, String dosesNum, Boolean hospitalized) {
		verifyPage();
		Actions actions = new Actions(driver);
		String checkbox = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Name']/ancestor::tr//td[5]"))).getText();
		
//		Select the Inmate in the 
		try{
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Name']")))).perform();
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Name']")))).click().perform();	
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Inmate with name " + firstName + " " + lastName + " not found in Involved section. (Edit failed)");
		}
		
//		Fill in the employee information:
//		Role
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(employeeRoleDropdown))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + role + "']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Doses
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();          
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit))).click().sendKeys(dosesNum).perform();
		
//		Hospitalized
		if (hospitalized && checkbox.equals("No")) {
			wait.until(ExpectedConditions.elementToBeClickable(hospitalizedCheckbox)).click();
			checkbox = "Yes";
		}
		else if (!hospitalized && checkbox.equals("Yes")) {
			wait.until(ExpectedConditions.elementToBeClickable(hospitalizedCheckbox)).click();
			checkbox = "No";
		}
		wait.until(ExpectedConditions.elementToBeClickable(commitButton)).click();
		
//		TODO: Check the changes are saved
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Name']/following-sibling::td[text()='" + role + "']/following-sibling::td[text()='" + dosesNum + "' and @data-label='Sign']/following-sibling::td[text()='" + checkbox + "']/ancestor::tr")));
	}
	
	/**
	  * Delete Inmate by name in Involved section (NOTE: Assuming each person's name in "Inmate" are unique)
	  *
	  * @param firstName
	  * @param lastName
	  */
	public void deleteInmateByName(String firstName, String lastName) {
		verifyPage();
		Actions actions = new Actions(driver);
		try {			
			WebElement userTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Name']/following-sibling::td[@data-label='OTSID']/ancestor::tr//td[last()-1]/button[@class='" + buttonClass + "']")));
			actions.moveToElement(userTableRow).perform();
			actions.moveToElement(userTableRow).click().perform(); 
			//Work around. When entering Involved section in Update mode, there is a scrolling animation which messes with the script.
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Inmate with name " + firstName + " " + lastName + " not found in Involved section.");
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Name']/following-sibling::td[@data-label='OTSID']/ancestor::tr//td[last()-1]/button[@class='" + buttonClass + "']")));
	}
	
	
	/**
	  * Add employee in Involved section
	  *
	  * @param firstName
	  * @param lastName
	  * @param role (Witness, Participant, Other)
	  * @param dosesNum
	  * @param hospitalized
	  */
	public void addEmployee(String firstName, String lastName, String role, String dosesNum, Boolean hospitalized) {
		verifyPage();
//		Fill in name fields
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(employeeFirstNameField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(employeeFirstNameField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform(); 
		wait.until(ExpectedConditions.elementToBeClickable(employeeFirstNameField)).sendKeys(firstName);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(employeeLastNameField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(employeeLastNameField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform(); 
		wait.until(ExpectedConditions.elementToBeClickable(employeeLastNameField)).sendKeys(lastName);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(employeeSearchButton))).click().perform();
		
//		Select the employee from the table
		SearchTables table = new SearchTables(driver);
		table.selectUserFromTable(firstName, lastName);
		
//		Select the Added Employee in the table below
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/following-sibling::td[@data-label='Title']")))).click().perform();
		
//		Fill in the employee information:
//		Role
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(employeeRoleDropdown))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + role + "']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Doses
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit))).click().sendKeys(dosesNum).perform();
		
//		Hospitalized
		String checkbox = "No";
		if (hospitalized) {
			wait.until(ExpectedConditions.elementToBeClickable(hospitalizedCheckbox)).click();
			checkbox = "Yes";
		}
		wait.until(ExpectedConditions.elementToBeClickable(commitButton)).click();
		
//		TODO: Check the employee is saved
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/following-sibling::td[text()='" + role + "']/following-sibling::td[text()='" + dosesNum + "' and @data-label='Sign']/following-sibling::td[text()='" + checkbox + "']/ancestor::tr")));
	}
	
	/**
	  * Edit employee in Involved section
	  *
	  * @param firstName
	  * @param lastName
	  * @param role (Witness, Participant, Other)
	  * @param dosesNum
	  * @param hospitalized
	  */
	public void editEmployee(String firstName, String lastName, String role, String dosesNum, Boolean hospitalized) {
		verifyPage();
		Actions actions = new Actions(driver);
		String checkbox = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/ancestor::tr//td[7]"))).getText();
		
//		Select the Added Employee in the table below
		try {
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/following-sibling::td[@data-label='Title']")))).perform();
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/following-sibling::td[@data-label='Title']")))).click().perform();
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Employee with name " + firstName + " " + lastName + " not found in Involved section. (Edit failed)");
		}
		
//		Fill in the employee information:
//		Role
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(employeeRoleDropdown))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + role + "']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Doses
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();          
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit))).click().sendKeys(dosesNum).perform();
		
//		Hospitalized
		if (hospitalized && checkbox.equals("No")) {
			wait.until(ExpectedConditions.elementToBeClickable(hospitalizedCheckbox)).click();
			checkbox = "Yes";
		}
		else if (!hospitalized && checkbox.equals("Yes")) {
			wait.until(ExpectedConditions.elementToBeClickable(hospitalizedCheckbox)).click();
			checkbox = "No";
		}
		wait.until(ExpectedConditions.elementToBeClickable(commitButton)).click();
		
//		TODO: Check the changes are saved
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/following-sibling::td[text()='" + role + "']/following-sibling::td[text()='" + dosesNum + "' and @data-label='Sign']/following-sibling::td[text()='" + checkbox + "']/ancestor::tr")));
	}
	
	/**
	  * Delete employee in Involved section (NOTE: Assuming each person's name in "Employee" are unique)
	  *
	  * @param firstName
	  * @param lastName
	  */
	public void deleteEmployee(String firstName, String lastName) {
		verifyPage();
		Actions actions = new Actions(driver);
		try {			
			WebElement userTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/following-sibling::td[@data-label='Title']/ancestor::tr//td[last()-1]/button[@class='" + buttonClass + "']")));
			actions.moveToElement(userTableRow).perform();
			actions.moveToElement(userTableRow).click().perform(); 
			//Work around. When entering Involved section in Update mode, there is a scrolling animation which messes with the script.
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Employee with name " + firstName + " " + lastName + " not found in Involved section.");
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/following-sibling::td[@data-label='OTSID']/ancestor::tr//td[last()-1]/button[@class='" + buttonClass + "']")));
	}
	
	
	/**
	  * Add Others in Involved section
	  *
	  * @param firstName
	  * @param lastName
	  * @param category (Vendor, Volunteer, FeeForServiceProvider, ContractProvider, Visitor, AgencyStaff, Other)
	  * @param role (Witness, Participant, Other)
	  * @param dosesNum
	  * @param hospitalized
	  */
	public void addOthers(String firstName, String lastName, String category, String role, String dosesNum, Boolean hospitalized) {
//		Fill in the Other information
//		Last name
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(otherLastNameField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(otherLastNameField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform(); 
		wait.until(ExpectedConditions.elementToBeClickable(otherLastNameField)).sendKeys(lastName);
		
//		First name
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(otherFirstNameField))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(otherFirstNameField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform(); 
		wait.until(ExpectedConditions.elementToBeClickable(otherFirstNameField)).sendKeys(firstName);
		
//		Category
		wait.until(ExpectedConditions.visibilityOfElementLocated(otherCategoryField)).click();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + category + "']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Role
		wait.until(ExpectedConditions.visibilityOfElementLocated(otherRoleField)).click();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + role + "']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Doses
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(otherNumberDosesField))).click().perform();
		actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(otherNumberDosesField)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();          
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(otherNumberDosesField))).click().sendKeys(dosesNum).perform();
		
		
//		Hospitalized
		String checkbox = "No";
		if (hospitalized) {
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(otherHospitalizedCheckbox))).click().perform();
			checkbox = "Yes";
		}
		
//		Click "ADD TO OTHER" button
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(addToOtherButton))).click().perform();
		
//		TODO: Check Other is saved
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/following-sibling::td[text()='" + category + "']/following-sibling::td[text()='" + role + "']/following-sibling::td[text()='" + dosesNum + "' and @data-label='Sign']/following-sibling::td[text()='" + checkbox + "']/ancestor::tr")));
		
//		Reset the Dropdowns (Otherwise the next time we add others the dropdown will not be selected by the selector)
//		Category
		wait.until(ExpectedConditions.visibilityOfElementLocated(otherCategoryFilled)).click();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='Select a Category']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(otherCategoryFilled));
		
//		Role
		wait.until(ExpectedConditions.visibilityOfElementLocated(otherRoleFilled)).click();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='Select a Involved Role']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(otherRoleFilled));
		
//		Hospitalized
		if (hospitalized) {
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(otherHospitalizedCheckbox))).click().perform();
		}
	}
	
	/**
	  * Edit Others in Involved section
	  *
	  * @param firstName
	  * @param lastName
	  * @param category (Vendor, Volunteer, FeeForServiceProvider, ContractProvider, Visitor, AgencyStaff, Other)
	  * @param role (Witness, Participant, Other)
	  * @param dosesNum
	  * @param hospitalized
	  */
	public void editOther(String firstName, String lastName, String category, String role, String dosesNum, Boolean hospitalized) {
		verifyPage();
		Actions actions = new Actions(driver);
		String checkbox = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/ancestor::tr//td[5]"))).getText();
		
//		Select the Added Employee in the table below
		try {
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/ancestor::tr//td[6]/button[@class='" + buttonClass + "']/ancestor::tr")))).perform();
			actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/ancestor::tr//td[6]/button[@class='" + buttonClass + "']/ancestor::tr")))).click().perform();
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Other with name " + firstName + " " + lastName + " not found in Involved section. (Edit failed)");
		}
		
//		Category
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(otherCatEditDropdown))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + category + "']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Role
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(otherRoleEditDropdown))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[contains(@class, '" + selectDropdownOption +  "') and text()='" + role + "']"))))
					.click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(updateDialogDropdown));
		
//		Doses
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit))).click().perform();
        actions.keyDown(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit)), Keys.CONTROL)  // Press CTRL
        .sendKeys("A")                   // Press A (to select all)
        .sendKeys(Keys.BACK_SPACE)           // Press BACKSPACE (to delete the text)
        .keyUp(Keys.CONTROL)                // Release CTRL
        .perform();          
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(numberDosesFieldEdit))).click().sendKeys(dosesNum).perform();
		
//		Hospitalized
		if (hospitalized && checkbox.equals("No")) {
			wait.until(ExpectedConditions.elementToBeClickable(hospitalizedCheckbox)).click();
			checkbox = "Yes";
		}
		else if (!hospitalized && checkbox.equals("Yes")) {
			wait.until(ExpectedConditions.elementToBeClickable(hospitalizedCheckbox)).click();
			checkbox = "No";
		}
		wait.until(ExpectedConditions.elementToBeClickable(commitButton)).click();
		
//		TODO: Check the changes are saved
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//td[text()='" + firstName + "' and text()='" + lastName + "' and @data-label='Employee']/following-sibling::td[text()='" + category + "']/following-sibling::td[text()='" + role + "']/following-sibling::td[text()='" + dosesNum + "' and @data-label='Sign']/following-sibling::td[text()='" + checkbox + "']/ancestor::tr")));
	}
	
	
	/**
	  * Delete Other in Involved section (NOTE: Assuming each person's name in "Other" are unique)
	  *
	  * @param firstName 
	  * @param lastName
	  */
	public void deleteOther(String firstName, String lastName) {
		verifyPage();
		Actions actions = new Actions(driver);
		try {			
			WebElement userTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/ancestor::tr//td[6]/button[@class='" + buttonClass + "']")));
			actions.moveToElement(userTableRow).perform();
			actions.moveToElement(userTableRow).click().perform(); 
			//Work around. When entering Involved section in Update mode, there is a scrolling animation which messes with the script.
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Other with name " + firstName + " " + lastName + " not found in Involved section.");
		}
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='" + lastName + "' and text()='" + firstName + "' and @data-label='Employee']/ancestor::tr//td[6]/button[@class='" + buttonClass + "']")));
	}
	
	public void verifyDuplicateInmateAlert() {
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3)); //Otherwise test will wait 10 seconds if the test is failing
		
		wait2.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='" + alertErrorClass + "']//div[@class='" + alertErrorMessageClass + "' and text()='This inmate is already in the list.']")));
	}
	
	public void verifyDuplicateEmployeeAlert() {
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3)); //Otherwise test will wait 10 seconds if the test is failing
		
		wait2.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='" + alertErrorClass + "']//div[@class='" + alertErrorMessageClass + "' and text()='This employee is already in the list.']")));
	}
	
	public void clickNext() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(nextButton))).perform();
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