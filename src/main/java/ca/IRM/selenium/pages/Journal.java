package ca.IRM.selenium.pages;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Journal {
	
	WebDriver driver;
	WebDriverWait wait;
	
	By nextPageButton = By.xpath("//button[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-icon mud-flip-x-rtl' and @aria-label='Next page']");
	By previousPageButton = By.xpath("//button[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-icon mud-flip-x-rtl' and @aria-label='Previous page']");
	By firstPageButton = By.xpath("//button[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-icon mud-flip-x-rtl' and @aria-label='First page']");
	By lastPageButton = By.xpath("//button[@class='mud-button-root mud-icon-button mud-ripple mud-ripple-icon mud-flip-x-rtl' and @aria-label='Last page']");
	By rowPerPageDropdown = By.xpath("//div[@class='mud-input mud-input-text mud-input-adorned-end mud-shrink mud-select-input']");
	
	String header = "mud-typography mud-typography-h6";
	String rowPerPageItems = "mud-typography mud-typography-body2";
	
	public Journal(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void verifyPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//h6[@class='" + header + "' and contains(text(), 'Histories for : ')]")));
	}
	
	/**
	  * Select number of rows per page
	  *
	  * @param rows ("10", "25", "50", "100")
	  */
	public void selectRowsPerPage(String rows) {
		Actions actions = new Actions(driver);
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(rowPerPageDropdown))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[@class='" + rowPerPageItems + "' and text()='" + rows + "']")))).click().perform();
	}
	
	public void clickNextPage() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(nextPageButton))).click().perform();
	}
	
	public void clickPreviousPage() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(previousPageButton))).click().perform();
	}
	
	public void clickFirstPage() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(firstPageButton))).click().perform();
	}
	
	public void clickLastPage() {
		verifyPage();
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(lastPageButton))).click().perform();
	}
	
	/**
	  * Verify new value in Incident row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param incidentDate (String: ie. "19/02/2025" or "" if IncidentDate was not updated)
	  * @param incidentTime (String: ie. "12:00:00" or "" if IncidentTime was not updated. Note: incidentDate must also be provided along incidentTime)
	  * @param facility (String: ie. "ALGOMA TREATMENT & REMAND CTR-ADULT" or "" if facility (ie. Location) was not updated)
	  * @param priority (String: ie. "One" or "" if priority was not updated)
	  * @param generalLocation (String: ie. "Washroom" or "" if generalLocation was not updated)
	  * @param unitRange (String: ie. "ALPHA_F" or "" if unitRange was not updated)
	  * @param locationDetails (String: Any text or "" if locationDetails was not updated)
	  *  
	  */
	public void verifyIncident(String dateUpdated, String timeUpdated, String incidentDate, String incidentTime, String facility, String priority, String generalLocation, String unitRange, String locationDetails) {
		String incidentRowNewVal = "/following-sibling::td[text()='Incident' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue']";
		String newVal;
		
//		Get the new value
		try {			
			WebElement incidentTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + dateUpdated + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal)));
			newVal = incidentTableRow.getText();
			System.out.println(newVal);
			
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + " and Time: " + timeUpdated + " not found.");
		}
		
