package com.ritier.myrealtrip_rssapp

import junit.framework.TestCase.assertEquals
import org.jsoup.Jsoup
import org.junit.Before
import org.junit.Test

class CrawlTest {

    private var url: String = ""

    @Before
    fun setData() {
        url =
            "https://news.google.com/__i/rss/rd/articles/CBMiOGh0dHA6Ly93d3cuaGFuaS5jby5rci9hcnRpL3BvbGl0aWNzL2Fzc2VtYmx5LzkzMzM4OS5odG1s0gEA?oc=5"
    }

    @Test
    fun getData() {

        val doc = Jsoup.connect(url).get()
        val imagePath = doc.select("meta[property=og:image]").attr("content")
        val allDesc = doc.select("meta[property=og:description]").attr("content")

        assertEquals(
            "http://flexible.img.hani.co.kr/flexible/normal/970/610/imgdb/original/2020/0319/20200319504139.jpg",
            imagePath
        )

    }
}