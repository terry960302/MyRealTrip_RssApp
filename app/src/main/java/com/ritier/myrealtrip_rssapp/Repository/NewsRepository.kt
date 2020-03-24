package com.ritier.myrealtrip_rssapp.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.Util.mappingModel
import com.ritier.myrealtrip_rssapp.model.NewsItem
import com.ritier.myrealtrip_rssapp.model.NewsListItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*


class NewsRepository(newsApi: NewsApi) {

    val tag = "NewsRepository"
    private val newsItems by lazy { newsApi.getNewsItems() }

    fun getNewsItems(): MutableLiveData<MutableList<NewsListItem>> {

        val result = MutableLiveData<MutableList<NewsListItem>>()
        val emptyErrorItem = NewsListItem(null, "오류입니다.", "", "", mutableListOf())

        val observer: DisposableObserver<MutableList<NewsItem>> =
            object : DisposableObserver<MutableList<NewsItem>>() {
                override fun onComplete() {
                    Log.d(tag, "뉴스 데이터 구독 완료")
                }

                override fun onNext(itemList: MutableList<NewsItem>) {
                    val job = Job()
                    val scope = CoroutineScope(Dispatchers.IO + job)
                    scope.launch {
                        kotlin.runCatching {
                            result.postValue(itemList.map { item -> mappingModel(item) }
                                .filter { item -> item != emptyErrorItem }
                                .toMutableList())
                        }.onSuccess {
                            Log.d(tag, "코루틴 파싱 성공")
                            job.cancelAndJoin()
                        }.onFailure {
                            Log.d(tag, "코루틴 파싱 실패 : ${it.message}")
                            job.cancelAndJoin()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    Log.e(tag, "뉴스 데이터 구독 실패 : ${e.message}")
                }

            }

        val disposable = newsItems.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { item -> item.channel?.newsItems }.subscribeWith(observer)

        return result
    }
}