@file:Suppress("DEPRECATION")

package com.ritier.myrealtrip_rssapp.View

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.R
import com.ritier.myrealtrip_rssapp.Util.Utils
import com.ritier.myrealtrip_rssapp.View.Adapter.NewsAdapter
import com.ritier.myrealtrip_rssapp.databinding.ActivityMainBinding
import com.ritier.myrealtrip_rssapp.model.Rss
import kotlinx.coroutines.*
import org.koin.android.ext.android.get
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    val tag = "MainActivity"
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var dialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setLoadingDialog()
        initRecyclerView()
        setSwipeRefresh()
        getData()
    }

    private fun setSwipeRefresh(){
        binding.srlMain.setOnRefreshListener {
            dialog.show()
            newsAdapter.clearItems()
            getData()
        }
    }

    private fun setLoadingDialog(){
        dialog = ProgressDialog(this)
        dialog.setMessage("뉴스를 불러오고 있습니다...")
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun initRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.rvMain.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            this.adapter = newsAdapter
        }
    }

    private fun getData(){
        dialog.show()

        val newsApi : NewsApi = get()

        newsApi.getNewsItems().enqueue(object :  retrofit2.Callback<Rss> {
            override fun onFailure(call: Call<Rss>, t: Throwable) {
                Log.e(tag, "에러 : ${t.message}")
            }

            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
                val itemList= response.body()?.channel?.newsItems

                //비동기 함수를 실행할 때는 다시 코루틴으로 감싸줌.
                //https://stackoverflow.com/questions/58658630/parallel-request-with-retrofit-coroutines-and-suspend-functions
                val mainScope = CoroutineScope(Dispatchers.IO + Job())
                mainScope.launch {
                    try{
                        withContext(Dispatchers.IO) {
                            itemList?.forEach { item ->
                                Utils.resBodyToModel(item) {
                                    runOnUiThread {
                                        newsAdapter.addItem(it)
                                    }
                                }
                            }
                        }
                        dialog.dismiss()
                        binding.srlMain.isRefreshing = false
                    }catch (e : Exception){
                        Log.e(tag, e.message.toString())
                        e.printStackTrace()
                        dialog.dismiss()
                        binding.srlMain.isRefreshing = false
                    }
                }

            }
        })
    }

}
