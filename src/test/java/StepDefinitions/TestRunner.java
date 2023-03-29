package StepDefinitions;

import org.junit.runner.RunWith;

import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.CourgetteTestOutput;
import courgette.api.CucumberOptions;
import courgette.api.junit.Courgette;

@RunWith(Courgette.class)
@CourgetteOptions(
        threads = 10,
        runLevel = CourgetteRunLevel.SCENARIO,
        // rerunFailedScenarios = true,
        testOutput = CourgetteTestOutput.CONSOLE,
        reportTargetDir = "target",
        cucumberOptions = @CucumberOptions(
                features = "src/test/resources/Features/login.feature",
                glue = { "StepDefinitions" },
                // tags = {"@regression", "not @excluded"},
                publish = true
                // plugin = {
                //         "pretty",
                //         // "json:target/cucumber-report/cucumber.json",
                //         // "html:target/cucumber-report/cucumber.html",
                //         "junit:target/cucumber-report/cucumber.xml"
                // }
        ))
public class TestRunner {

}
