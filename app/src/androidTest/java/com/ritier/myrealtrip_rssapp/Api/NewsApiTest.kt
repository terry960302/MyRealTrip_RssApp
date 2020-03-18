package com.ritier.myrealtrip_rssapp.Api

import com.ritier.myrealtrip_rssapp.Model.Rss
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class NewsApiTest  {

    private lateinit var api : Call<Rss>

    @Before
    fun setting(){
        api = NewsClient.getInstance().getNewsItems()
    }

    @Test
    fun getItems(){

        api.enqueue(object: retrofit2.Callback<Rss>{
            override fun onFailure(call: Call<Rss>, t: Throwable) {
                print("에러입니다. : ${t.message}")
            }

            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
                val body = response.body()

                if(body != null){
                    val channel = body.channel
                    if(channel != null){
                        println("결과 : ${channel.newsItems}")
                    }
                }

            }

        })
    }
}