package StepDefinitions;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/Features/login.feature", glue = { "StepDefinitions" }, plugin = {
		"pretty", "html:target/HtmlReport.html",
		"json:target/JsonReport.json",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"junit:target/JunitReport.xml"
}, publish = true)
public class TestRunner {

}
