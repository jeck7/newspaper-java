package test.newspaper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArticleScraper {

    private String url;

    public ArticleScraper(String url) {
        this.url = url;
    }

    public Article scrape() throws IOException {
        Document document = Jsoup.connect(url).get();
        String title = document.title();
        String text = extractText(document);
        String topImage = extractTopImage(document);
        List<String> authors = extractAuthors(document);
        String publishDate = extractPublishDate(document);

        return new Article(title, authors, publishDate, text, topImage);
    }

    private String extractText(Document document) {
        Element body = document.body();
        return body != null ? body.text() : "";
    }

    private String extractTopImage(Document document) {
        Element image = document.select("meta[property=og:image]").first();
        return image != null ? image.attr("content") : "";
    }

    private List<String> extractAuthors(Document document) {
        List<String> authors = new ArrayList<>();
        // Logic to extract authors from the document
        return authors;
    }

    private String extractPublishDate(Document document) {
        Element dateElement = document.select("meta[property=article:published_time]").first();
        return dateElement != null ? dateElement.attr("content") : "";
    }
}