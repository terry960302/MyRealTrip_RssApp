package com.ritier.myrealtrip_rssapp

import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.regex.Pattern

class KeywordSortTest {

    private var data = listOf<String>()
    private var result = listOf<String>()

    @Before
    fun setData(){
        data = listOf("..", "안녕", "하신가\"", "누.구.인.가?", "기,침", "안녕합니다")
        result = listOf("안녕", "하신가", "누구인가", "기침", "안녕합니다")
    }

    @Test
    fun initSort(){

//        val regex = """"^[ㄱ-ㅎ가-힣]*\$""" // 한글
//        val regex = """[ㄱ-ㅎ가-힣]\W+""" //한글이랑 기호
//        val regex = """\w+""" //기호만
        val regex = """\W+""" //기호를 포함한 글자들
        val boolResult = Pattern.matches(regex, "ㅁㅁㅁㅁㄹㄴㅁㄴ.")

        data.map { word -> Pattern.matches(regex, word) }

        assertEquals(true, boolResult)

    }

    @After
    fun finish(){
        println("키워드 분류 테스트를 마쳤습니다.")
    }
}