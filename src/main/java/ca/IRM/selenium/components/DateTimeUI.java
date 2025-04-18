package ca.IRM.selenium.components;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DateTimeUI {
	
	WebDriver driver;
	WebDriverWait wait;

	By calendarButton = By.xpath("//*[@aria-label='Open Date Picker']");
	By timeButton = By.xpath("//*[@aria-label='Open Time Picker']");
	By innerHourHand = By.xpath("//div[contains(@class, 'mud-picker-stick-inner mud-hour')]");
	By minuteHand = By.xpath("//div[contains(@class, 'mud-picker-stick mud-minute')]");
	
	String innerHourHandCustom = "(//div[contains(@class, 'mud-picker-stick-inner mud-hour')])"; //range => 1-12
	String minuteHandCustom = "(//div[contains(@class, 'mud-picker-stick mud-minute')])"; // range => 0-60
	
	
	//Constructor
	public DateTimeUI(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public static String getCurrentTime(String format) {
	    GregorianCalendar c = new GregorianCalendar();
	    LocalTime now = LocalTime.of(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
	    DateTimeFormatter formatter;
	    
	    if (format.equals("full")) {
	    	formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	    } else {
	    	formatter = DateTimeFormatter.ofPattern("hh:mm");
	    }
	    return now.format(formatter);
	}
	
	
	public static String getCurrentDate(String format) {
	    GregorianCalendar c = new GregorianCalendar();
	    LocalDate today = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
	    DateTimeFormatter formatter;
	    
	    if (format.equals("full")) {
	    	formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");	    	
	    }
	    else if(format.equals("calendar")) {
	    	formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");	  
	    }
	    else {	    	
	    	formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    }
	    return today.format(formatter);
	}
	
	
	public void selectCurrentDate() {
		Actions actions = new Actions(driver);
		String current = getCurrentDate("full");
		
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(calendarButton))).perform();
		wait.until(ExpectedConditions.elementToBeClickable(calendarButton)).click();
		
		actions.moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-label='" + current + "']")))).perform();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@aria-label='" + current + "']"))).click();
	}

	/**
	  * Select Time from Clock UI
	  *
	  * @param hour (currently only from 1-12)
	  * @param minute (0-60)
	  */
	public void selectTimeUI(int hour, int minute) {
		Actions actions = new Actions(driver);
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(timeButton))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(innerHourHandCustom + "[" + hour + "]")))).click().perform();
		actions.moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath(minuteHandCustom + "[" + (minute + 1) +  "]")))).click().perform();
		
	}
	
}
