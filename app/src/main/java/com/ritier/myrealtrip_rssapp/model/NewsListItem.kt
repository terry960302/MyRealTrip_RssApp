package com.ritier.myrealtrip_rssapp.model

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.ritier.myrealtrip_rssapp.View.DetailActivity
import kotlinx.android.parcel.Parcelize


@Parcelize
data class NewsListItem(
    val thumbnailUrl: String?,
    val title: String,
    val url : String,
    val desc: String,
    val keywords: MutableList<String>
) : Parcelable {
    constructor() : this(null, "", "","",  mutableListOf())

    fun onClickListener(view :  View){
        val intent = Intent(view.context, DetailActivity::class.java)
        val news = NewsListItem(this.thumbnailUrl, this.title, this.url, this.desc, this.keywords)
        val bundle = Bundle()
        bundle.putParcelable("newsListItem", news)
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }
}