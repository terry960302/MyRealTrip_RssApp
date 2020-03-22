package com.ritier.myrealtrip_rssapp.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.model.NewsItem
import com.ritier.myrealtrip_rssapp.model.Rss
import retrofit2.Call
import retrofit2.Response

class NewsRepository(private val newsApi: NewsApi) {

    val tag = "NewsRepository"

    init {
        getNewsItems()
    }

    fun getNewsItems(): MutableLiveData<MutableList<NewsItem>> {
        val liveData: MutableLiveData<MutableList<NewsItem>> = MutableLiveData()

        newsApi.getNewsItems().enqueue(object : retrofit2.Callback<Rss> {

            override fun onFailure(call: Call<Rss>, t: Throwable) {
                Log.e(tag, "에러 : ${t.message}")
                liveData.value = null
            }

            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
                if(response.isSuccessful){
                    val itemList = response.body()?.channel?.newsItems
                    liveData.value = itemList
                    Log.d(tag, "데이터 성공 : ${liveData.value?.size}개")
                }
                else{
                    liveData.value = null
                    Log.d(tag, "데이터 실패 : ${liveData.value}")
                }
            }
        })
        return liveData
    }
}