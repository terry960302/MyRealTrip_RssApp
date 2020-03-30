package com.ritier.myrealtrip_rssapp

import androidx.test.platform.app.InstrumentationRegistry
import com.ritier.myrealtrip_rssapp.Api.NewsClient
import com.ritier.myrealtrip_rssapp.model.Rss
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class NewsApiTest {

    private lateinit var api: Observable<Rss>
    private val mockContext = InstrumentationRegistry.getInstrumentation().targetContext
    var isExist = false

    @Before
    fun setting() {
        api = NewsClient.getInstance(
            mockContext
        ).getNewsItems()
    }

    @Test
    fun getItems() {

        api.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            val data = it.channel?.newsItems

            if (data != null) {
                isExist = true
            }

            assertEquals(isExist, true)
        }
    }
}