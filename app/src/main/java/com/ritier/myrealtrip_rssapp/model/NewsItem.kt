package com.ritier.myrealtrip_rssapp.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item")
data class NewsItem(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String,

    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String,

    @field:Element(name = "guid")
    @param:Element(name = "guid")
    val id: String,

    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    val date: String,

    @field:Element(name = "description")
    @param:Element(name = "description")
    val desc: String,

    @field:Element(name = "source")
    @param:Element(name = "source")
    val src: String
) {
    constructor() : this("", "", "", "", "", "")

}