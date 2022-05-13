package com.elkfrawy.akhbar.data

import com.elkfrawy.akhbar.model.Article
import com.elkfrawy.akhbar.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getNews(@Query("country") countries:String, @Query("apiKey") apiKey:String):Response<News>

    @GET("everything")
    suspend fun search(@Query("q") text:String, @Query("apiKey") apiKey:String):Response<News>

}