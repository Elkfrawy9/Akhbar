package com.elkfrawy.akhbar.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.function.BinaryOperator
import androidx.annotation.Nullable

@Entity(tableName = "article")
data class Article(

    @PrimaryKey(autoGenerate = true)
    val id:Int,

    var saved: Boolean,

    @Embedded val source: Source,

    @Nullable
    var author: String?,

    @Nullable
    val title: String?,

    @SerializedName("description")
    @Nullable
    val desc: String?,

    val url: String ,

    @ColumnInfo(name = "image_url")
    @SerializedName("urlToImage")
    @Nullable
    val imageUrl: String?,

    @SerializedName("publishedAt")
    @ColumnInfo(defaultValue = "")
    @Nullable
    val date: String ,

    @ColumnInfo(defaultValue = "")
    @Nullable
    val content: String?,
)
