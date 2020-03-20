package com.ritier.myrealtrip_rssapp.View

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.Util.Utils
import com.ritier.myrealtrip_rssapp.View.Adapter.NewsAdapter
import com.ritier.myrealtrip_rssapp.databinding.ActivityMainBinding
import com.ritier.myrealtrip_rssapp.model.NewsListItem
import com.ritier.myrealtrip_rssapp.model.Rss
import kotlinx.coroutines.*
import org.koin.android.ext.android.get
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    val tag = "MainActivity"
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initRecyclerView()

        binding.pgLoading.visibility = View.VISIBLE

        binding.srlMain.setOnRefreshListener {
            binding.pgLoading.visibility = View.VISIBLE
            newsAdapter.clearItems()
            getData()
            binding.srlMain.isRefreshing = false
        }
        getData()
    }

    private fun initRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.rvMain.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            this.adapter = newsAdapter
        }
    }

    private fun getData(){
        val newsApi : NewsApi = get()

        newsApi.getNewsItems().enqueue(object :  retrofit2.Callback<Rss> {
            override fun onFailure(call: Call<Rss>, t: Throwable) {
                Log.e(tag, "에러 : ${t.message}")
            }

            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
                val itemList= response.body()?.channel?.newsItems

                val mainScope = CoroutineScope(Dispatchers.Main + Job())

                mainScope.launch {
                    var items:List<NewsListItem>? = listOf<NewsListItem>()
                    withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                        items = itemList?.map { items -> Utils.resBodyToModel(items) }
                    }
                    newsAdapter.setItems(items!!)
                    binding.pgLoading.visibility = View.INVISIBLE

                }

            }
        })
    }

}
