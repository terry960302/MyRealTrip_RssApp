package com.ritier.myrealtrip_rssapp.Model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Element(name = "channel")
data class Channel(
    @Element(name = "generator")
    val generator: String,
    @Element(name = "title")
    val title: String,
    @Element(name = "link")
    val link: String,
    @Element(name = "language")
    val language: String,
    @Element(name = "webMaster")
    val webMaster: String,
    @Element(name = "copyright")
    val copyright: String,
    @Element(name = "lastBuildDate")
    val lastBuildDate: String,
    @Element(name = "description")
    val description: String,
    @ElementList
    val news: List<NewsItem>
) {
    constructor() : this("", "", "", "", "", "", "", "", listOf())
}