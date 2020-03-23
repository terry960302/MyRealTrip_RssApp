package com.ritier.myrealtrip_rssapp.Api

import com.ritier.myrealtrip_rssapp.model.Rss
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {
    @GET("rss?hl=ko")
    fun getNewsItems() : Observable<Rss>
}