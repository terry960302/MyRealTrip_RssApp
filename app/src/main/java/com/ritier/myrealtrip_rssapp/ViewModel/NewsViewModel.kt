package com.ritier.myrealtrip_rssapp.ViewModel

import androidx.lifecycle.ViewModel
import com.ritier.myrealtrip_rssapp.Repository.NewsRepository

class NewsViewModel(private val newsRepo : NewsRepository) : ViewModel() {

    fun getNewsItems(newsRepo: NewsRepository) = newsRepo.getNewsItems()
}