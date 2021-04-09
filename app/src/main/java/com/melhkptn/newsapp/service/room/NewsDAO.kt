package com.melhkptn.newsapp.service.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.melhkptn.newsapp.model.Article

@Dao
interface NewsDAO {

    @Insert
    suspend fun insertAll(vararg articles: Article) : List<Long>

    @Query("SELECT * FROM article")
    suspend fun getAllArticles() : List<Article>

    @Query("SELECT * FROM article WHERE uuid = :articleId")
    suspend fun getArticle(articleId : Int) : Article

    @Query("DELETE FROM article")
    suspend fun deleteAllArticles()
}