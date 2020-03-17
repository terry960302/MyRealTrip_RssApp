package com.ritier.myrealtrip_rssapp.Model

data class NewsItem(
    val thumbnailUrl: String,
    val title: String,
    val desc: String,
    val keywords: MutableList<String>
) {
    constructor() : this("", "", "", mutableListOf())
}