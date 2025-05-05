package test.newspaper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    public static String fetchHtml(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        if (connection.getResponseCode() == 200) {
            Document document = Jsoup.parse(connection.getInputStream(), null, urlString);
            return document.html();
        } else {
            throw new IOException("Failed to fetch HTML: " + connection.getResponseCode());
        }
    }

    public static String cleanText(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }
}