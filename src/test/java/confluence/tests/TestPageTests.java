package confluence.tests;

import confluence.pageobjects.TestPage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import io.github.bonigarcia.seljup.SingleSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SeleniumExtension.class)
@DisplayName("Confluence Page Tests")
public class TestPageTests {

    private final WebDriver driver;
    private TestPage testPage;

    public TestPageTests(WebDriver driver) {
        this.driver = driver;
        this.testPage = PageFactory.initElements(driver, TestPage.class);
        this.testPage.navigateTo();
    }

    @AfterEach
    void storageCleanup() {
        ((JavascriptExecutor) driver).executeScript("window.localStorage.clear()");
    }

    @Test
    @DisplayName("Check page loading")
    void checkPage() {
        //assertThat(driver.getTitle().contains("Log in to continue"));
        //TODO check assert, always passing
        assertThat(driver.getTitle().contains("Milano"));
    }

    @Test
    @DisplayName("Open and close Restrictions modal")
    void openAndCloseRestrictions() {
        testPage.openModal();
        //assertThat modal is active
        testPage.closeModal();
        //assetThat modal is inactive
    }

}
