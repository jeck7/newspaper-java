package test.newspaper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Set;

public class BypassDataDome {
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

            ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

//            Thread.sleep(10000);

            System.out.println("Title: " + driver.getTitle());
            // with Selenium WebDriver  page source is available
            System.out.println("PageSource: " + driver.getPageSource());

            Set<Cookie> cookies = driver.manage().getCookies();

            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + "=" + cookie.getValue());
            }
            // Find the script tag by ID
            WebElement scriptTag = driver.findElement(By.id("__NEXT_DATA__"));
            // Get the raw JSON content
            String jsonString = scriptTag.getAttribute("innerHTML");
            // Parse using org.json
            JSONObject json = new JSONObject(jsonString);

            Object result = findKey(json, "authors");
            JSONArray authors;
            if (result instanceof JSONArray) {
                authors = (JSONArray) result;
                for (int i = 0; i < authors.length(); i++) {
                    JSONObject author = authors.getJSONObject(i);
                    System.out.println("Author: " + author.getString("name"));
                }
            } else {
                System.out.println("Key not found or not an array.");
            }

            System.out.println("Content: " + scriptTag.getText());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public static Object findKey(JSONObject json, String keyToFind) {
        for (String key : json.keySet()) {
            Object value = json.get(key);

            if (key.equals(keyToFind)) {
                return value;
            }

            if (value instanceof JSONObject) {
                Object found = findKey((JSONObject) value, keyToFind);
                if (found != null) return found;
            } else if (value instanceof JSONArray) {
                for (int i = 0; i < ((JSONArray) value).length(); i++) {
                    Object item = ((JSONArray) value).get(i);
                    if (item instanceof JSONObject) {
                        Object found = findKey((JSONObject) item, keyToFind);
                        if (found != null) return found;
                    }
                }
            }
        }
        return null;
    }
}

