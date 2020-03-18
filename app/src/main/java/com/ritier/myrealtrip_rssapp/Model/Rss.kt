package com.ritier.myrealtrip_rssapp.Model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class Rss(
    @field:Element(
        name = "channel")
    @param:Element(name = "channel")
    val channel: Channel?
){
    constructor() : this(null)
}