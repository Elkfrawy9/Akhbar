package com.elkfrawy.akhbar.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elkfrawy.akhbar.model.Article

@Database(entities = [Article::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

}