//		Verify all the given info (the parameters) are present in the newVal string variable
		if (incidentDate.length() != 0) {
			if (!newVal.contains("IncidentDate:" + incidentDate)) {
				throw new NoSuchElementException("IncidentDate:" + incidentDate + " not found in Journal's Incident row.");
			}
		}
		
		if (incidentTime.length() != 0) {
			if (!newVal.contains("IncidentTime:" + incidentDate + " " + incidentTime)) {
				throw new NoSuchElementException("IncidentTime:" + incidentDate + " " + incidentTime + " not found in Journal's Incident row.");
			}
		}
		
		if (facility.length() != 0) { // Location
			if (!newVal.contains("Facility:" + facility)) {
				throw new NoSuchElementException("Facility:" + facility + " not found in Journal's Incident row.");
			}
		}
		
		if (priority.length() != 0) {
			if (!newVal.contains("Priority:" + priority)) {
				throw new NoSuchElementException("Priority:" + priority + " not found in Journal's Incident row.");
			}
		}
		
		if (generalLocation.length() != 0) {
			if (!newVal.contains("GeneralLocation:" + generalLocation)) {
				throw new NoSuchElementException("GeneralLocation:" + generalLocation + " not found in Journal's Incident row.");
			}
		}
		
		if (unitRange.length() != 0) {
			if (!newVal.contains("UnitRange:" + unitRange)) {
				throw new NoSuchElementException("UnitRange:" + unitRange + " not found in Journal's Incident row.");
			}
		}
		
		if (locationDetails.length() != 0) {
			if (!newVal.contains("LocationDetails:" + locationDetails)) {
				throw new NoSuchElementException("LocationDetails:" + locationDetails + " not found in Journal's Incident row.");
			}
		}
	}
	
	/**
	  * Verify new value in RegionalOfficeContacted row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param regionAdvisedBy (String: ie. "Phone"/"Email" or "" if regionAdvisedBy was not updated)
	  * @param regionalOfficalNotified (String: any text provided or "" if regionalOfficalNotified was left empty)
	  * @param regionAdvisedOn (String: ie. "19/02/2025" or "" if IncidentDate was not updated)
	  * @param regionAdvisedAt (String: ie. "12:00:00" or "" if regionAdvisedAt was not updated. Note: regionAdvisedOn must also be provided along regionAdvisedAt)
	  * @param regionlOfficial (String: name of the official or "" if left empty )
	  *  
	  */
	public void verifyRegionalOfficeContacted(String dateUpdated, String timeUpdated, String regionAdvisedBy, String regionalOfficalNotified, String regionAdvisedOn, String regionAdvisedAt, String regionlOfficial) {
		String incidentRowNewVal = "/following-sibling::td[text()='RegionalOfficeContacted' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue']";
		String newVal;
		
//		Get the new value
		try {			
			WebElement incidentTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + dateUpdated + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal)));
			newVal = incidentTableRow.getText();
			System.out.println(newVal);
			
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + " and Time: " + timeUpdated + " not found.");
		}
		
//		Verify all the given info (the parameters) are present in the newVal string variable
		if (regionAdvisedBy.length() != 0) {
			if (!newVal.contains("RegionAdvisedBy:" + regionAdvisedBy)) {
				throw new NoSuchElementException("RegionAdvisedBy:" + regionAdvisedBy + " not found in Journal's RegionalOfficeContacted row.");
			}
		}
		
		if (regionalOfficalNotified.length() != 0) {
			if (!newVal.contains("RegionalOfficalNotified:" + regionalOfficalNotified)) {
				throw new NoSuchElementException("RegionalOfficalNotified:" + regionalOfficalNotified + " not found in Journal's RegionalOfficeContacted row.");
			}
		}
		
		if (regionAdvisedOn.length() != 0) { 
			if (!newVal.contains("RegionAdvisedOn:" + regionAdvisedOn)) {
				throw new NoSuchElementException("RegionAdvisedOn:" + regionAdvisedOn + " not found in Journal's RegionalOfficeContacted row.");
			}
		}
		
		if (regionAdvisedAt.length() != 0) {
			if (!newVal.contains("RegionAdvisedAt:" + regionAdvisedOn + " " + regionAdvisedAt)) {
				throw new NoSuchElementException("RegionAdvisedAt:" + regionAdvisedOn + " " + regionAdvisedAt + " not found in Journal's RegionalOfficeContacted row.");
			}
		}
		
		if (regionlOfficial.length() != 0) {
			if (!newVal.contains("RegionlOfficial:" + regionlOfficial)) {
				throw new NoSuchElementException("RegionlOfficial:" + regionlOfficial + " not found in Journal's RegionalOfficeContacted row.");
			}
		}
	}
	
	/**
	  * Verify new value in MediaAware row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param isMediaAwareCommonStatus (String: ie. "Yes"/"No"/"Unknown" or "" if isMediaAwareCommonStatus was left unanswered)
	  * @param details (String: any text provided or "" if details was left empty)
	  *  
	  */
	public void verifyMediaAware(String dateUpdated, String timeUpdated, String isMediaAwareCommonStatus , String details) {
		String incidentRowNewVal = "/following-sibling::td[text()='MediaAware' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue']";
		String newVal;
		
//		Get the new value
		try {			
			WebElement incidentTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + dateUpdated + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal)));
			newVal = incidentTableRow.getText();
			newVal = newVal.replaceAll("\\s",""); //Remove all whitespace
			System.out.println(newVal);
			
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + " and Time: " + timeUpdated + " not found.");
		}
		
