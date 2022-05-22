package com.elkfrawy.akhbar.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.elkfrawy.akhbar.R
import com.elkfrawy.akhbar.core.BASE_URL
import com.elkfrawy.akhbar.data.ArticleDao
import com.elkfrawy.akhbar.data.ArticleDatabase
import com.elkfrawy.akhbar.data.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CountDownLatch
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun getRetrofit(): NewsApi{
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun requestOptions():RequestOptions =
         RequestOptions.errorOf(R.drawable.ic_no_image)


    @Singleton
    @Provides
    fun getGlide(@ApplicationContext context: Context, requestOptions: RequestOptions) =
        Glide.with(context).applyDefaultRequestOptions(requestOptions)


    @Singleton
    @Provides
    fun room(@ApplicationContext context: Context) =
         Room.
         databaseBuilder(context, ArticleDatabase::class.java, "article")
             .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun getArticle(appDatabase: ArticleDatabase) = appDatabase.getArticleDao()

    @Singleton
    @Provides
    fun applicationScope() = CoroutineScope(SupervisorJob())


}