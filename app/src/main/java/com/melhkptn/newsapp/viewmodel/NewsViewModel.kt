package com.melhkptn.newsapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.melhkptn.newsapp.model.Article
import com.melhkptn.newsapp.model.News
import com.melhkptn.newsapp.service.NewsAPIService
import com.melhkptn.newsapp.service.room.NewsDatabase
import com.melhkptn.newsapp.util.PrivateSharedPrefences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : BaseViewModel(application) {

    val articleList = MutableLiveData<List<Article>>()
    val loading = MutableLiveData<Boolean>(false)
    private val apiService = NewsAPIService()
    private val disposable = CompositeDisposable()
    private val privateSharedPrefences = PrivateSharedPrefences(getApplication())
    private val updateTime = 30 * 60 * 1000 * 1000 * 1000L // Nano time metriğinde 30 dk


    fun getNews() {

        val savedTime = privateSharedPrefences.takeTime()
        if (savedTime != null && savedTime > 0L && System.nanoTime() - savedTime < updateTime) {
            getDataRoom()
        } else {
            getDataOnline()
        }

    }

    fun getDataOnline() {

        loading.value = true

        disposable.add(
            apiService.getNewsData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<News>() {
                    override fun onSuccess(t: News) {
                        saveArticles(t.articles)

                        Toast.makeText(
                            getApplication(),
                            "Verileri internetten aldık.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getDataRoom() {

        loading.value = true
        launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            showArticleList(dao.getAllArticles())
            Toast.makeText(getApplication(), "Verileri roomdan aldık.", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveArticles(articles: List<Article>) {
        launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            dao.deleteAllArticles()

            val uuidList =
                dao.insertAll(*articles.toTypedArray())
            var index = 0
            while (index < articles.size) {
                articles[index].uuid = uuidList[index]
                index++
            }

            showArticleList(articles)
        }

        privateSharedPrefences.saveTime(System.nanoTime())
    }

    private fun showArticleList(list: List<Article>) {
        articleList.value = list
        loading.value = false
    }
}