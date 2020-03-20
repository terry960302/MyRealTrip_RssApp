@file:Suppress("DEPRECATION")

package com.ritier.myrealtrip_rssapp.Util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ritier.myrealtrip_rssapp.model.NewsItem
import com.ritier.myrealtrip_rssapp.model.NewsListItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Utils {

    companion object {
        fun hasNetwork(context: Context): Boolean {
            var isConnected = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }

        //        //비동기 함수
//        suspend fun resBodyToModel(newsItem: NewsItem): NewsListItem = withContext(Dispatchers.IO) {
//            //TODO : 네트워크 불안정함. 에러 핸들링 필요 + 속도 저하 너무 심함
//            var doc: Document? = null
//            try {
//                doc = Jsoup.connect(newsItem.link).method(Connection.Method.GET).get()
//            } catch (e: IOException) {
//                Log.e("Util.resBodyToModel", "에러 : ${e.message}")
//            }
//
//            if (doc != null) {
//                val imagePath = doc.select("meta[property=og:image]").attr("content")
//                val allDesc = doc.select("meta[property=og:description]").attr("content")
//
//                val keywordMap = hashMapOf<String, Int>()
//                val descList = allDesc.split(" ")
//                descList.forEach {
//                    val keyword = it.trim()
//                    if (keyword in keywordMap) {
//                        val value = keywordMap[keyword]!! + 1
//                        keywordMap[keyword] = value
//                    } else {
//                        keywordMap[keyword] = 1
//                    }
//                }
//                val keywordList = if (keywordMap.keys.size >= 3) {
//                    keywordMap.toSortedMap(compareBy { it }).keys.toMutableList().subList(0, 3)
//                } else {
//                    keywordMap.toSortedMap(compareBy { it }).keys.toMutableList()
//                }
//
//                return@withContext NewsListItem(imagePath, newsItem.title, allDesc, keywordList)
//            } else {
//                return@withContext NewsListItem("", "", "", mutableListOf())
//            }
//
//        }

        //https://sourcediving.com/kotlin-coroutines-in-android-e2d5bb02c275
        suspend fun resBodyToModel(newsItem: NewsItem): NewsListItem {
            //TODO : 네트워크 불안정함. 에러 핸들링 필요 + 속도 저하 너무 심함
            return suspendCoroutine { continuation ->
                var doc: Document? = null
                try {
                    doc = Jsoup.connect(newsItem.link).maxBodySize(0).get()
                } catch (e: IOException) {
                    Log.e("Util.resBodyToModel", "에러 : ${e.message}")
                    e.printStackTrace()
                    continuation.resumeWithException(e)
                }

                if (doc != null) {
                    val imagePath = doc.select("meta[property=og:image]").attr("content")
                    val allDesc = doc.select("meta[property=og:description]").attr("content")

                    val keywordMap = hashMapOf<String, Int>()
                    val descList = allDesc.split(" ")
                    descList.forEach {
                        val keyword = it.trim()
                        if (keyword in keywordMap) {
                            val value = keywordMap[keyword]!! + 1
                            keywordMap[keyword] = value
                        } else {
                            keywordMap[keyword] = 1
                        }
                    }
                    val keywordList = if (keywordMap.keys.size >= 3) {
                        keywordMap.toSortedMap(compareBy { it }).keys.toMutableList().subList(0, 3)
                    } else {
                        keywordMap.toSortedMap(compareBy { it }).keys.toMutableList()
                    }

                    continuation.resume(
                        NewsListItem(
                            imagePath,
                            newsItem.title,
                            allDesc,
                            keywordList
                        )
                    )
                }
            }


        }
    }

    object GlidePlaceHolder {
        fun circularPlaceHolder(context: Context?): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context!!)
            circularProgressDrawable.centerRadius = 20f
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.start()
            return circularProgressDrawable
        }
    }
}