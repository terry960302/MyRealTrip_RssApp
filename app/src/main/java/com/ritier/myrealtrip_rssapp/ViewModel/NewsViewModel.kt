package com.ritier.myrealtrip_rssapp.ViewModel

import android.util.Log
import androidx.lifecycle.*
import com.ritier.myrealtrip_rssapp.Repository.NewsRepository
import com.ritier.myrealtrip_rssapp.Util.mappingModel
import com.ritier.myrealtrip_rssapp.Util.notifyObserver
import com.ritier.myrealtrip_rssapp.model.NewsItem
import com.ritier.myrealtrip_rssapp.model.NewsListItem
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import kotlinx.coroutines.launch

class NewsViewModel(val newsRepo: NewsRepository) : ViewModel() {

    private val tag = "NewsViewModel"
    private var repoLiveData: LiveData<MutableList<NewsItem>?> = newsRepo.getNewsItems()

    //https://developer.android.com/topic/libraries/architecture/coroutines
    fun getNewsItems(): MutableLiveData<MutableList<NewsListItem>>? {
        Log.d(tag, "뷰모델 함수 실행")

        Log.d(tag, "가져온 데이터 : ${repoLiveData?.value}")

        if (!repoLiveData?.value.isNullOrEmpty()) {
            val result = MutableLiveData<MutableList<NewsListItem>>()
            val emptyModel = NewsListItem(null, "", "", "", mutableListOf())

            viewModelScope.launch {
                val itemList = mutableListOf<NewsListItem>()
                repoLiveData?.value?.forEach {
                    if (mappingModel(it) == emptyModel) {
                        return@forEach
                    } else {
                        itemList.add(mappingModel(it))
                        result.value = itemList
                        result.notifyObserver()
                        Log.d(tag, "데이터 추가 성공: ${it.title}")
                    }
                }
            }
            return result
        } else {
            Log.d(tag, "빈값만 받아서 반환합니다.")
            return null
        }

//    fun getNewsItems(): LiveData<MutableList<NewsListItem>>? =
//        LiveDataReactiveStreams.fromPublisher(repoLiveData?.map { it ->
//            it.map { item ->
//                mappingModel(
//                    item
//                )
//            }.toMutableList()
//        }!!.toFlowable(BackpressureStrategy.LATEST))


}


}