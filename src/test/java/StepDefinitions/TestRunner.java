package StepDefinitions;

import org.junit.runner.RunWith;

import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.CourgetteTestOutput;
import courgette.api.CucumberOptions;
import courgette.api.junit.Courgette;

@RunWith(Courgette.class)
@CourgetteOptions(
threads = 1, 
runLevel = CourgetteRunLevel.SCENARIO, 
testOutput = CourgetteTestOutput.CONSOLE, 
reportTargetDir = "target", 
cucumberOptions = @CucumberOptions(
	features = "src/test/resources/Features/login.feature", 
	glue = { "StepDefinitions" }, 
	plugin = {
		"pretty", "html:target/HtmlReport.html",
		"json:target/JsonReport.json",
		"junit:target/JunitReport.xml"
	}, 
	publish = true)
)
public class TestRunner {

}
