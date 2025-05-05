package test.newspaper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentExtractor {

    private final String language;
    private final Stopwords stopwords;

    public ContentExtractor(String language) {
        this.language = language;
        this.stopwords = new Stopwords(language);
    }

    public List<String> getAuthors(Document doc) {
        List<String> authors = new ArrayList<>();
        String[] authorTags = {"meta[name=author]", "meta[property=author]"};
        for (String tag : authorTags) {
            Elements elements = doc.select(tag);
            for (Element element : elements) {
                String content = element.attr("content");
                if (!content.isEmpty()) {
                    authors.add(content);
                }
            }
        }
        return authors;
    }

    public Date getPublishingDate(String url, Document doc) {
        // Strategy 1: Extract date from URL
        Pattern datePattern = Pattern.compile("\\d{4}/\\d{2}/\\d{2}");
        Matcher matcher = datePattern.matcher(url);
        if (matcher.find()) {
            try {
                return new SimpleDateFormat("yyyy/MM/dd").parse(matcher.group());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Strategy 2: Extract date from meta tags
        String[] dateMetaTags = {
                "meta[property=article:published_time]",
                "meta[name=publish_date]",
                "meta[name=pubdate]"
        };
        for (String tag : dateMetaTags) {
            Elements elements = doc.select(tag);
            for (Element element : elements) {
                String content = element.attr("content");
                try {
                    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(content);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public String getTitle(Document doc) {
        String title = doc.title();
        Elements ogTitle = doc.select("meta[property=og:title]");
        if (!ogTitle.isEmpty()) {
            title = ogTitle.attr("content");
        }
        return title;
    }

    public String getMetaDescription(Document doc) {
        Elements metaDescription = doc.select("meta[name=description]");
        if (!metaDescription.isEmpty()) {
            return metaDescription.attr("content");
        }
        return "";
    }

    public Set<String> getKeywords(Document doc) {
        Elements metaKeywords = doc.select("meta[name=keywords]");
        if (!metaKeywords.isEmpty()) {
            String content = metaKeywords.attr("content");
            return new HashSet<>(Arrays.asList(content.split(",")));
        }
        return Collections.emptySet();
    }

    public String getTopImage(Document doc) {
        Elements ogImage = doc.select("meta[property=og:image]");
        if (!ogImage.isEmpty()) {
            return ogImage.attr("content");
        }
        return "";
    }

    public Set<String> getTags(Document doc) {
        Set<String> tags = new HashSet<>();
        Elements tagElements = doc.select("a[rel=tag], a[href*='/tag/']");
        for (Element element : tagElements) {
            tags.add(element.text());
        }
        return tags;
    }

    public List<String> getUrls(Document doc) {
        List<String> urls = new ArrayList<>();
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            urls.add(link.attr("abs:href"));
        }
        return urls;
    }

    public String getCanonicalLink(Document doc) {
        Elements canonicalLink = doc.select("link[rel=canonical]");
        if (!canonicalLink.isEmpty()) {
            return canonicalLink.attr("href");
        }
        return "";
    }

    public String getLanguage(Document doc) {
        String lang = doc.select("html").attr("lang");
        if (lang.isEmpty()) {
            Elements metaLang = doc.select("meta[http-equiv=content-language]");
            if (!metaLang.isEmpty()) {
                lang = metaLang.attr("content");
            }
        }
        return lang.isEmpty() ? "en" : lang;
    }

    // Helper class for stopwords
    static class Stopwords {
        private final Set<String> stopwords;

        public Stopwords(String language) {
            this.stopwords = loadStopwords(language);
        }

        private Set<String> loadStopwords(String language) {
            // Load stopwords based on language (simplified for demo)
            return new HashSet<>(Arrays.asList("the", "and", "is", "in", "at", "of"));
        }

        public boolean isStopword(String word) {
            return stopwords.contains(word.toLowerCase());
        }
    }

    public static void main(String[] args) throws Exception {
        // Example usage
//        String url = "https://example.com/article";
//        String url = "https://www.wsj.com/articles/ai-agents-are-learning-how-to-collaborate-companies-need-to-work-with-them-28c7464d?st=nARTcV&reflink=article_email_share";
        String url = "https://www.ft.com/content/70cba9ec-caad-4958-bed9-59d43e8da6c2";
        Document doc = Jsoup.connect(url).get();

        ContentExtractor extractor = new ContentExtractor("en");
        System.out.println("Title: " + extractor.getTitle(doc));
        System.out.println("Authors: " + extractor.getAuthors(doc));
        System.out.println("Publishing Date: " + extractor.getPublishingDate(url, doc));
        System.out.println("Meta Description: " + extractor.getMetaDescription(doc));
        System.out.println("Keywords: " + extractor.getKeywords(doc));
        System.out.println("Top Image: " + extractor.getTopImage(doc));
        System.out.println("Tags: " + extractor.getTags(doc));
        System.out.println("Canonical Link: " + extractor.getCanonicalLink(doc));
        System.out.println("Language: " + extractor.getLanguage(doc));
    }


}
