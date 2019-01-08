package com.news.scraper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Hemant Sharma
 */
public class ArticleScraper implements Runnable {

    private final String url;

    public ArticleScraper(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        scrapData();
    }

    /**
     * featches required data from provided url. creates an object of Article
     * and writes fetched data to it. adds article object to global static list
     * ARTICLES
     */
    public void scrapData() {

        try {

            Article page = new Article();

            Document document = Jsoup.connect(url).timeout(5000).get();

            Elements metatags = document.select("meta[name='title'], meta[name='description'], meta[name='article:author']");

            metatags.forEach((metatag) -> {
                switch (metatag.attr("name")) {
                    case "title":
                        page.setTitle(metatag.attr("content"));
                        break;
                    case "description":
                        page.setDescription(metatag.attr("content"));
                        break;
                    case "article:author":
                        page.setAuthor(metatag.attr("content"));
                        break;
                    default:
                        break;
                }
            });

            ScrapStarter.ARTICLES.add(page);

            System.out.println("> " + page.getTitle() + ":" + page.getAuthor() + ":" + page.getDescription());

        } catch (IOException e) {

            System.err.println("[Scraper] For '" + url + "': " + e.getMessage());

        }

    }

}
