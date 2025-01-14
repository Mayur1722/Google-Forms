package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class Wrappers {
    private final WebDriver driver;

    public Wrappers(WebDriver driver) {
        this.driver = driver;
    }
    /*
     * Write your selenium wrappers here
     */
    public void navigateToUrl(String url) {
        try {
            if (url == null || url.isEmpty()) {
                System.out.println("url is empty");
            } else {
                driver.get(url);
                System.out.println("Url navigation succeeded :" + url);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid url :" + e.getMessage());
        }
    }

    public void sendText(WebElement element, String text) {
        try {
            element.sendKeys(text);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    public void clickOnElement(WebElement element) {

        try {
            element.click();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
