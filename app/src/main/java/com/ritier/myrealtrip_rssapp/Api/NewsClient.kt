@file:Suppress("DEPRECATION")

package com.ritier.myrealtrip_rssapp.Api

import okhttp3.Cache
import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NewsClient {

    companion object{
        //TODO : 레트로핏 캐싱
        // https://medium.com/@bapspatil/caching-with-retrofit-store-responses-offline-71439ed32fda
//        fun hasNetwork(context: Context): Boolean? {
//            var isConnected: Boolean? = false // Initial Value
//            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
//            if (activeNetwork != null && activeNetwork.isConnected)
//                isConnected = true
//            return isConnected
//        }

        private const val baseUrl = "https://news.google.com/"

        fun getInstance(): NewsApi {

//            val cacheSize = (5 x 1024 x 1024).toLong()
//            val myCache = Cache(context.cacheDir, cacheSize)


            val client =
                OkHttpClient.Builder()
//                    .cache(myCache)
//                    // Add an Interceptor to the OkHttpClient.
//                    .addInterceptor { chain ->
//
//                        // Get the request from the chain.
//                        var request = chain.request()
//
//                        /*
//                        *  Leveraging the advantage of using Kotlin,
//                        *  we initialize the request and change its header depending on whether
//                        *  the device is connected to Internet or not.
//                        */
//                        request = if (hasNetwork(context)!!)
//                        /*
//                        *  If there is Internet, get the cache that was stored 5 seconds ago.
//                        *  If the cache is older than 5 seconds, then discard it,
//                        *  and indicate an error in fetching the response.
//                        *  The 'max-age' attribute is responsible for this behavior.
//                        */
//                            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
//                        else
//                        /*
//                        *  If there is no Internet, get the cache that was stored 7 days ago.
//                        *  If the cache is older than 7 days, then discard it,
//                        *  and indicate an error in fetching the response.
//                        *  The 'max-stale' attribute is responsible for this behavior.
//                        *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
//                        */
//                            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
//                        // End of if-else statement
//
//                        // Add the modified request to the chain.
//                        chain.proceed(request)
//                    }
                    .build()

            return Retrofit.Builder().baseUrl(baseUrl).client(client)
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