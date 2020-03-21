package com.ritier.myrealtrip_rssapp

import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class KeywordSortTest {

    private var data = listOf<String>()
    private var result = listOf<String>()

    @Before
    fun setData(){
        data = listOf("..", "안녕", "하신가\"", "누.구.인.가?", "기,침", "안녕합니다", "aa.", "#asd")
        result = listOf("안녕", "하신가", "누구인가", "기침", "안녕합니다", "aa", "asd")
    }

    private fun stringSort(word  :String) : String?{
        val re = Regex("[^가-힣^A-Za-z0-9 ]")
        val result = re.replace(word, "")
        return if(result.isEmpty()){
            null
        }else{
            result
        }
    }

    @Test
    fun initSort(){

        val myResult = data.mapNotNull { item -> stringSort(item) }

        assertEquals(result, myResult)

    }

    @After
    fun finish(){
        println("키워드 분류 테스트를 마쳤습니다.")
    }
}