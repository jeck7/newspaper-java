package test.newspaper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
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
//            System.out.println("PageSource: " + driver.getPageSource());
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
            json = json.getJSONObject("props")
                    .getJSONObject("pageProps")
                    .getJSONObject("featureCollections");
            List<Object> authorsMatches = findAllKeys(json, "authors");
            for (Object match : authorsMatches) {
                if (match instanceof JSONArray) {
                    JSONArray authorsArray = (JSONArray) match;
                    for (int i = 0; i < authorsArray.length(); i++) {
                        JSONObject author = authorsArray.getJSONObject(i);
                        System.out.println("Author: " + author.getString("name"));
                    }
                } else {
                    System.out.println("Found value: " + match.toString());
                }
            }

//            This utility supports both:
//            Raw key search (set filterKey/filterValue to null)
//            Filtered key search within arrays or single objects

//            // Search for all "authors" entries where "seoName" is "eve-hartley"
//            List<JSONObject> filteredAuthors = JSONUtils.findAllMatchingObjects(json, "authors", "seoName", "eve-hartley");
//
//            for (JSONObject author : filteredAuthors) {
//                System.out.println("Name: " + author.getString("name"));
//                System.out.println("Hedcut: " + author.optString("hedcut", "N/A"));
//                System.out.println("ID: " + author.getInt("id"));
//                System.out.println("---");
//            }

//            System.out.println("Content: " + scriptTag.getText());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }


    public static List<Object> findAllKeys(JSONObject json, String keyToFind) {
        List<Object> results = new ArrayList<>();
        recursiveSearch(json, keyToFind, results);
        return results;
    }

    private static void recursiveSearch(Object current, String keyToFind, List<Object> results) {
        if (current instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) current;
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);

                if (key.equals(keyToFind)) {
                    results.add(value);
                }

                // Recursive call
                recursiveSearch(value, keyToFind, results);
            }
        } else if (current instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) current;
            for (int i = 0; i < jsonArray.length(); i++) {
                recursiveSearch(jsonArray.get(i), keyToFind, results);
            }
        }
    }
}

