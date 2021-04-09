package com.melhkptn.newsapp.service

import com.melhkptn.newsapp.model.News
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NewsAPI::class.java)


    fun getNewsData(): Single<News> {
       return api.getNews()
    }

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}