package com.example.warehouseandroid

import com.example.warehouseandroid.contractor.ContractorDataSource
import com.example.warehouseandroid.contractor.ContractorRepository
import com.example.warehouseandroid.contractor.local.ContractorDao
import com.example.warehouseandroid.contractor.local.ContractorEntity
import com.example.warehouseandroid.contractor.local.ContractorLocalDataSource
import com.example.warehouseandroid.contractor.local.ContractorLocalRepository
import com.example.warehouseandroid.contractor.remote.ContractorRemoteDataSource
import com.example.warehouseandroid.contractor.remote.ContractorRemoteRepository
import com.example.warehouseandroid.contractoradd.viewmodel.ContractorAddViewModel
import com.example.warehouseandroid.contractordetails.viewmodel.ContractorDetailsViewModel
import com.example.warehouseandroid.contractoredit.viewmodel.ContractorEditViewModel
import com.example.warehouseandroid.contractorlist.viewmodel.ContractorListViewModel
import com.example.warehouseandroid.receiptdocument.ReceiptDocumentDataSource
import com.example.warehouseandroid.receiptdocument.ReceiptDocumentRepository
import com.example.warehouseandroid.receiptdocument.local.ReceiptDocumentDao
import com.example.warehouseandroid.receiptdocument.local.ReceiptDocumentEntity
import com.example.warehouseandroid.receiptdocument.local.ReceiptDocumentLocalDataSource
import com.example.warehouseandroid.receiptdocument.local.ReceiptDocumentLocalRepository
import com.example.warehouseandroid.receiptdocument.remote.ReceiptDocumentRemoteDataSource
import com.example.warehouseandroid.receiptdocument.remote.ReceiptDocumentRemoteRepository
import com.example.warehouseandroid.receiptdocumentlist.viewmodel.ReceiptDocumentListViewModel
import com.google.gson.Gson
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
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
            .build()
            .create(ApiService::class.java)
    }
}

val databaseModule = module {
    single {
        //todo
//        val schema = setOf(ContractorEntity::class, ReceiptDocumentEntity::class)
        val configuration = RealmConfiguration.create(
            schema = setOf(
                ContractorEntity::class,
                ReceiptDocumentEntity::class
            )
        )
        Realm.open(configuration)
    }
    single { ContractorDao(get()) }
    single { ReceiptDocumentDao(get()) }
    single { Gson() }
}

val repositoryModule = module {
    //todo
    single<ContractorRemoteDataSource> { ContractorRemoteRepository(get()) }
    single<ContractorLocalDataSource> { ContractorLocalRepository(get()) }
    single<ContractorDataSource> { ContractorRepository(get(), get()) }
    single<ReceiptDocumentRemoteDataSource> { ReceiptDocumentRemoteRepository(get()) }
    single<ReceiptDocumentLocalDataSource> { ReceiptDocumentLocalRepository(get()) }
    single<ReceiptDocumentDataSource> { ReceiptDocumentRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { ContractorListViewModel(get()) }
    viewModel { (contractorId: Long) -> ContractorDetailsViewModel(get(), get(), contractorId) }
    viewModel { ContractorAddViewModel(get()) }
    viewModel { (contractorJson: String) -> ContractorEditViewModel(get(), get(), contractorJson) }
    viewModel { ReceiptDocumentListViewModel(get()) }
}