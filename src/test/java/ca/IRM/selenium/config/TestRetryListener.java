package ca.IRM.selenium.config;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestRetryListener implements IAnnotationTransformer{
	
	@Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // Attach the RetryAnalyzer to all tests in the suite
        annotation.setRetryAnalyzer(TestRetryAnalyzer.class);
    }
}