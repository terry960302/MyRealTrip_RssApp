package com.ritier.myrealtrip_rssapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritier.myrealtrip_rssapp.Repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepo : NewsRepository) : ViewModel() {

    //https://developer.android.com/topic/libraries/architecture/coroutines
    fun getNewsItems(newsRepo: NewsRepository){
        viewModelScope.launch {

        }
    }
}