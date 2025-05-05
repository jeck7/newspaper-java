package test.newspaper;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        // Initialize the application and handle command-line arguments
        if (args.length < 1) {
            System.out.println("Please provide a URL to scrape.");
            return;
        }

        String url = args[0];
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
    }
}