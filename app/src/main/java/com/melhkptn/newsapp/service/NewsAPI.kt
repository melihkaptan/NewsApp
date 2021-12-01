package com.melhkptn.newsapp.service

import com.melhkptn.newsapp.BuildConfig.API_KEY
import com.melhkptn.newsapp.model.News
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines?country=tr")
    fun getNews(
        @Query("apiKey")
        apiKey: String = API_KEY
    ) : Single<News>
}