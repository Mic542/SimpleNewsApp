package com.example.michael.newsapp.API;

import com.example.michael.newsapp.Model.articleWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
/**
 * Created by Michael on 23/05/2018.
 */

public interface ApiInterface {
    @GET("top-headlines")
    Call<articleWrapper> getTopHeadlines(@Query("country") String country,
                                         @Query("pageSize") String pageSize,
                                         @Query("page") int Page,
                                         @Query("apiKey") String apiKey);

    @GET("everything")
    Call<articleWrapper> getAllNews(@Query("q") String q,
                                    @Query("pageSize") String pageSize,
                                    @Query("page") int Page,
                                    @Query("apiKey") String apiKey);
}
