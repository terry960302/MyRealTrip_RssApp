@file:Suppress("DEPRECATION")

package com.ritier.myrealtrip_rssapp.Util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.ritier.myrealtrip_rssapp.model.NewsItem
import com.ritier.myrealtrip_rssapp.model.NewsListItem
import kotlinx.coroutines.*
import org.jsoup.Jsoup

class Utils {

    companion object{
        fun hasNetwork(context: Context): Boolean {
            var isConnected = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }

        suspend fun resBodyToModel(newsItem: NewsItem) : NewsListItem = withContext(Dispatchers.IO){
            //TODO : 네트워크 불안정함. 에러 핸들링 필요 + 속도 저하 너무 심함
            val doc = Jsoup.connect(newsItem.link).get()
            val imagePath = doc.select("meta[property=og:image]").attr("content")
            val allDesc = doc.select("div.text").text()

            val keywordMap = mutableMapOf<String, Int>()
            val descList = allDesc.split(" ")
            descList.forEach {
                if(it in keywordMap){
                    val value = keywordMap[it]!! +1
                    keywordMap[it] = value
                }else{
                    keywordMap[it] = 1
                }
            }
            val keywordList = if(keywordMap.keys.size >= 3){
                keywordMap.toSortedMap(compareBy { it }).keys.toMutableList().subList(0, 3)
            }else{
                keywordMap.toSortedMap(compareBy { it }).keys.toMutableList()
            }

            return@withContext NewsListItem(imagePath, newsItem.title, allDesc, keywordList)
        }
    }
}