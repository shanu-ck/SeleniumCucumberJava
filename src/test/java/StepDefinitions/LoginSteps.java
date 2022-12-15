package StepDefinitions;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginSteps {
	WebDriver driver = null;

	@Before
	public void before() {
		WebDriverManager.chromedriver().driverVersion("108.0.5359.124").setup();
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().window().maximize();
	}

	@After
	public void after() {
		driver.quit();
	}

	@Given("I am on the login page")
	public void i_am_on_the_login_page() {
		driver.navigate().to("https://shanudey.github.io/task-management-ui/#/login");
	}

	@When("^I enter email \"(.*)\" and password \"(.*)\"$")
	public void i_enter_email_and_password(String email, String password) {
		WebElement emailTextBox = driver.findElement(By.id("email"));
		emailTextBox.sendKeys(email);
		WebElement passwordTextBox = driver.findElement(By.id("password"));
		passwordTextBox.sendKeys(password);
	}

	@When("I click on login button")
	public void i_click_on_login_button() {
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		loginButton.click();
	}

	@Then("I verify dashbord page is opened")
	public void i_verify_dashbord_page_is_opened() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@type='submit']")));

		String currentUrl = driver.getCurrentUrl();
		if (!currentUrl.contains("dashboard")) {
			System.out.println("Current URL = " + currentUrl);
			throw new Exception("Dashboard page is not opened");
		}
	}

	@Then("I verify login failed with some error message")
	public void I_verify_login_failed_with_some_error_message() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		By errorMsgLocator = By.xpath("//form//p");
		wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsgLocator));
		String errorMsgText = driver.findElement(errorMsgLocator).getText();
		Assert.assertNotEquals("", errorMsgText);
	}

}
