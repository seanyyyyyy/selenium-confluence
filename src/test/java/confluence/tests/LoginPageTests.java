package confluence.tests;

import confluence.pageobjects.HomePage;
import confluence.pageobjects.LoginPage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumExtension.class)
@DisplayName("Login Page Tests")
public class LoginPageTests {

    private final LoginPage loginPage;

    public LoginPageTests(WebDriver driver) {
        this.loginPage = PageFactory.initElements(driver, LoginPage.class);
        this.loginPage.navigateTo();
    }

    @Test
    @DisplayName("Login with valid user")
    public void validUserLogin() {
        HomePage homePage = loginPage.userLogin("szliaw@gmail.com","Testing123");
        assertEquals("Atlassian | Start page", homePage.getHomePageTitle());
    }
}