//		Verify all the given info (the parameters) are present in the newVal string variable
		if (isMediaAwareCommonStatus.length() != 0) {
			if (!newVal.contains("IsMediaAwareCommonStatus:" + isMediaAwareCommonStatus)) {
				throw new NoSuchElementException("IsMediaAwareCommonStatus:" + isMediaAwareCommonStatus + " not found in Journal's MediaAware row.");
			}
		}
		
		if (details.length() != 0) {
			if (!newVal.contains("Details:" + details)) {
				throw new NoSuchElementException("Details:" + details + " not found in Journal's MediaAware row.");
			}
		}
	}

	/**
	  * Verify new value in InvolvedIncidentTypes row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param incident (String: ie. "Assault")
	  *  
	  */
	public void verifyInvolvedIncidentTypes(String dateUpdated, String timeUpdated, String incident) {
		String incidentRowNewVal = "/following-sibling::td[text()='InvolvedIncidentTypes' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue']";
		String newVal;
		
//		Get the new value
		try {			
			WebElement incidentTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + dateUpdated + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal)));
			newVal = incidentTableRow.getText();

			System.out.println(newVal);
			
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of InvolvedIncidentTypes not found.");
		}
		
//		Verify all the given info (the parameters) are present in the newVal string variable
		if (incident.length() != 0) {
			if (!newVal.contains(incident)) {
				throw new NoSuchElementException("Incident Type: \"" + incident + "\" not found in Journal's InvolvedIncidentTypes row.");
			}
		}
		
	}
	
	/**
	  * Verify new value in InvolvedIncidentTypes row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param incident (String: ie. "Assault")
	  * @param question (String: ie. "CCRL notified if racially motivated")
	  *  
	  */
	public void verifyIncidentTypeAnswers(String dateUpdated, String timeUpdated, String incident, String question) {
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		String newVal = "IncidentType:" + incident + "; Question:" + question;
		
		String incidentRowNewVal = "/following-sibling::td[text()='IncidentTypeAnswers' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue' and text()='" + newVal + "']";
		String xpathExpression = "//td[text()='" + dateUpdated 
		        + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" 
		        + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal;

		// Get the new value
		try {            
			String check = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression))).getText();
			System.out.println(check);
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of InvolvedIncidentTypes with the newVal:'" + newVal + "' not found.");
		}
	}
	
	/**
	  * Verify new value in SupportingDocument row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param incidentType (String: ie. "Death of Staff"/"Labour Activities"/"Employee Medical Emergency"/"External Media Inquiries")
	  * @param documentName (String: ie. "Mol Order"/"Field Visit Report"/"Copy of media article")
	  * @param supportingDocumentDescription (String: name of the document uploaded ie. "someName.pdf")
	  *  
	  */
	public void verifySupportingDocument(String dateUpdated, String timeUpdated, String incidentType, String documentName, String supportingDocumentDescription) {
		String incidentRowNewVal = "/following-sibling::td[text()='SupportingDocument' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue']";
		String newVal;
		
//		Get the new value
		try {			
			WebElement incidentTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + dateUpdated + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal)));
			newVal = incidentTableRow.getText();

			System.out.println(newVal);
			
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of SupportingDocument not found.");
		}
		
