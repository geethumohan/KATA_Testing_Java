import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import static utils.DriversUtils.tearDown;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:cucumber.json"},
        features = "src/test/java/features",
        tags = "@book",
        glue = {"pages","features/step_definitions"})

public class RunnerTest {
    @AfterClass
    public static void afterScenario(){
        tearDown();
    }
}
