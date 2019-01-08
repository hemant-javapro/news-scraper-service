package com.news.service;

import com.google.gson.Gson;
import com.news.scraper.Article;
import com.news.scraper.ScrapStarter;
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
@Path("/authors")
public class Authors {

    /**
     * Returns all the authors
     *
     * @return List of authors in JSON format as response.
     */
    @GET
    public Response getAuthors() {
        List<Article> articles = Arrays.asList(new ScrapStarter().getScrapedData());

        List<String> article = articles.stream()
                .map(Article::getAuthor)
                .collect(Collectors.toList());

        return Response.status(200).entity(new Gson().toJson(article)).build();
    }

    /**
     * Returns all the authors containing given search text
     *
     * @param searchText a {@link java.lang.String}
     * @return List of authors in JSON format as response.
     */
    @GET
    @Path("/{search-text}")
    public Response getAuthors(@PathParam("search-text") String searchText) {
        List<Article> articles = Arrays.asList(new ScrapStarter().getScrapedData());

        List<String> article = articles.stream()
                .filter(p -> p.getAuthor() != null && p.getAuthor().toLowerCase().matches(".*" + searchText.toLowerCase() + ".*"))
                .map(Article::getAuthor)
                .collect(Collectors.toList());

        return Response.status(200).entity(new Gson().toJson(article)).build();
    }

}
