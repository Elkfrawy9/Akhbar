package com.elkfrawy.akhbar.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.function.BinaryOperator

@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var saved: Boolean,
    @Embedded val source: Source,
    var author: String = "",
    val title: String = "",
    @ColumnInfo(name = "description")
    @SerializedName("description")
    val desc: String = "",
    val url: String = "",
    @ColumnInfo(name = "image_url")
    @SerializedName("urlToImage")
    val imageUrl: String = "",
    @SerializedName("publishedAt")
    val date: String = "",
    val content: String = "",
)
