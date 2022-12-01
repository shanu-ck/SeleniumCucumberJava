package StepDefinitions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {
	boolean enableSauceLabs = "true".equals(System.getenv("SAUCE_LABS_STATUS"));
	WebDriver driver = null;

	@Before
	public void before() {
		String saucelabsUsername = System.getenv("SAUCELABS_USERNAME");
		String saucelabsAccessKey = System.getenv("SAUCELABS_ACCESS_KEY");
		if (enableSauceLabs) {
			// Saucelabs
			ChromeOptions browserOptions = new ChromeOptions();
			browserOptions.setPlatformName("Windows 10");
			browserOptions.setBrowserVersion("latest");
			Map<String, Object> sauceOptions = new HashMap<>();
			sauceOptions.put("build", "selenium-build-IHV8F");
			sauceOptions.put("name", "Login Tests using SeleniumCucumberJava suite");
			browserOptions.setCapability("sauce:options", sauceOptions);
			String saucelabsConnectionString = "https://"
					+ saucelabsUsername
					+ ":"
					+ saucelabsAccessKey
					+ "@ondemand.apac-southeast-1.saucelabs.com:443/wd/hub";
			try {
				URL url = new URL(saucelabsConnectionString);
				driver = new RemoteWebDriver(url, browserOptions);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			ChromeOptions options = new ChromeOptions();
			String ciStatus = System.getenv("CI");
			if ("true".equals(ciStatus)) {
				System.setProperty("webdriver.chrome.driver",
						System.getenv("CHROMEWEBDRIVER") + "/chromedriver");
				// options.addArguments("--headless");
			} else {
				String driverPath = System.getProperty("user.dir");
				System.setProperty("webdriver.chrome.driver", driverPath +
						"/src/test/resources/drivers/chromedriver");
			}
			driver = new ChromeDriver(options);
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().window().maximize();
	}

	@After
	public void after(Scenario scenario) {
		if (!scenario.isFailed()) {
			if (enableSauceLabs)
				((RemoteWebDriver) driver).executeScript("sauce:job-result=passed");
		} else {
			if (enableSauceLabs)
				((RemoteWebDriver) driver).executeScript("sauce:job-result=failed");
		}
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
}
