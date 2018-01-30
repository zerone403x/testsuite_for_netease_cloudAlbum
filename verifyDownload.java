package cloudAlbumTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class verifyDownload {
	WebDriver driver;
	
	@Test
	public void cloudAlbumDownload() {		

		driver.findElement(By.linkText("了解更多")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading

		for (String winHandle:driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		
		driver.findElement(By.xpath("//a[@id=\"J-iphone\"]")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading

		//verify
		String iphone = driver.findElement(By.xpath("//a[@id='J-down-ios']")).getText();
		System.out.println(iphone);
		Assert.assertEquals(iphone, "app store");
		
	}
	
	@BeforeClass
	public void openWebsite() {
		driver = new FirefoxDriver(); 
		driver.get("http://photo.163.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading
	}
	
	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}

}
