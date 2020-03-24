package com.ritier.myrealtrip_rssapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.model.NewsItem
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsRepository(newsApi: NewsApi) {

    val tag = "NewsRepository"
    private val apiObservable by lazy { newsApi.getNewsItems() }
//    private val result by lazy { MutableLiveData<MutableList<NewsItem>>() }

    fun getNewsItems(): LiveData<MutableList<NewsItem>?> =
        LiveDataReactiveStreams.fromPublisher(apiObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { item -> item.channel?.newsItems }.toFlowable(BackpressureStrategy.LATEST)
        )

//    fun getNewsItems(): MutableLiveData<MutableList<NewsItem>> {
//
//        Log.d(tag, "레포 함수 실행")
//
//        val result = MutableLiveData<MutableList<NewsItem>>()
//
//        apiCall.enqueue(object : retrofit2.Callback<Rss> {
//
//            override fun onFailure(call: Call<Rss>, t: Throwable) {
//                Log.e(tag, "에러 : ${t.message}")
//                result.value = mutableListOf()
//            }
//
//            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
//                if(response.isSuccessful){
//                    val itemList = response.body()?.channel?.newsItems
//                    result.value = itemList
//                    Log.d(tag, "데이터 성공 : ${result.value?.size}개")
//                }
//                else{
//                    result.value = mutableListOf()
//                    Log.d(tag, "데이터 실패 : ${result.value}")
//                }
//            }
//        })
//        //TODO : onResponse 를 안거치고 너무 빨리 반환하는 문제(이 때문에 ViewModel에서 null값만 받음.)
//        Log.d(tag, "반환하기 전 레포 데이터 : ${result.value}")
//        return result
//    }
}