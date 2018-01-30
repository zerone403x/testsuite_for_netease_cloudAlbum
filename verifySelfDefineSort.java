package cloudAlbumTest;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class verifySelfDefineSort {

	WebDriver driver;
	
	@Test
	public void cloudAlbumSelfDefineSort() {
		
		driver.findElement(By.linkText("排序")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading
		driver.findElement(By.linkText("自定义排序")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading
		
		WebElement source = driver.findElement(By.xpath("//div[@title='CCC']"));
		WebElement target = driver.findElement(By.xpath("//div[@title='AAA']"));
		//though making CCC and AAA exchange position, actually CCC exchange position with "BBB"  
		Actions act = new Actions(driver);
		act.dragAndDrop(source, target).perform();
		driver.findElement(By.linkText("保存")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading
		
		//verify
		//System.out.println(driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[1]/div[2]/div[1]//div[@class='ln ln0']/a/img")).getAttribute("title").toString());
		Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[1]/div[2]/div[1]//div[@class='ln ln0']/a/img")).getAttribute("title").toString(), "BBB");
		//System.out.println(driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[1]/div[2]/div[2]//div[@class='ln ln0']/a/img")).getAttribute("title").toString());
		Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[1]/div[2]/div[2]//div[@class='ln ln0']/a/img")).getAttribute("title").toString(), "CCC");

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
	
	@BeforeMethod(dependsOnMethods={"userLogin"})
	public void create3Album () {
		driver.findElement(By.linkText("进入我的相册")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading
		//create 3 albums firstly
		driver.findElement(By.linkText("创建相册")).click();				
		driver.findElement(By.xpath("//input[@name=\"fm-0\"]")).sendKeys("AAA");
		driver.findElement(By.xpath("//button[@name=\"fm-a\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[2]/div[1]/div/div[1]/ul/li[8]/a")).click();
		
		driver.findElement(By.linkText("创建相册")).click();				
		driver.findElement(By.xpath("//input[@name=\"fm-0\"]")).sendKeys("BBB");
		driver.findElement(By.xpath("//button[@name=\"fm-a\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[2]/div[1]/div/div[1]/ul/li[8]/a")).click();
		
		driver.findElement(By.linkText("创建相册")).click();				
		driver.findElement(By.xpath("//input[@name=\"fm-0\"]")).sendKeys("CCC");
		driver.findElement(By.xpath("//button[@name=\"fm-a\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[2]/div[1]/div/div[1]/ul/li[8]/a")).click();
		
	}
	
	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}

}
