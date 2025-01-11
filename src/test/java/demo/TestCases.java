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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import demo.wrappers.Wrappers;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

        System.out.println("Start TestCase 01");
        wrapper = new Wrappers(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String url = "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";
        wrapper.navigateToUrl(url);

        WebElement nameField = driver.findElement(By.xpath("(//input[@class='whsOnd zHQkBf'])[1]"));
        wrapper.sendText(nameField, "Crio Learner");

        WebElement textArea = driver.findElement(By.xpath("//textarea[@aria-label='Your answer']"));
        wrapper.sendText(textArea, "I want to be the best QA Engineer! 1710572021");

        WebElement experience = driver.findElement(By.xpath("(//div[@class='vd3tt'])[2]"));
        wrapper.clickOnElement(experience);

        System.out.println("Select checkboxes");
        WebElement javaCheckBox = driver.findElement(By.xpath("(//div[@class = 'uHMk6b fsHoPb'])[1]"));
        if (!javaCheckBox.isSelected()) {
            wrapper.clickOnElement(javaCheckBox);
        }

        WebElement seleniumCheckBox = driver.findElement(By.xpath("(//div[@class = 'uHMk6b fsHoPb'])[2]"));
        if (!seleniumCheckBox.isSelected()) {
            wrapper.clickOnElement(seleniumCheckBox);
        }

        WebElement testNgCheckBox = driver.findElement(By.xpath("(//div[@class = 'uHMk6b fsHoPb'])[4]"));
        if (!testNgCheckBox.isSelected()) {
            wrapper.clickOnElement(testNgCheckBox);
        }

        WebElement dropdown = driver.findElement(By.className("ry3kXd"));
        wrapper.clickOnElement(dropdown);
        Thread.sleep(1000);

        System.out.println("Selecting dropdown option");
        WebElement option = driver.findElement(By.xpath("//div[@role='option']//span[@class='vRMGwf oJeWuf'][normalize-space()='Mr']"));
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
        wrapper.sendText(hour, "07");
        WebElement minute = driver.findElement(By.xpath("//input[@aria-label='Minute']"));
        wrapper.sendText(minute, "30");

        WebElement submitButton = driver.findElement(By.xpath("(//span[@class = 'l4V7wb Fxmcue'])[1]"));
        wrapper.clickOnElement(submitButton);

        WebElement responseText = driver.findElement(By.xpath("//div[@class='vHW8K']"));
        System.out.println(responseText.getText());
        if (responseText.getText().contains("Thanks")) {
            System.out.println("Form submitted successfully");
        }
        System.out.println("Start TestCase 02");
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