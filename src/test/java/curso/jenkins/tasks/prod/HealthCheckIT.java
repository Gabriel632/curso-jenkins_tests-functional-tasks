package curso.jenkins.tasks.prod;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HealthCheckIT {

	public WebDriver acessarAplicacao() {		
		try {
			/*
			new DesiredCapabilities();
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.23:4444/wd/hub"), cap);			
			*/
			
			System.setProperty("webdriver.chrome.driver","C:\\Users\\gabri\\OneDrive\\Área de Trabalho\\Programas\\chromedriver-win64\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
						
			driver.navigate().to("http://192.168.0.23:9999/tasks/");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			return driver;			
		} catch (Exception e) {
			return null;
		}		
	}
	
	@Test
	public void healthCheck() {
		WebDriver driver = acessarAplicacao();
		try {			
			String version = driver.findElement(By.id("version")).getText();
			Assert.assertTrue(version.startsWith("build"));			
		} finally {
			driver.quit();	
		}		
	}
}
