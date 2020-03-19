package com.ritier.myrealtrip_rssapp.model

data class NewsListItem(
    val thumbnailUrl: String,
    val title: String,
    val desc: String,
    val keywords: MutableList<String>
) {
    constructor() : this("", "", "", mutableListOf())
}