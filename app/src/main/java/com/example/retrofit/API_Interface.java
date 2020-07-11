package com.example.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Interface {
    @GET("/v2/top-headlines?country=in&apiKey=982679c1932a4c71a109373918d8efa4")

        Call<String> getNews();

 //   Call<String> getNews(@Query("sources") String sourceValue, @Query("apiKey") String apiKey);

    @GET("/v2/top-headlines?country=in&category=sports&apiKey=982679c1932a4c71a109373918d8efa4")
    Call<String> getSports();

    @GET("/v2/top-headlines?country=in&category=business&apiKey=982679c1932a4c71a109373918d8efa4")
    Call<String> getBusiness();

    @GET("/v2/top-headlines?country=in&category=health&apiKey=982679c1932a4c71a109373918d8efa4")
    Call<String> getHealth();

}
