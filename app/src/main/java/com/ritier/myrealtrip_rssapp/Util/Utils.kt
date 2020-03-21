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
        suspend fun resBodyToModel(newsItem: NewsItem, onComplete: (NewsListItem) -> Unit) {
            // TODO : 네트워크 불안정함. 에러 핸들링 필요 + 속도 저하 너무 심함(Jsoup이 Blocking하므로 비동기로 작동필요)
            return suspendCoroutine { continuation ->
                try {
                    val doc = Jsoup.connect(newsItem.link).ignoreHttpErrors(true)
                        .execute().header("Connection", "close")
                        .parse()
                    val imagePath = doc.select("meta[property=og:image]").attr("content")
                    val allDesc = doc.select("meta[property=og:description]").attr("content")
                    val keywordList = extractKeywords(allDesc)

                    continuation.resume(
                        onComplete(
                            NewsListItem(
                                imagePath,
                                newsItem.title,
                                newsItem.link,
                                allDesc,
                                keywordList
                            )
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    continuation.resumeWithException(e)
                }
            }
        }

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