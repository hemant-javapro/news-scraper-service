package com.news.scraper;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Hemant Sharma
 */
public class ScrapStarter {

    public static final String BASE_URL = "https://www.thehindu.com/";
    public static final ArrayList<Thread> THREADS = new ArrayList<Thread>();
    public static final List<Article> ARTICLES = new ArrayList<Article>();

    public static void main(String[] args) {
        try {

            Thread thread = new Thread(new ArticleFinder(BASE_URL + "archive/"));
            thread.start();
            THREADS.add(thread);

            for (int i = 0; i < THREADS.size(); i++) {
                THREADS.get(i).join();
            }

            new ScrapStarter().writeScrapedData(new Gson().toJson(ARTICLES, ArrayList.class));
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Writes the scraped data from saved JSON file
     *
     * @param data a {@link java.lang.String}
     */
    public void writeScrapedData(String data) {
        Properties prop = new Properties();
        InputStream is = null;
        FileWriter fw = null;
        String filename;

        try {
            is = this.getClass().getResourceAsStream("/application.properties");
            prop.load(is);
            filename = prop.getProperty("file");

            fw = new FileWriter(filename, false);
            fw.write(data);
            fw.flush();
        } catch (IOException io) {
            System.err.println("[Writing] Error: " + io.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Reads the scraped data from saved JSON file
     *
     * @return Array of Article object.
     */
    public Article[] getScrapedData() {
        Properties prop = new Properties();
        Article[] articles = null;
        InputStream is = null;
        BufferedReader br = null;
        String filename, line;
        StringBuilder sb = new StringBuilder();

        try {
            is = this.getClass().getResourceAsStream("/application.properties");
            prop.load(is);
            filename = prop.getProperty("file");

            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            articles = new Gson().fromJson(sb.toString(), Article[].class);

        } catch (IOException io) {
            System.err.println("[Reading] Error: " + io.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {

            }
        }

        return articles;
    }
}
