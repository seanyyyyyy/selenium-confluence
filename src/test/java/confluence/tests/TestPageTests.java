package confluence.tests;

import confluence.pageobjects.TestPage;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumExtension.class)
@DisplayName("Confluence Page Tests")
class TestPageTests {

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
    @DisplayName("Check page loads correct URL")
    void checkPage() {
        //TODO check driver values
        String expectedUrl="https://bonigarcia.github.io/selenium-jupiter/";
        String actualUrl;
        actualUrl = driver.getCurrentUrl();
        assertAll(
            () -> assertEquals(expectedUrl, actualUrl),
            () -> assertEquals("Selenium-Jupiter: JUnit 5 extension for Selenium", driver.getTitle())
        );
    }

    @DisplayName("Open and close Restrictions modal")
    void openAndCloseRestrictions() {
        testPage.openModal();
        //assertThat modal is active
        testPage.closeModal();
        //assetThat modal is inactive
    }

}
