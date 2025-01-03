package ca.IRM.selenium.config;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private final int maxRetryCount = 3; // Maximum retry count (you can set it to your desired value)

    public boolean retry(ITestResult result) {
        if (count < maxRetryCount) {
            count++;
            return true;  // Retry the test
        }
        return false;  // Do not retry after maxRetryCount attempts
    }
}
