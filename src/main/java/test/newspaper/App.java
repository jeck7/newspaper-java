package test.newspaper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class App {
    public static void main(String[] args) {
        // Initialize the application and handle command-line arguments
//        if (args.length < 1) {
//            System.out.println("Please provide a URL to scrape.");
//            return;
//        }

//        String url = "https://www.ft.com/content/70cba9ec-caad-4958-bed9-59d43e8da6c2";
//        String url = "https://www.wsj.com/";
        String url = "https://www.wsj.com/articles/ai-agents-are-learning-how-to-collaborate-companies-need-to-work-with-them-28c7464d?st=nARTcV&reflink=article_email_share";
        ArticleScraper scraper = new ArticleScraper(url);
        Article article = null;
        try {
            article = scraper.scrape();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (article != null) {
            System.out.println("Title: " + article.getTitle());
            System.out.println("Authors: " + String.join(", ", article.getAuthors()));
            System.out.println("Publish Date: " + article.getPublishDate());
            System.out.println("Text: " + article.getText());
            System.out.println("Top Image: " + article.getTopImage());
        } else {
            System.out.println("Failed to scrape the article from the provided URL.");
        }

//        URL website;
//        try {
//            URL google = new URL(url);
//            HttpURLConnection connection = (HttpURLConnection) google.openConnection();
//            connection.addRequestProperty("User-Agent", "Mozilla/4.0");
//            InputStream input;
//            if (connection.getResponseCode() == 200)  // this must be called before 'getErrorStream()' works
//                input = connection.getInputStream();
//            else input = connection.getErrorStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//            String msg;
//            while ((msg = reader.readLine()) != null)
//                System.out.println(msg);
//        } catch (MalformedURLException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


    }
}