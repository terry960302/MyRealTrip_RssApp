package com.ritier.myrealtrip_rssapp.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.model.NewsItem
import com.ritier.myrealtrip_rssapp.model.Rss
import retrofit2.Call
import retrofit2.Response

class NewsRepository(newsApi: NewsApi) {

    val tag = "NewsRepository"
    private val apiCall by lazy { newsApi.getNewsItems() }

    fun getNewsItems(): MutableLiveData<MutableList<NewsItem>> {

        Log.d(tag, "레포 함수 실행")

        val result = MutableLiveData<MutableList<NewsItem>>()

        apiCall.enqueue(object : retrofit2.Callback<Rss> {

            override fun onFailure(call: Call<Rss>, t: Throwable) {
                Log.e(tag, "에러 : ${t.message}")
                result.value = mutableListOf()
            }

            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
                if(response.isSuccessful){
                    val itemList = response.body()?.channel?.newsItems
                    result.value = itemList
                    Log.d(tag, "데이터 성공 : ${result.value?.size}개")
                }
                else{
                    result.value = mutableListOf()
                    Log.d(tag, "데이터 실패 : ${result.value}")
                }
            }
        })
        //TODO : onResponse 를 안거치고 너무 빨리 반환하는 문제(이 때문에 ViewModel에서 null값만 받음.)
        Log.d(tag, "반환하기 전 레포 데이터 : ${result.value}")
        return result
    }
}