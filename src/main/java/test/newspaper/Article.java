package test.newspaper;

import java.util.List;

public class Article {
    private String title;
    private List<String> authors;
    private String publishDate;
    private String text;
    private String topImage;

    public Article(String title, List<String> authors, String publishDate, String text, String topImage) {
        this.title = title;
        this.authors = authors;
        this.publishDate = publishDate;
        this.text = text;
        this.topImage = topImage;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getText() {
        return text;
    }

    public String getTopImage() {
        return topImage;
    }
}