package confluence.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(id="username") private WebElement usernameInput;
    @FindBy(id="login-submit") private WebElement loginButton;
    @FindBy(id="password") private WebElement passwordInput;

    public void navigateTo() {
        driver.get("https://id.atlassian.com/login");
    }

    public void userLogin(String username, String password) {
        usernameInput.sendKeys(username);
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passwordInput.sendKeys(password);
        loginButton.click();
    }

}
