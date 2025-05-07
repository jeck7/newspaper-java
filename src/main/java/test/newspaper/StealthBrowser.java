package test.newspaper;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class StealthBrowser {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/hristo/Desktop/chromedriver-mac-arm64/chromedriver");


        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            driver.get("https://www.wsj.com/articles/ai-agents-are-learning-how-to-collaborate-companies-need-to-work-with-them-28c7464d?st=nARTcV&reflink=article_email_share");

            // Hide webdriver flag
            ((JavascriptExecutor) driver).executeScript(
                    "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
            );

            // Let the page load and scripts run
            Thread.sleep(10000);

            // Print or extract page content
            System.out.println(driver.getTitle());

            // Extract cookies
            for (Cookie cookie : driver.manage().getCookies()) {
                System.out.println(cookie.getName() + "=" + cookie.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}

