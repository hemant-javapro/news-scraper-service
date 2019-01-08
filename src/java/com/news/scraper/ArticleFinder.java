package com.news.scraper;

import static com.news.scraper.ScrapStarter.BASE_URL;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Hemant Sharma
 */
public class ArticleFinder implements Runnable {

    private final String url;

    public ArticleFinder(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        travers();
    }

    /**
     * traverses a url to find articles and other archive hierarchy urls. starts
     * a new thread of ArticleScaper to scrap data from found articles. starts a
     * new thread of ArticleFinder to traverse the hierarchy.
     */
    public void travers() {

        try {

            Document document = Jsoup.connect(url).timeout(5000).get();

            Elements anchors = document.select("ul[class='archiveMonthList'] a[href^='" + BASE_URL + "']");

            anchors.addAll(document.select("table[class='archiveTable'] a[href^='" + BASE_URL + "']"));

            anchors.addAll(document.select("ul[class='archive-list'] a[href^='" + BASE_URL + "']"));

            anchors.forEach((anchor) -> {
                if (anchor.attr("abs:href").endsWith(".ece")) {
                    Thread thread = new Thread(new ArticleScraper(anchor.attr("abs:href")));
                    thread.start();
                    ScrapStarter.THREADS.add(thread);
                } else {
                    Thread thread = new Thread(new ArticleFinder(anchor.attr("abs:href")));
                    thread.start();
                    ScrapStarter.THREADS.add(thread);
                }
            });

        } catch (IOException e) {

            System.err.println("[Finder] For '" + url + "': " + e.getMessage());

        }

    }

}
