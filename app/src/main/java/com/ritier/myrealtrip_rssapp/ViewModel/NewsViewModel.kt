package com.ritier.myrealtrip_rssapp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ritier.myrealtrip_rssapp.Repository.NewsRepository
import com.ritier.myrealtrip_rssapp.Util.Utils
import com.ritier.myrealtrip_rssapp.model.NewsListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext

class NewsViewModel(private var newsRepo: NewsRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val tag = "NewsViewModel"

    //https://developer.android.com/topic/libraries/architecture/coroutines
    suspend fun getNewsItems(): MutableLiveData<MutableList<NewsListItem>> =
        withContext(Dispatchers.IO + viewModelJob) {

            var result = MutableLiveData<MutableList<NewsListItem>>()

            val itemList = newsRepo.getNewsItems().value
            Log.d(tag, "repo에서 가져온 데이터 : ${itemList?.size}개")

            itemList?.forEach { newsItem ->
                Utils.resBodyToModel(newsItem) {
                    result.value?.add(it)
                    Log.d("NewsViewModel", "어댑터에 붙음 : ${it.title}")
                }
            }
            return@withContext result
        }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}