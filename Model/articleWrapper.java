package com.example.michael.newsapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Michael on 23/05/2018.
 */

public class articleWrapper {
    @SerializedName("articles")
    private List<article> articles;

    public List<article> getArticles() {
        return articles;
    }

    public void setArticles(List<article> articles) {
        this.articles = articles;
    }
}
