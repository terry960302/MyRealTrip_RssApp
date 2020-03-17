package com.ritier.myrealtrip_rssapp.Api

import android.content.Context
import com.ritier.myrealtrip_rssapp.R
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NewsApi {
    companion object{
        fun getApi(context: Context): NewsApiService {
            val client =
                OkHttpClient.Builder().build()

            return Retrofit.Builder().baseUrl(context.getString(R.string.baseUrl)).client(client)
                .addConverterFactory(
                    SimpleXmlConverterFactory.createNonStrict(
                        Persister(AnnotationStrategy())
                    )
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(NewsApiService::class.java)
        }
    }

}