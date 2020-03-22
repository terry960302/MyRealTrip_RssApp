package com.ritier.myrealtrip_rssapp

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.ritier.myrealtrip_rssapp.Api.NewsClient
import com.ritier.myrealtrip_rssapp.Repository.NewsRepository
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RepoTest {

    @Mock
    lateinit var mockContext : Context
    lateinit var lifecycleOwner: LifecycleOwner

    @Before
    fun setData(){
        lifecycleOwner = Mockito.mock(LifecycleOwner::class.java)
        val lifecycle = LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java))
        lifecycle.markState(Lifecycle.State.RESUMED)
        Mockito.`when`(lifecycleOwner.lifecycle).thenReturn(lifecycle)
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getData() {
        val api = NewsClient.getInstance(mockContext)
        val repo = NewsRepository(api)

        repo.getNewsItems().observe(lifecycleOwner, Observer {
            assertEquals("샘플 결과 데이터", it)

        })
    }
}