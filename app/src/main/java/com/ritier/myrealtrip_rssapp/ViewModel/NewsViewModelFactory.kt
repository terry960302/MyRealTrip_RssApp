package com.ritier.myrealtrip_rssapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ritier.myrealtrip_rssapp.Repository.NewsRepository

class NewsViewModelFactory(private val repo : NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepo = repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}