package com.ritier.myrealtrip_rssapp.Api

import com.ritier.myrealtrip_rssapp.model.Rss
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {
    @GET("rss?hl=ko")
    //TODO : 아래 함수 안에서 Jsoup으로 파싱하는 거 고려
    fun getNewsItems() : Call<Rss>
}