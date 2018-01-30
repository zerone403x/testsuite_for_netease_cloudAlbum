package cloudAlbumTest;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;


public class verifyDeleteAlbum {

	public static void main(String[] args) {
		//Pre-condition
		String account_input,password_input;
		Scanner in = new Scanner(System.in);
		System.out.println("Please input your account");
		account_input = in.nextLine();
		System.out.println("Please input your password");
		password_input = in.nextLine();
		in.close();

		//Step1
		WebDriver driver = new FirefoxDriver(); 
		driver.get("http://photo.163.com/");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading

		driver.switchTo().frame(driver.findElement(By.xpath("//*[contains(@src,'http://blog.163.com/newpage/ursweb/tmpl2/loginurs.html')]"))); //first level iframe
		driver.switchTo().frame(driver.findElement(By.xpath("//*[contains(@id,'x-URS-iframe')]"))); //second level iframe
		
		driver.findElement(By.xpath("//input[@name=\"email\"]")).sendKeys(account_input);
		driver.findElement(By.name("password")).sendKeys(password_input);
		driver.findElement(By.id("dologin")).click();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading

		driver.switchTo().defaultContent(); //exit second level iframe
		driver.switchTo().defaultContent(); //exit first level iframe
		
		driver.findElement(By.linkText("进入我的相册")).click();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //wait for page loading
		
		//Step2
		//create 3 albums firstly
		driver.findElement(By.linkText("创建相册")).click();				
		driver.findElement(By.xpath("//input[@name=\"fm-0\"]")).sendKeys("For Delete");
		driver.findElement(By.xpath("//button[@name=\"fm-a\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"photo-163-com-container\"]/div[2]/div[1]/div/div[1]/ul/li[8]/a")).click();
		
		//verify
		try {
			Thread.sleep(5000); //wait for 5 seconds for verifying
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		WebElement element = driver.findElement(By.xpath("//div[@class='ln ln0']"));
		Actions builder = new Actions(driver);
		Action hover = builder.moveToElement(element).build();
		hover.perform();
		
		driver.findElement(By.xpath("//a[@title='删除']")).click();		
		driver.findElement(By.xpath("//button[@class='ui-btn ui-btn-sub0']")).click();
		
		//verify
		try {
			Thread.sleep(5000); //wait for 5 seconds for verifying
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.quit();
	}

}
