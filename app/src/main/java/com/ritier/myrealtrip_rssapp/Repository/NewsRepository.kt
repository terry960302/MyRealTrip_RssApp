package com.ritier.myrealtrip_rssapp.Repository

import android.util.Log
import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.Model.NewsItem
import com.ritier.myrealtrip_rssapp.Model.Rss
import retrofit2.Call
import retrofit2.Response

class NewsRepository {

    val tag = "NewsRepository"
    private var api : Call<Rss> = NewsApi.getApi().getNewsItems()

    fun getNewsItems() : List<NewsItem>?{
        var result : List<NewsItem>? = null
        api.enqueue(object : retrofit2.Callback<Rss>{
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