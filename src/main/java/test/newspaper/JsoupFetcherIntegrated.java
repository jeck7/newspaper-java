package test.newspaper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsoupFetcherIntegrated {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.wsj.com/");

            ((JavascriptExecutor) driver).executeScript(
                    "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
            );

            Thread.sleep(10000);

            System.out.println("Title: " + driver.getTitle());

            Map<String, String> cookieMap = new HashMap<>();
            Set<Cookie> cookies = driver.manage().getCookies();
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }

            // Call JsoupFetcher with cookies
            JsoupFetcher.fetchPageWithCookies(cookieMap);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public static class JsoupFetcher {
        public static void fetchPageWithCookies(Map<String, String> cookies) {
            try {
                Document doc = Jsoup.connect("https://www.wsj.com/")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                        .cookies(cookies)
                        .get();

                System.out.println("Jsoup page title: " + doc.title());
                System.out.println(doc.body().text().substring(0, 300)); // Preview

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//    public static void fetchPageWithCookies(Map<String, String> cookies) {
//        try {
//            Document doc = Jsoup.connect("https://www.wsj.com/")
//                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
//                .cookies(cookies)
//                .get();
//
//            System.out.println("Jsoup page title: " + doc.title());
//            System.out.println(doc.body().text().substring(0, 300)); // Preview
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
