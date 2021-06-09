package confluence.pageobjects;

import org.openqa.selenium.WebDriver;

public class HomePage {
    protected WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver = driver;
        if (!driver.getTitle().equals("Atlassian | Start page")) {
            throw new IllegalStateException("This is not Home Page of logged in user," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public String getHomePageTitle() {
        return driver.getTitle();
    }
}
