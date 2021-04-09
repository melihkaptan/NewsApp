package com.melhkptn.newsapp.service

import com.melhkptn.newsapp.model.News
import io.reactivex.Single
import retrofit2.http.GET

interface NewsAPI {

    @GET("top-headlines?country=tr&apiKey=2ad0205144c34cf28bb2d5adb76d6335")
    fun getNews() : Single<News>
}