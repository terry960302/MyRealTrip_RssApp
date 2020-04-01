@file:Suppress("DEPRECATION")

package com.ritier.myrealtrip_rssapp.Util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ritier.myrealtrip_rssapp.model.NewsItem
import com.ritier.myrealtrip_rssapp.model.NewsListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import kotlin.collections.set


fun hasNetwork(context: Context): Boolean {
    var isConnected = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

suspend fun mappingModel(newsItem: NewsItem): NewsListItem = withContext(Dispatchers.IO) {
    try {
        val doc = Jsoup.connect(newsItem.link).ignoreHttpErrors(true)
            .execute().header("Connection", "close")
            .parse()

        val imagePath = doc.select("meta[property=og:image]").attr("content")
        val allDesc = doc.select("meta[property=og:description]").attr("content")
        val keywordList = extractKeywords(allDesc)

        return@withContext NewsListItem(
            if (imagePath.trim().isNotEmpty()) imagePath else null,
            newsItem.title,
            newsItem.link,
            if (allDesc.trim().isNotEmpty()) allDesc else "원문을 확인해주십시오.",
            keywordList
        )
    } catch (e: Exception) {
        e.printStackTrace()
        return@withContext NewsListItem(null, "오류입니다.", "", "", mutableListOf())
    }
}

//키워드 분류 알고리즘 함수 2개
private fun stringSort(word: String): String? {
    val re = Regex("[^가-힣^A-Za-z0-9 ]")
    val result = re.replace(word, "")
    return if (result.isEmpty()) {
        null
    } else {
        result
    }
}

private fun extractKeywords(allDesc: String): MutableList<String> {
    val keywordMap = hashMapOf<String, Int>()
    val descList = allDesc.split(" ").mapNotNull { word -> stringSort(word) }
    Log.d("Utils", "키워드들 : $descList")
    descList.forEach {
        val keyword = it.trim()
        if (keyword in keywordMap) {
            val value = keywordMap[keyword]!! + 1
            keywordMap[keyword] = value
        } else {
            keywordMap[keyword] = 1
        }
    }
    return if (keywordMap.keys.size >= 3) {
        keywordMap.toSortedMap(compareBy { it }).keys.toMutableList().subList(0, 3)
    } else {
        keywordMap.toSortedMap(compareBy { it }).keys.toMutableList()
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