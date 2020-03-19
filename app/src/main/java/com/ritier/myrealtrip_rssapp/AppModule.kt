package com.ritier.myrealtrip_rssapp

import com.ritier.myrealtrip_rssapp.Api.NewsApi
import com.ritier.myrealtrip_rssapp.Api.NewsClient
import com.ritier.myrealtrip_rssapp.Repository.NewsRepository
import com.ritier.myrealtrip_rssapp.ViewModel.NewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//TODO : 아래 참고(koin injection)
//https://github.com/InsertKoinIO/getting-started-koin-android/tree/master/app/src/main/kotlin/org/koin/sample

val appModule = module {

    single<NewsApi> {
        NewsClient.getInstance(this.androidContext())
    }

    single<NewsRepository> {
        NewsRepository(get())
    }

    viewModel { NewsViewModel(get()) }
}