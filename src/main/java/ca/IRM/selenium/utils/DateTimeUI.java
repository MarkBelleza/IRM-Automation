package ca.IRM.selenium.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
	
	
	public static String getCurrentDate() {
	    GregorianCalendar c = new GregorianCalendar();
	    LocalDate today = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
	    return today.format(formatter);
	}
	
	
	public void selectCurrentDate() {
		wait.until(ExpectedConditions.elementToBeClickable(calendarButton)).click();
		String current = getCurrentDate();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@aria-label='" + current + "']"))).click();
	}

	/**
	  * Select Time from Clock UI
	  *
	  * @param hour (currently only from 1-12)
	  * @param minute (0-60)
	  */
	public void selectTimeUI(int hour, int minute) {
		wait.until(ExpectedConditions.elementToBeClickable(timeButton)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(innerHourHandCustom + "[" + hour + "]"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(minuteHandCustom + "[" + (minute + 1) +  "]"))).click();
		
	}
	
}
