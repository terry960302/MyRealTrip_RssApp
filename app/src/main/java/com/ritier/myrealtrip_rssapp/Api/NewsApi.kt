package com.ritier.myrealtrip_rssapp.Api

import com.ritier.myrealtrip_rssapp.Model.Rss
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {
    @GET("rss")
    fun getNewsItems() : Call<Rss>
}