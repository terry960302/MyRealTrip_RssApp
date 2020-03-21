package com.ritier.myrealtrip_rssapp.model

import android.content.Intent
import android.view.View
import com.ritier.myrealtrip_rssapp.View.DetailActivity

data class NewsListItem(
    val thumbnailUrl: String,
    val title: String,
    val url : String,
    val desc: String,
    val keywords: MutableList<String>
) {
    constructor() : this("", "", "","",  mutableListOf())

    fun onClickListener(view :  View){
        val intent = Intent(view.context, DetailActivity::class.java)
        intent.putExtra("url", this.url)
        view.context.startActivity(intent)
    }
}