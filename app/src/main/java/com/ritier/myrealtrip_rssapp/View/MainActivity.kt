@file:Suppress("DEPRECATION")

package com.ritier.myrealtrip_rssapp.View

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.Util.mappingModel
import com.ritier.myrealtrip_rssapp.View.Adapter.NewsAdapter
import com.ritier.myrealtrip_rssapp.ViewModel.NewsViewModel
import com.ritier.myrealtrip_rssapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val tag = "MainActivity"
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var dialog: ProgressDialog
    private val newsViewModel by viewModel<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setLoadingDialog()
        initRecyclerView()
        setSwipeRefresh()
        getData()
    }

    private fun setSwipeRefresh() {
        binding.srlMain.setOnRefreshListener {
            dialog.show()
            newsAdapter.clearItems()
            getData()
        }
    }

    private fun setLoadingDialog() {
        dialog = ProgressDialog(this)
        dialog.setMessage("뉴스를 불러오고 있습니다...")
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvMain.apply {
            this.layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            this.adapter = newsAdapter
        }
    }

    private fun getData() {

        //TODO : 화면 회전시 다시 불러오는 문제 해결 필요
        newsViewModel.getNewsItems().observe(this, Observer {
            dialog.show()
            val job = Job()
            val coroutineScope = CoroutineScope(Dispatchers.IO + job)
            coroutineScope.launch {
                it?.forEach {
                    val item = mappingModel(it)
                    runOnUiThread {
                        newsAdapter.addItem(item)
                    }
                }
                dialog.dismiss()
                binding.srlMain.isRefreshing = false
            }
        })

//        dialog.show()
//
//
//        val api : NewsApi = get()
//
//        api.getNewsItems().enqueue(object : retrofit2.Callback<Rss>{
//
//            override fun onFailure(call: Call<Rss>, t: Throwable) {
//                Log.e(tag, "에러  : ${t.message}")
//            }
//
//            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
//                val itemList = response.body()?.channel?.newsItems
//
//                val mainScope = CoroutineScope(Dispatchers.IO)
//                mainScope.launch {
//                    val result = itemList?.map {
//                        mappingModel(it)
//                    }?.toMutableList()
//                    runOnUiThread {
//                        newsAdapter.setItems(result!!)
//                        dialog.dismiss()
//                        binding.srlMain.isRefreshing = false
//                    }
//                }
//            }
//
//        })
    }

}
