package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = "steps",
       // tags = "@appium or @smoke or @e2e or @happypath"
        tags=   "@validRegistration or @hashPasswordControl or @uniqueFieldTest_1\n"+
                "@uniqueFieldTest_2 or @uniqueFieldTest_3 or @foreignKeyTest \n"+
                "@deleteProcess or @foreignKeyTest or @OTPVerify\n"+
                "@birthDateFormatValidity or @citizenshipPrivacyPolicy or @socialLogin"

)

public class RunCucumberTests {
}

