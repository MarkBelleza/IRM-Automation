package UI;
import java.util.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
 
 
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;
 
//*****************THIS FILE IS ALL FOR TESTING THE FUNCTIONALITY OF MY CODE AND NOT RELATED TO MAIN CODE **************
 
public class DemoReference {
  /*
  public static String getMonth(int month){
    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    return monthNames[month];
  } */
  
  public static int getMonth(String month){
    List<String> monthNames = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    //String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    return monthNames.indexOf(month) + 1;
  }
  
  public static String getCurrentDate() {
    GregorianCalendar c = new GregorianCalendar();
    LocalDate today = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL d, yyyy");
    return today.format(formatter);
  }
  
  // this only works for getting dates up to one week in advance
  public static List<String> generateDates(){
    //Gets current date
    GregorianCalendar c = new GregorianCalendar();
    LocalDate today = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
    
 
    //List containing all the dates
    List<String> list = new ArrayList<String>();
    //Used to format date into "Month Day, Year"
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL d, yyyy");
 
    int count = 0;
    
    for (int i = 0; i <= 9; i++ ) {
      //Adds the increment i as days to the current date to get the incoming dates
      LocalDate newDate = today.plusDays(i + count);
      LocalDate saturday = newDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
      LocalDate sunday = newDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));      
      
      //If a Saturday has occurred, the remaining dates in the list will be shifted by 2 days to Monday
      if (newDate.equals(saturday)) {
        count = count + 2;
      }
      
      //If a Saturday has occurred, the remaining dates in the list will be shifted by 1 days to Monday
      if (newDate.equals(sunday)) {
        count++;
      }
 
      //adds newly generated date to list
      list.add(today.plusDays(i + count).format(formatter));
 
    }
    
    return list;
  }
 
  // To select date, provide the WebDriver, WebDriverWait, and specified date to be chosen
  public static void SelectDate(WebDriver driver, WebDriverWait wait, String date, String curDate) {   
    //Splits provided date into month, year, and day
    String[] splitTargetDate = date.split("\\s+");
    String month = splitTargetDate[0];
    String year = splitTargetDate[2];
    
    String[] splitCurDate = curDate.split("\\s+");
    String curMonth = splitCurDate[0];
    String curYear = splitCurDate[2];
    
    
    //Need this if branch to work around choosing dates with same month and year
    //For some reason using EC.clickable or findElements do not work for dates with same month and year
    if (curYear.equals(year) && curMonth.equals(month)) {
    //This works for selecting a date within same month and same year
      List<WebElement> calendar = driver.findElements(By.cssSelector("span[aria-label='"+ date +"']"));
      for (WebElement i : calendar) {
        if (i.getCssValue("visibility").equals("visible")) {
          i.click();
          break;
        }
      }
    }
    else {  
      //Finds all the calendar on page and puts it into the list
      List<WebElement> calendar = driver.findElements(By.cssSelector("div[class='flatpickr-month']"));
      WebElement targetCal = null;
      
      //Find the opened/visible calendar
      for (WebElement i : calendar) {
        if (i.getCssValue("visibility").equals("visible")) {
          targetCal = i;
          break;
        }
      }
      
      //Selects the given month
      Select monthMenu = new Select(targetCal.findElement(By.cssSelector("select[class='flatpickr-monthDropdown-months']")));
      monthMenu.selectByVisibleText(month);
      
      //Change year to specified year
      WebElement x = targetCal.findElement(By.cssSelector("input[aria-label='Year']"));
      x.click();
      x.sendKeys(year);
      x.sendKeys(Keys.ENTER);
  
  
      //Clicks on the target date
      WebElement y = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[aria-label='"+ date +"']")));
      y.click();
    }
    
    System.out.println("Finish date selection");
  }
  
  //Still need to add this
  //Function for Orders Related to Information and Language Rights
  // Parameters: WebDriver, WebDriver, userID, Date, Order Type, Pursuant, Status, Other
  public static void updateOrders(WebDriver driver, WebDriverWait wait, String elemID, String date, String type, String pursuant, String status, String other, String curDate) {
    // Select Date
    wait.until(ExpectedConditions.elementToBeClickable(By.id ("order-date-select-"+ elemID))).click();
    SelectDate(driver, wait, date, curDate);
    
    // Select Type
    Select orderType = new Select(driver.findElement(By.id("publication-ban-type-"+ elemID)));
    orderType.selectByVisibleText(type);
    
    // Pursuant To
    wait.until(ExpectedConditions.elementToBeClickable(By.id ("text-publication-ban-pursuant-to-"+ elemID))).sendKeys(pursuant);
    
    // Select Status
    Select statusMenu = new Select(driver.findElement(By.id("publication-ban-status-"+ elemID)));
    statusMenu.selectByVisibleText(status);
    
    // Other
    wait.until(ExpectedConditions.elementToBeClickable(By.id ("publication-ban-other-"+ elemID))).sendKeys(other);
 
    // Click Update Orders
    wait.until(ExpectedConditions.elementToBeClickable(By.id ("update-publication-ban-btn-"+ elemID))).click();
 
    System.out.println("Updated Orders");
  }
  
  public static void main(String[] args) throws InterruptedException, ParseException  {
    WebDriverManager.edgedriver().setup();
    WebDriver driver = new EdgeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    //JavascriptExecutor executor = (JavascriptExecutor) driver;
    
    // Makes ID number into a variable and replaced spaces with underscores then add to existing strings
    // to match with element's ID format
    //String userID = "3811 998 24 38100366-02";
    String userID = "2811 998 24 28100086-08";
    String elemID = userID.replaceAll(" ", "_");
    
    driver.manage().window().maximize();
    
    driver.navigate().to("https://intra.test.courtshub.jus.gov.on.ca/Dashboard");  
    wait.until(ExpectedConditions.elementToBeClickable(By.id ("SearchString"))).sendKeys(userID);
    wait.until(ExpectedConditions.elementToBeClickable(By.id ("SearchString"))).sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div[5]/ul/li[1]/div/table/tbody/tr/td[4]/a"))).click();
    wait.until(ExpectedConditions.elementToBeClickable(By.id ("adjournment-date-edit-"+ elemID))).click();
    
    
    //Need to follow this specific date format
    //SelectDate(driver, wait, "September 25, 2024", currentDate);
    //SelectDate(driver, wait, "January 6, 2025");
    
    // Parameters: WebDriver, WebDriver, userID, Date, Order Type, Pursuant, Status, Other, currentDate
    //updateOrders(driver, wait, elemID, "September 3, 2024", "Non-Communication Order", "Dude", "Vacated", "Bob", currentDate);
    
    Date date = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse("MaRcH");
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    
    System.out.println(getMonth("January"));
    System.out.println(generateDates());
    
    Date currentDate = new Date();
    System.out.println("==========================================" + currentDate);
    
  }
 
}