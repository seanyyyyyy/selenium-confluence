package confluence.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class TestPage {

    private final WebDriver driver;
    public TestPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(css = "button[data-test-id='restrictions.dialog.button']")
    private WebElement restrictionsButton;

    @FindBy(className = "css-k5brqb")
    private WebElement restrictionsModalCancelButton;

    public void navigateTo() {
        //driver.get("https://szliaw.atlassian.net/wiki/spaces/HOME/pages/262146/Test+Page");
        driver.get("https://bonigarcia.github.io/selenium-jupiter/");
    }

    public void openModal() {
        restrictionsButton.click();
    }

    public void closeModal() {
        restrictionsModalCancelButton.click();
    }
}