//		Verify all the given info (the parameters) are present in the newVal string variable
		if (incidentType.length() != 0) {
			if (!newVal.contains("IncidentType:" + incidentType)) {
				throw new NoSuchElementException("Incident Type: \"" + incidentType + "\" not found in Journal's SupportingDocument row.");
			}
		}
		
		if (documentName.length() != 0) {
			if (!newVal.contains("DocumentName:" + documentName)) {
				throw new NoSuchElementException("Document Name: \"" + documentName + "\" not found in Journal's SupportingDocument row.");
			}
		}
		
		if (supportingDocumentDescription.length() != 0) {
			if (!newVal.contains("SupportingDocumentDescription:" + supportingDocumentDescription)) {
				throw new NoSuchElementException("Supporting Document Description: \"" + supportingDocumentDescription + "\" not found in Journal's SupportingDocument row.");
			}
		}
	}
	
	/**
	  * Verify new value in PoliceContacted row (Part 1. Note: This method has been divided to two parts as there is too many parameters)
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param isPoliceContacted (String: "True"/"False" or "" if no change)
	  * @param reasonPoliceNotContactedId (String: "Not a Police matter"/"Decision not to lay charges" or "" if no change) TODO: currently a bug, does not show the actual text only "0" or "1"
	  * @param willPoliceBeAttenting (String: "True"/"False" or "" if no change)
	  * @param criminalCharges (String: "True"/"False" or "" if no change or Unknown) TODO: may need to double check if "" is expected when "Unknown" is selected 
	  * @param policeContactedDate (String: ie. "19/02/2025" or "" if PoliceContactedDate was not updated)
	  * @param policeContactedTime (String: ie. "12:00:00" or "" if IncidentTime was not updated. Note: policeContactedDatepoliceContactedDate must also be provided along policeContactedTime)
	  *  
	  */
	public void verifyPoliceContacted(String dateUpdated, String timeUpdated, String isPoliceContacted, String reasonPoliceNotContactedId, String willPoliceBeAttenting, String criminalCharges, String policeContactedDate, String policeContactedTime) {
		String incidentRowNewVal = "/following-sibling::td[text()='PoliceContacted' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue']";
		String newVal;
		
//		Get the new value
		try {			
			WebElement incidentTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + dateUpdated + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal)));
			newVal = incidentTableRow.getText();

			System.out.println(newVal);
			
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of PoliceContacted not found.");
		}
		
