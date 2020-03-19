package com.ritier.myrealtrip_rssapp

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class keywordFetchTest {

    private lateinit var list  : List<String>

    @Before
    fun setData(){
        list = listOf("사과", "바나나", "사과를", "바나나", "바나나는", "고래", "고래")
    }

    @Test
    fun sortByCount(){
        val resultMap = mutableMapOf<String, Int>()
        list.forEach {
            if(it in resultMap){
                val value = resultMap[it]!! +1
                resultMap[it] = value
            }else{
                resultMap[it] = 1
            }
        }
        val sortedList = resultMap.toSortedMap(compareBy { it }).keys.toList().subList(0, 3)

        assertEquals(listOf("고래", "바나나", "바나나는"), sortedList)
    }
}