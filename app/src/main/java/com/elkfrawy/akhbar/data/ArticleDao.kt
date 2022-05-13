package com.elkfrawy.akhbar.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.elkfrawy.akhbar.model.Article

@Dao
interface ArticleDao {

    @Insert
    suspend fun insert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM article")
    fun getCachedNews():LiveData<List<Article>>

}