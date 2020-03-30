@file:Suppress("DEPRECATION")

package com.ritier.myrealtrip_rssapp.View

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.View.Adapter.NewsAdapter
import com.ritier.myrealtrip_rssapp.ViewModel.NewsViewModel
import com.ritier.myrealtrip_rssapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tag = "MainActivity"
    private lateinit var newsAdapter: NewsAdapter
    private val newsViewModel by viewModel<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setLottieLoading()
        initRecyclerView()
        setSwipeRefresh()
        getNewsData()
    }

    private fun setSwipeRefresh() {
        binding.srlMain.setOnRefreshListener {
            newsAdapter.clearItems()
            getNewsData()
        }
    }

    private fun setLottieLoading(){
        binding.lvLoading.apply{
            this.setAnimation("newspaper_spinner.json")
            this.playAnimation()
            this.loop(true)
        }
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvMain.apply {
            this.setHasFixedSize(true)
            this.layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            this.adapter = newsAdapter
        }
    }

    private fun getNewsData() {
        binding.cvWrapLottie.visibility = View.VISIBLE
        newsViewModel.getNewsItems()
            .observe(this, Observer {
                if (it == null) {
                    binding.cvWrapLottie.visibility = View.GONE
                    binding.srlMain.isRefreshing = false
                    Log.d(tag, "데이터가 없습니다.")
                    Toast.makeText(applicationContext, "네트워크 상태를 확인해주십시오.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    newsAdapter.setItems(it)
                    binding.cvWrapLottie.visibility = View.GONE
                    binding.srlMain.isRefreshing = false
                }
            })
    }

}