//		Verify all the given info (the parameters) are present in the newVal string variable
		if (isPoliceContacted.length() != 0) {
			if (!newVal.contains("IsPoliceContacted:" + isPoliceContacted)) {
				throw new NoSuchElementException("IsPoliceContacted:\"" + isPoliceContacted + "\" not found in Journal's PoliceContacted row.");
			}
		}
		
		if (reasonPoliceNotContactedId.length() != 0) {
			if (!newVal.contains("ReasonPoliceNotContactedId:" + reasonPoliceNotContactedId)) {
				throw new NoSuchElementException("ReasonPoliceNotContactedId:\"" + reasonPoliceNotContactedId + "\" not found in Journal's PoliceContacted row.");
			}
		}
		
		if (willPoliceBeAttenting.length() != 0) {
			if (!newVal.contains("WillPoliceBeAttenting:" + willPoliceBeAttenting)) {
				throw new NoSuchElementException("WillPoliceBeAttenting:\"" + willPoliceBeAttenting + "\" not found in Journal's PoliceContacted row.");
			}
		}
		
		if (criminalCharges.length() != 0) {
			if (!newVal.contains("CriminalCharges:" + criminalCharges)) {
				throw new NoSuchElementException("CriminalCharges:\"" + criminalCharges + "\" not found in Journal's PoliceContacted row.");
			}
		}
		
		if (policeContactedDate.length() != 0) {
			if (!newVal.contains("PoliceContactedDate:" + policeContactedDate)) {
				throw new NoSuchElementException("PoliceContactedDate:\"" + policeContactedDate + "\" not found in Journal's PoliceContacted row.");
			}
		}
		
		if (policeContactedTime.length() != 0) {
			if (!newVal.contains("PoliceContactedTime:" + policeContactedDate + " " + policeContactedTime)) {
				throw new NoSuchElementException("PoliceContactedTime:\"" + policeContactedTime + "\" not found in Journal's PoliceContacted row.");
			}
		}
	}
	
	/**
	  * Verify new value in PoliceContacted row (Part 2. Note: This method has been divided to two parts as there is too many parameters)
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param personContacted (String: some name or "" if no change)
	  * @param policeContactedBy (String: some name or "" if no change)
	  * @param policeService (String: some service or "" if no change)
	  * @param policeContactedMetnod (String: service or "" if no change)
	  * @param policeTelephone (String: ie. "6471112222" or "" if no change)
	  * @param policeCaseOccurance (String: some number or "" if no change)
	  *  
	  */
	public void verifyPoliceContacted2(String dateUpdated, String timeUpdated, String personContacted, String policeContactedBy, String policeService, String policeContactedMetnod, String policeTelephone, String policeCaseOccurance) {
		String incidentRowNewVal = "/following-sibling::td[text()='PoliceContacted' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue']";
		String newVal;
		
//		Get the new value
		try {			
			WebElement incidentTableRow = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//td[text()='" + dateUpdated + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal)));
			newVal = incidentTableRow.getText();

			System.out.println(newVal);
			
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of PoliceContacted not found.");
		}
		
//		Verify all the given info (the parameters) are present in the newVal string variable
		if (personContacted.length() != 0) {
			if (!newVal.contains("PersonContacted:" + personContacted)) {
				throw new NoSuchElementException("PersonContacted:\"" + personContacted + "\" not found in Journal's PoliceContacted row.");
			}
		}
		
		if (policeContactedBy.length() != 0) {
			if (!newVal.contains("PoliceContactedBy:" + policeContactedBy)) {
				throw new NoSuchElementException("PoliceContactedBy:\"" + policeContactedBy + "\" not found in Journal's PoliceContacted row.");
			}
		}
		
		if (policeService.length() != 0) {
			if (!newVal.contains("PoliceService:" + policeService)) {
				throw new NoSuchElementException("PoliceService:\"" + policeService + "\" not found in Journal's PoliceContacted row.");
			}
		}
		
		if (policeContactedMetnod.length() != 0) {
			if (!newVal.contains("PoliceContactedMetnod:" + policeContactedMetnod)) {
				throw new NoSuchElementException("PoliceContactedMetnod:\"" + policeContactedMetnod + "\" not found in Journal's PoliceContacted row.");
			}
		}
		
		if (policeTelephone.length() != 0) {
			String teleFormat = "";
			for (int i = 0; i < policeTelephone.length(); i++) {
			    char c = policeTelephone.charAt(i);
			    if (i == 0) {
			    	teleFormat = teleFormat + '(';
			    }
			    else if (i == 3) {
			    	teleFormat = teleFormat + ") ";
			    }
			    else if (i == 6) {
			    	teleFormat = teleFormat + ' ';
			    }
			    teleFormat = teleFormat + c;
			}
			System.out.println(teleFormat);
			if (!newVal.contains("PoliceTelephone:" + teleFormat)) {
				throw new NoSuchElementException("PoliceTelephone:\"" + teleFormat + "\" not found in Journal's PoliceContacted row.");
			}
		}
		
		if (policeCaseOccurance.length() != 0) {
			if (!newVal.contains("PoliceCaseOccurance:" + policeCaseOccurance)) {
				throw new NoSuchElementException("PoliceCaseOccurance:\"" + policeCaseOccurance + "\" not found in Journal's PoliceContacted row.");
			}
		}
	}
	
	/**
	  * Verify new total value in InvolvedEmployee row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param totalEmplyeeInvolved (String: "1" or any number)
	  * @param totalInvolved (String: "1" or any number)
	  * @param totalEmplyeeDosesAdministered (String: "1" or any number)
	  * @param totalDosesAdministered (String: "1" or any number)
	  * @param totalEmployeeHospitalized (String: "1" or any number)
	  * @param totalHospitalized (String: "1" or any number)
	  *  
	  */
	public void verifyInvolvedEmployeeTotal(String dateUpdated, String timeUpdated, String totalEmplyeeInvolved, String totalInvolved, String totalEmplyeeDosesAdministered, String totalDosesAdministered, String totalEmployeeHospitalized, String totalHospitalized) {
		String newVal;
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		String expectedNewVal = "";
		
		String[][] params = {
		            {"TotalEmplyeeInvolved:", totalEmplyeeInvolved},
		            {"TotalInvolved:", totalInvolved},
		            {"TotalEmplyeeDosesAdministered:", totalEmplyeeDosesAdministered},
		            {"TotalDosesAdministered:", totalDosesAdministered},
		            {"TotalEmployeeHospitalized:", totalEmployeeHospitalized},
		            {"TotalHospitalized:", totalHospitalized}
		        };
		
		 for (int i = 0; i < params.length; i++) {
	            String titleName = params[i][0];
	            String param = params[i][1];
	            
	            if (param.length() != 0) {
	                expectedNewVal = expectedNewVal + titleName + param + "; ";
	            }
	        }
		
		// Removes the trailing space and semicolon
		expectedNewVal = expectedNewVal.substring(0, expectedNewVal.length() - 2);
		
		String incidentRowNewVal = "/following-sibling::td[text()='Involve' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue' and contains(text(), '" + expectedNewVal + "')]";
		String xpathExpression = "//td[text()='" + dateUpdated 
		        + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" 
		        + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal;

		// Get the new value
		try {            
			newVal = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression))).getText();
			System.out.println(newVal);
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of Involve with the newVal containing:'" + expectedNewVal + "' not found.");
		}
		
	}
	
	/**
	  * Verify new value in verifyInvolvedEmployee row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param numberOfAntiOpioidAdministered (String: "1" or any number)
	  * @param hospitalized (String: "True"/"False or "" if no change/not provided)
	  * @param involvedRole (String: "Witness" or "" if no change/not provided)
	  *  
	  */
	public void verifyInvolvedEmployeeDetails(String dateUpdated, String timeUpdated, String numberOfAntiOpioidAdministered, String hospitalized, String involvedRole) {
		String newVal;
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		String expectedNewVal = "";
		
		String[][] params = {
		            {"NumberOfAntiOpioidAdministered:", numberOfAntiOpioidAdministered},
		            {"Hospitalized:", hospitalized},
		            {"InvolvedRole:", involvedRole},
		        };
		
		 for (int i = 0; i < params.length; i++) {
	            String titleName = params[i][0];
	            String param = params[i][1];
	            
	            if (param.length() != 0) {
	                expectedNewVal = expectedNewVal + titleName + param + "; ";
	            }
	        }
		
		// Removes the trailing space and semicolon
		expectedNewVal = expectedNewVal.substring(0, expectedNewVal.length() - 2);
		
		
		String incidentRowNewVal = "/following-sibling::td[text()='InvolvedEmployee' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue' and contains(text(), '" + expectedNewVal + "')]";
		String xpathExpression = "//td[text()='" + dateUpdated 
		        + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" 
		        + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal;

		// Get the new value
		try {            
			newVal = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression))).getText();
			System.out.println(newVal);
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of InvolvedEmployee with the newVal containing:'" + expectedNewVal + "' not found.");
		}
		
	}
	
	/**
	  * Verify new value in verifyInvolvedEmployee row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param name (String: some name or "" if no change)
	  * @param title (String: some title or "" or "" if no change/not provided)
	  * @param department (String: some department name or "" if no change/not provided)
	  * @param location (String: some location or "" if no change/not provided)
	  *  
	  */
	public void verifyInvolvedEmployee(String dateUpdated, String timeUpdated, String name, String title, String department, String location) {
		String newVal;
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		String expectedNewVal = "Name:" + name;
		
		String incidentRowNewVal = "/following-sibling::td[text()='InvolvedEmployee' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue' and contains(text(), '" + expectedNewVal + "')]";
		String xpathExpression = "//td[text()='" + dateUpdated 
		        + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" 
		        + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal;

		// Get the new value
		try {            
			newVal = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression))).getText();
			System.out.println(newVal);
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of InvolvedEmployee with the newVal containing:'" + expectedNewVal + "' not found.");
		}
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("Title:", title);
		params.put("Department:", department);
		params.put("Location:", location);
		
