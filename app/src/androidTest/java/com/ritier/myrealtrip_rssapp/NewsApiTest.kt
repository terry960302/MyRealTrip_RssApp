package com.ritier.myrealtrip_rssapp

import androidx.test.platform.app.InstrumentationRegistry
import com.ritier.myrealtrip_rssapp.Api.NewsClient
import com.ritier.myrealtrip_rssapp.model.Rss
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class NewsApiTest  {

    private lateinit var api : Call<Rss>
    private val mockContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setting(){
        api = NewsClient.getInstance(
            mockContext
        ).getNewsItems()
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