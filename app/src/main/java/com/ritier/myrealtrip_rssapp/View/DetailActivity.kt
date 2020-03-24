package com.ritier.myrealtrip_rssapp.View

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
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
    companion object{
        const val tag = "DetailActivity"
    }
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
            this.webViewClient = object : WebViewClient(){
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    view?.visibility = View.INVISIBLE
                    binding.pgLoading.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    view?.visibility = View.VISIBLE
                    binding.pgLoading.visibility = View.INVISIBLE
                }

                //TODO : 웹뷰 에러 핸들링 더 철저하게
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    view?.visibility = View.INVISIBLE
                    binding.pgLoading.visibility = View.INVISIBLE
                    Log.e(DetailActivity.tag, "웹뷰 에러 : ${error.toString()}")
//                    Toast.makeText(applicationContext, "페이지 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    Log.e(DetailActivity.tag, "웹뷰 http 에러 : ${errorResponse.toString()}")
                }
            }
            this.webChromeClient = object : WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)

                    if(progress < 100){
                        binding.pgLoading.visibility = View.VISIBLE
                    }
                    else if(progress == 100){
                        binding.pgLoading.visibility = View.GONE
                    }
                }
            }
            this.settings.apply {
                this.javaScriptEnabled = true
                this.setSupportMultipleWindows(false)
                this.setSupportZoom(true)
                this.domStorageEnabled = true
            }
            this.loadUrl(item.url)
        }
    }
}
