package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wrappers {
    private WebDriver driver;
    private WebDriverWait wait;
    public Wrappers(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    /*
     * Write your selenium wrappers here
     */
    public void navigateToUrl(String url){
        if (url == null || url.isEmpty()){
            System.out.println("url is empty");
        }
        driver.get(url);
    }
    public void sendText(WebElement element, String text){

        element.click();
        element.sendKeys(text);
    }
    public void clickOnElement(WebElement element){

        element.click();
    }
    public WebElement waitForElementVisibility(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}
