package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import demo.wrappers.Wrappers;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrapper;
    WebDriverWait wait;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */
    @Test
    public void testCase01() throws InterruptedException {

        System.out.println("Start Test case: testCase01");

        wrapper = new Wrappers(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        String url = "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";
        wrapper.navigateToUrl(url);

        WebElement nameField = driver.findElement(By.xpath("//div[@class='o3Dpx']/div[1]//input"));
        wrapper.sendText(nameField, "Crio Learner");

        WebElement textArea = driver.findElement(By.xpath("//div[@class='o3Dpx']/div[2]//textarea"));
        wrapper.sendText(textArea, "I want to be the best QA Engineer! 1710572021");

        List<WebElement> radioButtons = driver.findElements(By.xpath("//div[@class='SG0AAe']/div//span"));
        for (WebElement element : radioButtons) {
            String text = element.getText();
            if (text.contains("3 - 5")) {
                wrapper.clickOnElement(element);
                break;
            }
        }

        System.out.println("Select checkboxes");
        List<WebElement> checkBoxes = driver.findElements(By.xpath("//div[@class='eBFwI']//div[@class='ulDsOb']"));
        for (WebElement el : checkBoxes) {
            String text = el.getText();
            if (!text.contains("Springboot")) {
                wrapper.clickOnElement(el);
            }
        }

        WebElement dropdown = driver.findElement(By.className("ry3kXd"));
        wrapper.clickOnElement(dropdown);
        Thread.sleep(1000);

        System.out.println("Selecting dropdown option");
        WebElement option = driver.findElement(By.xpath("//div[contains(@class,'ncFHed ')]/div[3]"));
        wait.until(ExpectedConditions.elementToBeClickable(option));
        wrapper.clickOnElement(option);

        System.out.println("Fill out the date field");
        LocalDate localDate = LocalDate.now().minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String pastDateString = localDate.format(formatter);
        Thread.sleep(1000);

        WebElement dateField = driver.findElement(By.xpath("//input[@type='date']"));
        wrapper.clickOnElement(dateField);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", dateField, pastDateString);

        wrapper.sendText(dateField, pastDateString);

        WebElement hour = driver.findElement(By.xpath("//input[@aria-label='Hour']"));
        WebElement minute = driver.findElement(By.xpath("//input[@aria-label='Minute']"));
        WebElement submitButton = driver.findElement(By.xpath("(//span[@class = 'l4V7wb Fxmcue'])[1]"));

        wrapper.sendText(hour, "07");
        wrapper.sendText(minute, "30");
        wrapper.clickOnElement(submitButton);

        WebElement responseText = driver.findElement(By.xpath("//div[@class='vHW8K']"));
        String expectedTExt = "Thanks for your response, Automation Wizard!";
        Assert.assertEquals(responseText.getText(), expectedTExt);

        System.out.println("End Test case: testCase01");
    }

    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}