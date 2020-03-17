package com.ritier.myrealtrip_rssapp.Api

import com.ritier.myrealtrip_rssapp.Model.NewsItem
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsApiService {
    @GET("rss")
    fun getNewsItems() : io.reactivex.rxjava3.core.Observable<List<NewsItem>>
}