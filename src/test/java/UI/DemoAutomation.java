package UI;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DemoAutomation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// No longer required in this selenium version
//		System.setProperty("webdriver.edge.driver", "C:\\Users\\BELLEZMA\\Documents\\.selenium stuff\\Drivers\\edgedriver_win64\\msedgedriver.exe");
//		ChromeDriver driver = new ChromeDriver();
		
		EdgeDriver driver = new EdgeDriver();
		driver.get("http://jtsazistcirm01/");
		driver.manage().window().maximize();
//		driver.findElement(".mud-appbar mud-appbar-fixed-top mud-elevation-0");
//		driver.close();
		
	}

}
