package com.ritier.myrealtrip_rssapp

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.test.platform.app.InstrumentationRegistry
import com.ritier.myrealtrip_rssapp.Api.NewsClient
import com.ritier.myrealtrip_rssapp.Repository.NewsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test

class NewsRepoTest {

    private lateinit var mockContext : Context
    private lateinit var mainScope : CoroutineScope

    @Before
    fun setData(){
        mockContext = InstrumentationRegistry.getInstrumentation().targetContext
        mainScope = CoroutineScope(Dispatchers.Main)
    }

    @Test
    fun getData() {
        val api = NewsClient.getInstance(mockContext)
        val repo = NewsRepository(api)

        mainScope.launch {
            repo.getNewsItems().observeForever {
                val isExist = it.size > 0
                assertEquals(true, isExist)
            }
        }
    }

    @After
    fun dispose(){
        mainScope.cancel()
    }
}