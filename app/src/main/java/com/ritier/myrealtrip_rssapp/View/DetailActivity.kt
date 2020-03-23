package com.ritier.myrealtrip_rssapp.View

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.View.Adapter.KeywordAdapter
import com.ritier.myrealtrip_rssapp.databinding.ActivityDetailBinding
import com.ritier.myrealtrip_rssapp.model.NewsListItem

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var keywordAdapter: KeywordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this

        val intent = intent
        val item = intent.extras?.getParcelable<NewsListItem>("newsListItem") as NewsListItem

        if(item == NewsListItem(null, "", "", "", mutableListOf())){
            binding.tvTitle.text = "오류입니다."
        }

        initRecyclerView(item.keywords)
        binding.tvTitle.text = item.title
        setWebView(item)
    }

    private fun initRecyclerView(list : MutableList<String>){
        keywordAdapter = KeywordAdapter()
        keywordAdapter.setItems(list)
        binding.rvKeywords.apply {
            this.layoutManager = LinearLayoutManager(this@DetailActivity, RecyclerView.HORIZONTAL, false)
            this.adapter = keywordAdapter
        }
    }

    private fun setWebView(item : NewsListItem){
        binding.wvDetail.apply {
            this.webViewClient = WebViewClient()
            this.settings.apply {
                this.javaScriptEnabled = true
                this.setSupportMultipleWindows(false)
                this.setSupportZoom(true)
            }
            this.loadUrl(item.url)
        }
    }
}
