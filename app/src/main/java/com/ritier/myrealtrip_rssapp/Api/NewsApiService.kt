package com.ritier.myrealtrip_rssapp.Api

import com.ritier.myrealtrip_rssapp.Model.Channel
import com.ritier.myrealtrip_rssapp.Model.NewsItem
import com.ritier.myrealtrip_rssapp.Model.Rss
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface NewsApiService {
    @GET("rss")
    fun getNewsItems() : Call<Rss>
}