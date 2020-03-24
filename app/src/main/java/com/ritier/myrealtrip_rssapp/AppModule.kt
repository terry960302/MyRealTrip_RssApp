package com.ritier.myrealtrip_rssapp

import com.ritier.myrealtrip_rssapp.Api.NewsClient
import com.ritier.myrealtrip_rssapp.Repository.NewsRepository
import com.ritier.myrealtrip_rssapp.ViewModel.NewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        NewsClient.getInstance(this.androidContext())
    }
    single {
        NewsRepository(get())
    }

    viewModel { NewsViewModel(get()) }
}