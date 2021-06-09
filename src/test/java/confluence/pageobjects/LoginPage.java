package confluence.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,10);
    }

    private final By usernameBy = By.id("username");
    private final By passwordBy = By.id("password");
    private final By loginButtonBy = By.id("login-submit");

    public void navigateTo() {
        driver.get("https://id.atlassian.com/login");
    }

    public HomePage userLogin(String username, String password) {
        driver.findElement(usernameBy).sendKeys(username);
        driver.findElement(loginButtonBy).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordBy));
        driver.findElement(passwordBy).sendKeys(password);
        driver.findElement(loginButtonBy).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-testid='switcher-btn']")));
        return new HomePage(driver);
    }

}
