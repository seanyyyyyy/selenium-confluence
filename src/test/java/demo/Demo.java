package demo;

import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

@ExtendWith(SeleniumExtension.class)
public class Demo {
    @Test
    void projectIsConfigured(ChromeDriver driver) {}
}
