package com.example.warehouseandroid

import com.example.warehouseandroid.contractor.remote.ContractorRemoteDataSource
import com.example.warehouseandroid.contractor.remote.ContractorRemoteRepository
import com.example.warehouseandroid.dashboard.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single {
        Retrofit
            .Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single<ContractorRemoteDataSource> { ContractorRemoteRepository(get()) }
}

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }
}