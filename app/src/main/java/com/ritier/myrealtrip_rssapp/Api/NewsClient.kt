@file:Suppress("DEPRECATION")

package com.ritier.myrealtrip_rssapp.Api

import android.content.Context
import com.ritier.myrealtrip_rssapp.Util.hasNetwork
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NewsClient {

    companion object{

        private const val baseUrl = "https://news.google.com/"

        fun getInstance(context : Context): NewsApi {

            val cacheSize = (5 * 1024 * 1024).toLong()
            val myCache = Cache(context.cacheDir, cacheSize)

            val okHttpClient =
                OkHttpClient.Builder()
                    .cache(myCache)
                    .addInterceptor { chain ->
                        var request = chain.request()

                        request = if (hasNetwork(context))
                        /*
                        *  If there is Internet, get the cache that was stored 5 seconds ago.
                        *  If the cache is older than 5 seconds, then discard it,
                        *  and indicate an error in fetching the response.
                        *  The 'max-age' attribute is responsible for this behavior.
                        */
                            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                        else
                        /*
                        *  If there is no Internet, get the cache that was stored 7 days ago.
                        *  If the cache is older than 7 days, then discard it,
                        *  and indicate an error in fetching the response.
                        *  The 'max-stale' attribute is responsible for this behavior.
                        *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                        */
                            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                        chain.proceed(request)
                    }
                    .build()

            return Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    SimpleXmlConverterFactory.createNonStrict(
                        Persister(AnnotationStrategy())
                    )
                )
                .build()
                .create(NewsApi::class.java)
        }
    }

}