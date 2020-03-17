package com.ritier.myrealtrip_rssapp.View

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var mainText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainText = findViewById(R.id.tv_main)

        val observable = NewsApi.getApi(this).getNewsItems()

        observable.apply {
            this.subscribeOn(Schedulers.io())
            this.observeOn(AndroidSchedulers.mainThread())
            this.subscribe { items ->
                mainText.text = items.toString()
            }
        }






    }
}
