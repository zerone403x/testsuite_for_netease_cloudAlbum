package cloudAlbumTest;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class verifyComment {
	
	WebDriver driver;
	
	@Test
	public void cloudAlbumCommets() {
		
		driver.findElement(By.linkText("进入我的相册")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading
		
		//Step2
		//enter the first album                             
		driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[1]/div[2]/div/div[1]")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading

		//add comments, and click publish
		String comments_input = "This is my comments";
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[2]/div[8]/div/div[2]/div[3]/div[2]/div/div[1]/div[1]/iframe")));
		driver.findElement(By.xpath("/html/body")).sendKeys(comments_input);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[2]/div[8]/div/div[2]/div[3]/div[2]/div/div[1]/div[2]/input[1]")).click();
		
		//verify
		String comments = driver.findElement(By.xpath("//div[@class='cnt fc98 js-cnt pre c-tag']")).getText();
		Assert.assertEquals(comments, comments_input);
	}	
	
	@BeforeClass
	public void openWebsite() {
		driver = new FirefoxDriver(); 
		driver.get("http://photo.163.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading
	}
	
	@BeforeMethod
	public void userLogin() {
		//get username and password
		String account_input,password_input;
		Scanner in = new Scanner(System.in);
		System.out.println("Please input your account");
		account_input = in.nextLine();
		System.out.println("Please input your password");
		password_input = in.nextLine();
		in.close();
		
		//perform login
		driver.switchTo().frame(driver.findElement(By.xpath("//*[contains(@src,'http://blog.163.com/newpage/ursweb/tmpl2/loginurs.html')]"))); //first level iframe
		driver.switchTo().frame(driver.findElement(By.xpath("//*[contains(@id,'x-URS-iframe')]"))); //second level iframe
		
		driver.findElement(By.xpath("//input[@name=\"email\"]")).sendKeys(account_input);
		driver.findElement(By.name("password")).sendKeys(password_input);
		driver.findElement(By.id("dologin")).click();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading

		driver.switchTo().defaultContent(); //exit second level iframe
		driver.switchTo().defaultContent(); //exit first level iframe
	}
	
	//need another BeforeMethod function to create an album and upload a picture in it, 
	//but cloudAblum use flash to perform uploading, I don't know how to use Selenium to upload picture,
	//so it is need to prepare a album with at least one picture firstly, before the @Test function "cloudAlbumCommets" execution.
	
	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}
}