//		Verify all the given info (the parameters) are present in the newVal string variable
		for  (Entry<String, String> entry : params.entrySet()) {
		    String titleName = entry.getKey();
		    String param = entry.getValue();
		    
		    if (param.length() != 0) {
				if (!newVal.contains(titleName + param)) {
					throw new NoSuchElementException(titleName + "\"" + param + "\" not found in Journal's InvolvedEmployee row.");
				}
			}
		}
		
	}
	
	/**
	  * Verify new total value in InvolvedInmates row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param totalInmanteInvolved (String: "1" or any number)
	  * @param totalInvolved (String: "1" or any number)
	  * @param totalInmatesDosesAdministered (String: "1" or any number)
	  * @param totalDosesAdministered (String: "1" or any number)
	  * @param totalInmatesHospitalized (String: "1" or any number)
	  * @param totalHospitalized (String: "1" or any number)
	  *  
	  */
	public void verifyInvolvedInmateTotal(String dateUpdated, String timeUpdated, String totalInmanteInvolved, String totalInvolved, String totalInmatesDosesAdministered, String totalDosesAdministered, String totalInmatesHospitalized, String totalHospitalized) {
		String newVal;
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		String expectedNewVal = "";
		
		String[][] params = {
		            {"TotalInmanteInvolved:", totalInmanteInvolved},
		            {"TotalInvolved:", totalInvolved},
		            {"TotalInmatesDosesAdministered:", totalInmatesDosesAdministered},
		            {"TotalDosesAdministered:", totalDosesAdministered},
		            {"TotalInmatesHospitalized:", totalInmatesHospitalized},
		            {"TotalHospitalized:", totalHospitalized}
		        };
		
		 for (int i = 0; i < params.length; i++) {
	            String titleName = params[i][0];
	            String param = params[i][1];
	            
	            if (param.length() != 0) {
	                expectedNewVal = expectedNewVal + titleName + param + "; ";
	            }
	        }
		
		// Removes the trailing space and semicolon
		expectedNewVal = expectedNewVal.substring(0, expectedNewVal.length() - 2);
		
		String incidentRowNewVal = "/following-sibling::td[text()='Involve' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue' and contains(text(), '" + expectedNewVal + "')]";
		String xpathExpression = "//td[text()='" + dateUpdated 
		        + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" 
		        + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal;

		// Get the new value
		try {            
			newVal = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression))).getText();
			System.out.println(newVal);
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of Involve with the newVal containing:'" + expectedNewVal + "' not found.");
		}
		
	}
	
	/**
	  * Verify new total value in InvolvedInmates row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param numberOfAntiOpioidAdministered (String: "1" or any number)
	  * @param hospitalized (String: "True"/"False or "" if no change/not provided)
	  * @param involvedRole (String: "Witness" or "" if no change/not provided)
	  *  
	  */
	public void verifyInvolvedInmateDetails(String dateUpdated, String timeUpdated, String numberOfAntiOpioidAdministered, String hospitalized, String involvedRole) {
		String newVal;
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		String expectedNewVal = "";
		
		String[][] params = {
		            {"NumberOfAntiOpioidAdministered:", numberOfAntiOpioidAdministered},
		            {"Hospitalized:", hospitalized},
		            {"InvolvedRole:", involvedRole},
		        };
		
		 for (int i = 0; i < params.length; i++) {
	            String titleName = params[i][0];
	            String param = params[i][1];
	            
	            if (param.length() != 0) {
	                expectedNewVal = expectedNewVal + titleName + param + "; ";
	            }
	        }
		
		// Removes the trailing space and semicolon
		expectedNewVal = expectedNewVal.substring(0, expectedNewVal.length() - 2);
		
		
		String incidentRowNewVal = "/following-sibling::td[text()='InvolvedInmates' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue' and contains(text(), '" + expectedNewVal + "')]";
		String xpathExpression = "//td[text()='" + dateUpdated 
		        + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" 
		        + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal;

		// Get the new value
		try {            
			newVal = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression))).getText();
			System.out.println(newVal);
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of InvolvedInmates with the newVal containing:'" + expectedNewVal + "' not found.");
		}
		
	}
	
	/**
	  * Verify new value in InvolvedInmates row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param name (String: some name or "" if no change)
	  * @param otisId (String: some id number ie. "12131431" or "" if no change/not provided)
	  *  
	  */
	public void verifyInvolvedInmate(String dateUpdated, String timeUpdated, String name, String otisId) {
		String newVal;
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		String expectedNewVal = "Name:" + name + "; OTISID:";
		
		String incidentRowNewVal = "/following-sibling::td[text()='InvolvedInmates' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue' and contains(text(), '" + expectedNewVal + "')]";
		String xpathExpression = "//td[text()='" + dateUpdated 
		        + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" 
		        + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal;

		// Get the new value
		try {            
			newVal = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression))).getText();
			System.out.println(newVal);
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of InvolvedIncidentTypes with the newVal containing:'" + expectedNewVal + "' not found.");
		}
		

	    if (otisId.length() != 0) {
			if (!newVal.contains("OTISID:" + otisId)) {
				throw new NoSuchElementException("OTISID: \"" + otisId + "\" not found in Journal's InvolvedInmates row.");
			}
	    }
	}
	
	/**
	  * Verify new total value in InvolvedOthers row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param totalOthersInvolved (String: "1" or any number)
	  * @param totalInvolved (String: "1" or any number)
	  * @param totalOthersDosesAdministered (String: "1" or any number)
	  * @param totalDosesAdministered (String: "1" or any number)
	  * @param totalOthersHospitalized (String: "1" or any number)
	  * @param totalHospitalized (String: "1" or any number)
	  * 
	  *  TODO: Note current bug when adding Other in involve section. 
	  *  The Involve row that breaks down the total numbers regarding the "Other" added do not display in Journal until a new Inmate/Employee is added
	  *  The time the TotalOthers row appear is the same time you added the Inmate/Employee 
	  */
	public void verifyInvolvedOthersTotal(String dateUpdated, String timeUpdated, String totalOthersInvolved, String totalInvolved, String totalOthersDosesAdministered, String totalDosesAdministered, String totalOthersHospitalized, String totalHospitalized) {
		String newVal;
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		String expectedNewVal = "";
		
		String[][] params = {
		            {"TotalOthersInvolved:", totalOthersInvolved},
		            {"TotalInvolved:", totalInvolved},
		            {"TotalOthersDosesAdministered:", totalOthersDosesAdministered},
		            {"TotalDosesAdministered:", totalDosesAdministered},
		            {"TotalOthersHospitalized:", totalOthersHospitalized},
		            {"TotalHospitalized:", totalHospitalized}
		        };
		
		 for (int i = 0; i < params.length; i++) {
	            String titleName = params[i][0];
	            String param = params[i][1];
	            
	            if (param.length() != 0) {
	                expectedNewVal = expectedNewVal + titleName + param + "; ";
	            }
	        }
		
		// Removes the trailing space and semicolon
		expectedNewVal = expectedNewVal.substring(0, expectedNewVal.length() - 2);
		
		String incidentRowNewVal = "/following-sibling::td[text()='Involve' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue' and contains(text(), '" + expectedNewVal + "')]";
		String xpathExpression = "//td[text()='" + dateUpdated 
		        + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" 
		        + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal;

		// Get the new value
		try {            
			newVal = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression))).getText();
			System.out.println(newVal);
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of Involve with the newVal containing:'" + expectedNewVal + "' not found.");
		}
	}
	
	/**
	  * Verify new value in InvolvedOthers row
	  *
	  * @param dateUpdated (String: the date of the report change)
	  * @param timeUpdated (String: the time of the report change)
	  * @param name (String: some name or "" if no change)
	  * @param numberOfAntiOpioidAdministered (String: "1" or any number)
	  * @param hospitalized (String: "True"/"False or "" if no change/not provided)
	  * @param involvedRole (String: "Witness" or "" if no change/not provided)
	  * @param involvedCategory (String: "Volunteer"  or "" if no change/not provided)
	  *  
	  */
	public void verifyInvolvedOthers(String dateUpdated, String timeUpdated, String name,  String numberOfAntiOpioidAdministered, String hospitalized, String involvedRole, String involvedCategory) {
		String newVal;
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(3));
		String expectedNewVal = "Name:" + name;
		
		String incidentRowNewVal = "/following-sibling::td[text()='InvolvedOthers' and @data-label='FieldName']/following-sibling::td[@data-label='NewValue' and contains(text(), '" + expectedNewVal + "')]";
		String xpathExpression = "//td[text()='" + dateUpdated 
		        + "' and @data-label='Date Modified']/following-sibling::td[contains(text(), '" 
		        + timeUpdated + "') and @data-label='Time Modified']" + incidentRowNewVal;

		// Get the new value
		try {            
			newVal = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression))).getText();
			System.out.println(newVal);
		}catch(TimeoutException e) {
			throw new NoSuchElementException("Journal row with Date: " + dateUpdated + ", Time: " + timeUpdated + " and field name of InvolvedOthers with the newVal containing:'" + expectedNewVal + "' not found.");
		}
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("Number Of Anti Opioid Administered:", numberOfAntiOpioidAdministered);
		params.put("Hospitalized:", hospitalized);
		params.put("InvolvedRole:", involvedRole);
		params.put("InvolvedCategory:", involvedCategory);
		
//		Verify all the given info (the parameters) are present in the newVal string variable
		for  (Entry<String, String> entry : params.entrySet()) {
		    String titleName = entry.getKey();
		    String param = entry.getValue();
		    
		    if (param.length() != 0) {
				if (!newVal.contains(titleName + param)) {
					throw new NoSuchElementException(titleName + "\"" + param + "\" not found in Journal's InvolvedOthers row.");
				}
			}
		}
		
	}
	
}
