package com.ritier.myrealtrip_rssapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritier.myrealtrip_rssapp.Repository.NewsRepository
import com.ritier.myrealtrip_rssapp.model.NewsListItem
import kotlinx.coroutines.async

class NewsViewModel(private val newsRepo: NewsRepository) : ViewModel() {

    fun getNewsItems(): MutableLiveData<MutableList<NewsListItem>> = newsRepo.getNewsItems()
}