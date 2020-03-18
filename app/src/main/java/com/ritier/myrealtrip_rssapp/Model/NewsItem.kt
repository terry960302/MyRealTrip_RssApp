package com.ritier.myrealtrip_rssapp.Model

import org.simpleframework.xml.Element

@Element(name = "item")
data class NewsItem(
    @Element(name = "title")
    val title: String,
    @Element(name = "link")
    val link: String,
    @Element(name = "guid")
    val id: String,
    @Element(name = "pubDate")
    val date: String,
    @Element(name = "description")
    val desc: String,
    @Element(name = "source")
    val srcUrl: String
) {
    constructor() : this("", "", "", "", "", "")

}