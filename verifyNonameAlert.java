package cloudAlbumTest;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class verifyNonameAlert {
	
	WebDriver driver;

	@Test
	public void cloudAlbumNoNameAlert() {

		driver.findElement(By.linkText("进入我的相册")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading
		
		//create a album, but no name
		driver.findElement(By.linkText("创建相册")).click();
		driver.findElement(By.xpath("//button[@name=\"fm-a\"]")).click();//click create album
		
		//verify
		Alert alert = driver.switchTo().alert();
		String waring =alert.getText();
		System.out.println("----"+waring+"-----");
		Assert.assertEquals(waring, "请输入相册名称！");
		
		//click confirm
		alert.accept();
		

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
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); //wait for page loading

		driver.switchTo().defaultContent(); //exit second level iframe
		driver.switchTo().defaultContent(); //exit first level iframe
	}
	
	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}

}
