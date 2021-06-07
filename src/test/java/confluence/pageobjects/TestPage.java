package confluence.pageobjects;

import confluence.TestFunctionality;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class TestPage implements TestFunctionality {

    private final WebDriver driver;
    public TestPage(WebDriver driver) {
        this.driver = driver;
    }

    private static final By openRestrictionsButton = By.cssSelector("button.css-grwtf6");

    @FindBy(css = "button[data-test-id='restrictions.dialog.button']")
    private WebElement restrictionsButton;

    @FindBy(className = "css-k5brqb")
    private WebElement restrictionsModalCancelButton;


    @Override
    public void navigateTo() {
        driver.get("https://szliaw.atlassian.net/wiki/spaces/HOME/pages/262146/Test+Page");
    }

    public void openModal() {
        restrictionsButton.click();
    }

    public void closeModal() {
        restrictionsModalCancelButton.click();
    }
}
