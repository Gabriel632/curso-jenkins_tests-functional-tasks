package curso.jenkins.tasks.functional;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

	public WebDriver acessarAplicacao() {		
		try {
			/*
			new DesiredCapabilities();
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.23:4444/wd/hub"), cap);			
			*/
			
			System.setProperty("webdriver.chrome.driver","C:\\Users\\gabri\\OneDrive\\Área de Trabalho\\Programas\\chromedriver-win64\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
						
			driver.navigate().to("http://192.168.0.23:8001/tasks/");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			return driver;			
		} catch (Exception e) {
			return null;
		}		
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() {
		WebDriver driver = acessarAplicacao();
		
		try {
			// clicar em add
			driver.findElement(By.id("addTodo")).click();		
			
			// escrever descrição
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");		
			
			// escrever data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");		
			
			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();		
			
			// validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		} 
		finally {
			// fechar browser
			driver.quit();
		}		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		WebDriver driver = acessarAplicacao();
		
		try {
			// clicar em add
			driver.findElement(By.id("addTodo")).click();	
			
			// escrever data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");		
			
			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();		
			
			// validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
		} finally {
			// fechar browser
			driver.quit();
		}		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		WebDriver driver = acessarAplicacao();
		
		try {
			// clicar em add
			driver.findElement(By.id("addTodo")).click();		
			
			// escrever descrição
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");						
			
			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();		
			
			// validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
		} finally {
			// fechar browser
			driver.quit();
		}		
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		WebDriver driver = acessarAplicacao();
		
		try {
			// clicar em add
			driver.findElement(By.id("addTodo")).click();		
			
			// escrever descrição
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");		
			
			// escrever data
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");		
			
			// clicar em salvar
			driver.findElement(By.id("saveButton")).click();		
			
			// validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		} finally {
			// fechar browser
			driver.quit();
		}		
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() {
		WebDriver driver = acessarAplicacao();
		
		try {
			// inserir tarefa
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2040");		
			driver.findElement(By.id("saveButton")).click();
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
			
			// remover tarefa
			driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();
			message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		} finally {
			// fechar browser
			driver.quit();
		}		
	}
}
