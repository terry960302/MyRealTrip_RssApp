package com.ritier.myrealtrip_rssapp.Model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class Rss(
    @Element(name = "channel")
    val channel : Channel

) {

}