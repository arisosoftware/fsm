package com.ariso.webtestauto;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Hashtable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FirstRemoteClient {

	public static void main(String[] args) throws Throwable {
		ChromeOptions chromeOptions = new ChromeOptions();
		//chromeOptions.setCapability("browserVersion", "95");
		//chromeOptions.setCapability("platformName", "Windows");
		// Showing a test name instead of the session id in the Grid UI
		chromeOptions.setCapability("se:name", "My simple test"); 
		
		// Other type of metadata can be seen in the Grid UI by clicking on the 
		// session info or via GraphQL
		//chromeOptions.setCapability("se:sampleMetadata", "Sample metadata value"); 
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.2.19:4444/"), chromeOptions);

//		((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
//		  
//		
//		driver.get("http://sso.dev.saucelabs.com/test/guinea-file-upload");
//		WebElement upload = driver.findElement(By.id("myfile"));
//		upload.sendKeys("/Users/sso/the/local/path/to/darkbulb.jpg");
//		  
		
		
		driver.get("https://stackoverflow.com/");
		System.out.println(driver.getTitle());
		driver.quit();

	}

}
