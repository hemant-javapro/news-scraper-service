package com.news.service;

import com.google.gson.Gson;
import com.news.scraper.Article;
import com.news.scraper.ScrapSarter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Hemant Sharma
 */
@Path("/articles")
public class Authors {

    /**
     * Returns all articles
     *
     * @return List of articles in JSON format as response.
     */
    @GET
    public Response getArticles() {
        List<Article> articles = Arrays.asList(new ScrapSarter().getScrapedData());

        return Response.status(200).entity(new Gson().toJson(articles)).build();
    }

    /**
     * Returns all the articles containing given author
     *
     * @param searchText a {@link java.lang.String}
     * @return List of articles in JSON format as response.
     */
    @GET
    @Path("/byauthor/{search-text}")
    public Response getArticlesByAuthor(@PathParam("search-text") String searchText) {
        List<Article> articles = Arrays.asList(new ScrapSarter().getScrapedData());

        List<Article> article = articles.stream()
                .filter(p -> p.getAuthor() != null && p.getAuthor().toLowerCase().matches(".*" + searchText.toLowerCase() + ".*"))
                .collect(Collectors.toList());

        return Response.status(200).entity(new Gson().toJson(article)).build();
    }

    /**
     * Returns all the articles containing given title
     *
     * @param searchText a {@link java.lang.String}
     * @return List of articles in JSON format as response.
     */
    @GET
    @Path("/bytitle/{search-text}")
    public Response getArticlesByTitle(@PathParam("search-text") String searchText) {
        List<Article> articles = Arrays.asList(new ScrapSarter().getScrapedData());

        List<Article> article = articles.stream()
                .filter(p -> p.getTitle() != null && p.getTitle().toLowerCase().matches(".*" + searchText.toLowerCase() + ".*"))
                .collect(Collectors.toList());

        return Response.status(200).entity(new Gson().toJson(article)).build();
    }

    /**
     * Returns all the articles containing given description
     *
     * @param searchText a {@link java.lang.String}
     * @return List of articles in JSON format as response.
     */
    @GET
    @Path("/bydescription/{search-text}")
    public Response getArticlesByDescription(@PathParam("search-text") String searchText) {
        List<Article> articles = Arrays.asList(new ScrapSarter().getScrapedData());

        List<Article> article = articles.stream()
                .filter(p -> p.getDescription() != null && p.getDescription().toLowerCase().matches(".*" + searchText.toLowerCase() + ".*"))
                .collect(Collectors.toList());

        return Response.status(200).entity(new Gson().toJson(article)).build();
    }

}
