# Newspaper Java Library

Newspaper Java is a Java library for scraping and curating news articles from various online sources. Inspired by the Newspaper3k Python library, this project aims to provide similar functionality in Java, allowing users to extract relevant information from articles easily.

## Features

- Article scraping from URLs
- Automatic language detection
- Natural Language Processing (NLP) capabilities for keyword extraction and summarization
- Support for multiple languages
- Simple and intuitive API

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven for dependency management

### Installation

1. Clone the repository:

   ```
   git clone https://github.com/yourusername/newspaper-java.git
   ```

2. Navigate to the project directory:

   ```
   cd newspaper-java
   ```

3. Build the project using Maven:

   ```
   mvn clean install
   ```

### Usage

To use the Newspaper Java library, you can create an instance of the `ArticleScraper` class and call its methods to download and parse articles. Here’s a simple example:

```java
import test.newspaper.ArticleScraper;

public class Main {
    public static void main(String[] args) {
        ArticleScraper scraper = new ArticleScraper();
        scraper.scrape("http://example.com/news-article");
    }
}
```

### Configuration

You can configure various settings in the `src/main/resources/config.properties` file, such as API keys or default settings for the scraper.

### Running Tests

To run the unit tests, use the following Maven command:

```
mvn test
```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any enhancements or bug fixes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.