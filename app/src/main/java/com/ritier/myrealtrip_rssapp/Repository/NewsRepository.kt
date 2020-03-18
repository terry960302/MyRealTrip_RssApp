package com.ritier.myrealtrip_rssapp.Repository

import android.util.Log
import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.Model.NewsItem
import com.ritier.myrealtrip_rssapp.Model.Rss
import retrofit2.Call
import retrofit2.Response

class NewsRepository(private val newsApi : NewsApi) {

    val tag = "NewsRepository"

    fun getNewsItems() : List<NewsItem>?{
        //TODO : 라이브데이터로 처리하기
        var result : List<NewsItem>? = null
        newsApi.getNewsItems().enqueue(object : retrofit2.Callback<Rss>{
            override fun onFailure(call: Call<Rss>, t: Throwable) {
                Log.e(tag, "retrofit error : ${t.message}")
            }

            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
                result =  response.body()?.channel?.newsItems
            }
        })
        return result
    }
}