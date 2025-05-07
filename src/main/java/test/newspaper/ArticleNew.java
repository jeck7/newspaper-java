package test.newspaper;

import org.jsoup.nodes.Document;

import java.util.*;
import java.util.logging.Logger;

public class ArticleNew {
    private static final Logger log = Logger.getLogger(ArticleNew.class.getName());

    private String url;
    private String sourceUrl;
    private String title;
    private String topImage;
    private String metaImage;
    private Set<String> images;
    private List<String> movies;
    private String text;
    private List<String> keywords;
    private List<String> metaKeywords;
    private Set<String> tags;
    private List<String> authors;
    private Date publishDate;
    private String summary;
    private String html;
    private String articleHtml;
    private boolean isParsed;
    private String metaDescription;
    private String metaLanguage;
    private String metaFavicon;
    private Map<String, Object> metaData;
    private String canonicalLink;
    private Document doc;

    private ContentExtractor extractor;

    public ArticleNew(String url, String sourceUrl, ContentExtractor extractor) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        this.url = url;
        this.sourceUrl = sourceUrl != null ? sourceUrl : extractSourceUrl(url);
        this.extractor = extractor;

        this.title = "";
        this.topImage = "";
        this.metaImage = "";
        this.images = new HashSet<>();
        this.movies = new ArrayList<>();
        this.text = "";
        this.keywords = new ArrayList<>();
        this.metaKeywords = new ArrayList<>();
        this.tags = new HashSet<>();
        this.authors = new ArrayList<>();
        this.publishDate = null;
        this.summary = "";
        this.html = "";
        this.articleHtml = "";
        this.isParsed = false;
        this.metaDescription = "";
        this.metaLanguage = "";
        this.metaFavicon = "";
        this.metaData = new HashMap<>();
        this.canonicalLink = "";
    }

    public void download(String htmlContent) {
        if (htmlContent == null || htmlContent.isEmpty()) {
            throw new IllegalArgumentException("HTML content cannot be null or empty");
        }
        this.html = htmlContent;
    }

    public void parse() {
        if (html == null || html.isEmpty()) {
            throw new IllegalStateException("HTML content must be downloaded before parsing");
        }

        // Simulate parsing logic
//        this.title = extractor.getTitle(html);
//        this.authors = extractor.getAuthors(html);
//        this.metaLanguage = extractor.getLanguage(html);
//        this.metaDescription = extractor.getMetaDescription(html);
//        this.canonicalLink = extractor.getCanonicalLink(html);
//        this.tags = extractor.getTags(html);
//        this.metaKeywords = new ArrayList<>(extractor.getKeywords(html));
//        this.metaData = extractor.getMetaData(html);
//        this.publishDate = extractor.getPublishingDate(url, html);
//        this.text = extractor.getText(html);
//        this.topImage = extractor.getTopImage(html);
//        this.images = extractor.getImages(html);
//
//        this.isParsed = true;
    }

    public void summarize() {
        if (!isParsed) {
            throw new IllegalStateException("Article must be parsed before summarizing");
        }
//        this.summary = extractor.summarize(text);
    }

    private String extractSourceUrl(String url) {
        // Logic to extract source URL from the given URL
        return url.split("/")[2];
    }

    // Getters and setters for all fields
    public String getUrl() {
        return url;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTopImage() {
        return topImage;
    }

    public String getMetaImage() {
        return metaImage;
    }

    public Set<String> getImages() {
        return images;
    }

    public List<String> getMovies() {
        return movies;
    }

    public String getText() {
        return text;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public List<String> getMetaKeywords() {
        return metaKeywords;
    }

    public Set<String> getTags() {
        return tags;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getSummary() {
        return summary;
    }

    public String getHtml() {
        return html;
    }

    public String getArticleHtml() {
        return articleHtml;
    }

    public boolean isParsed() {
        return isParsed;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public String getMetaLanguage() {
        return metaLanguage;
    }

    public String getMetaFavicon() {
        return metaFavicon;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }

    public String getCanonicalLink() {
        return canonicalLink;
    }
}
