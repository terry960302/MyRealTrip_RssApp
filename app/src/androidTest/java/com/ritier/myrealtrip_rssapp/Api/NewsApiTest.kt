package com.ritier.myrealtrip_rssapp.Api

import com.ritier.myrealtrip_rssapp.Model.NewsItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class NewsApiTest {

    lateinit var observable : Observable<List<NewsItem>>

    @Before
    fun setting(){
        observable = NewsApi.getApi().getNewsItems()
    }

    @Test
    fun getItems(){
        observable.apply {
            this.observeOn(Schedulers.io())
            this.subscribeOn(AndroidSchedulers.mainThread())
            this.subscribe {
                    items -> print(items.toString())
            }
        }
    }
}