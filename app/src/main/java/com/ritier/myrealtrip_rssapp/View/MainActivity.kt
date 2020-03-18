package com.ritier.myrealtrip_rssapp.View

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.Model.Rss
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.databinding.ActivityMainBinding
import org.koin.android.ext.android.get
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.pgLoading.visibility = View.VISIBLE

        val api : NewsApi = get()

        api.getNewsItems().enqueue(object :  retrofit2.Callback<Rss> {
            override fun onFailure(call: Call<Rss>, t: Throwable) {
                Log.e(tag, "에러 : ${t.message}")
            }

            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
                binding.tvMain.text = response.body()?.channel?.newsItems.toString()
                binding.pgLoading.visibility = View.INVISIBLE
            }
        })
    }
}
