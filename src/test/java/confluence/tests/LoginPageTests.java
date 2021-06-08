package confluence.tests;

import confluence.pageobjects.LoginPage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumExtension.class)
@DisplayName("Login Page Tests")
public class LoginPageTests {

    private final WebDriver driver;
    private LoginPage loginPage;

    public LoginPageTests(WebDriver driver) {
        this.driver = driver;
        this.loginPage = PageFactory.initElements(driver, LoginPage.class);
        this.loginPage.navigateTo();
    }

    @AfterEach
    void storageCleanup() {
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear()");
    }

    @Test
    @DisplayName("Login with valid user")
    public void validUserLogin() {
        loginPage.userLogin("szliaw@gmail.com","Testing123");
        //await new page load
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-testid='switcher-btn']")));
        assertAll(
            () -> assertEquals("https://start.atlassian.com/", driver.getCurrentUrl()),
            () -> assertEquals("Atlassian | Start page", driver.getTitle())
        );
    }

    @Test
    @DisplayName("Login with valid user and incorrect password")
    public void validUserInvalidPassword() {
        //TODO
    }
}
