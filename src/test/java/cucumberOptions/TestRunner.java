package cucumberOptions;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features"},
     tags = "@TC_0015",
		glue = "stepDefinations",
		dryRun = false,
		monochrome = true,
		stepNotifications = true,
		plugin = {"pretty",
				"json:target/cucumber.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		}
)

public class TestRunner {

}
