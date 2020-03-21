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
        //TODO : splash_Screen에서 미리 네트워크 검사하는거 필요
        fun hasNetwork(context: Context): Boolean {
            var isConnected = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }


        //https://sourcediving.com/kotlin-coroutines-in-android-e2d5bb02c275
        suspend fun resBodyToModel(newsItem: NewsItem): NewsListItem {
            // TODO : 네트워크 불안정함. 에러 핸들링 필요 + 속도 저하 너무 심함
            // 시간 체크 결과 => 13~23초(네트워크 상황에 따라 상이) / rss 부르는거 1~2초 감안해도 많이 느림, 개선 필요
            return suspendCoroutine { continuation ->
                var doc: Document? = null
                try {
                    doc = Jsoup.connect(newsItem.link).maxBodySize(0).get() ?: throw error("http fetching error")
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
                        if(keyword.contains("\""))
                        if(!keyword.isBlank()){
                            if (keyword in keywordMap) {
                                val value = keywordMap[keyword]!! + 1
                                keywordMap[keyword] = value
                            } else {
                                keywordMap[keyword] = 1
                            }
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
                            newsItem.link,
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