package com.ritier.myrealtrip_rssapp.Model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel")
data class Channel(
    @field:Element(name = "generator")
    @param:Element(name = "generator")
    val generator: String,

    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String,

    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String,

    @field:Element(name = "language")
    @param:Element(name = "language")
    val language: String,

    @field:Element(name = "webMaster")
    @param:Element(name = "webMaster")
    val webMaster: String,

    @field:Element(name = "copyright")
    @param:Element(name = "copyright")
    val copyright: String,

    @field:Element(name = "lastBuildDate")
    @param:Element(name = "lastBuildDate")
    val lastBuildDate: String,

    @field:Element(name = "description")
    @param:Element(name = "description")
    val description: String,

    @field:ElementList(name= "item", inline = true)
    @param:ElementList(name= "item", inline = true)
    val newsItems: List<NewsItem>
) {
    constructor() : this("", "", "", "", "", "", "", "", listOf())
}