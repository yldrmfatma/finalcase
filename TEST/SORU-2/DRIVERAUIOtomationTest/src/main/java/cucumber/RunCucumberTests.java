package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = "steps",
        //tags = "@appium or @smoke or @e2e or @happypath"
        tags = "@trueLogin or @invalidEmail or @invalidPassword or @forgotPassword\n" +
                "@loginViaFacebook or @logoutSuccess or @session-timeout or @signUpPositiveTest or @passwordVisibilityTest\n" +
                "@passwordMinLengthTest or @passwordUppercaseTest or @passwordLowercaseTest or @passwordNumberTest\n" +
                "@passwordSpecialCharTest or @passwordMismatchTest or @invalidDateOfBirthTest or @invalidEmailFormatTest\n"+
                " @noCitizenshipPolicyTest or @turkishCitizenshipPolicyTest or @europeanCitizenshipPolicyTest"

)

public class RunCucumberTests {
}
