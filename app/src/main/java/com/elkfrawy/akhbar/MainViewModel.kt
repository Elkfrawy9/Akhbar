package com.elkfrawy.akhbar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elkfrawy.akhbar.core.API_KEY
import com.elkfrawy.akhbar.data.ArticleDao
import com.elkfrawy.akhbar.data.NewsApi
import com.elkfrawy.akhbar.model.Article
import com.elkfrawy.akhbar.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val newsApi: NewsApi,
    val articleDao: ArticleDao,
    val appScope:CoroutineScope
) : ViewModel() {

    private val _liveData:MutableLiveData<News> = MutableLiveData()
    public val  liveData:LiveData<News> = _liveData



    fun getNews(){
        appScope.launch (Dispatchers.IO){
            val response = newsApi.getNews("us", API_KEY)
            if (response.isSuccessful){
                response.body()?.let {
                    _liveData.postValue(it)
                }
            }
        }
    }

    fun getSearchResult(text: String){
        appScope.launch (Dispatchers.IO){
            val response = newsApi.search(text, API_KEY)
            if (response.isSuccessful){
                response.body()?.let {
                    _liveData.postValue(it)
                }
            }
        }
    }

    fun insert(article: Article){
        appScope.launch(Dispatchers.IO) {
            articleDao.insert(article)
        }
    }


    fun delete(article: Article){
        appScope.launch {
            articleDao.delete(article)
        }
    }

    fun getCachedArticle(): LiveData<List<Article>>{
        return articleDao.getCachedNews()
    }






}