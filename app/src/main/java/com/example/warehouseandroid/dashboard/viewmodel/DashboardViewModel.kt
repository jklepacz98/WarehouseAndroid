package com.example.warehouseandroid.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.contractor.remote.ContractorDto
import com.example.warehouseandroid.contractor.remote.ContractorRemoteDataSource
import com.example.warehouseandroid.util.ApiError
import com.example.warehouseandroid.util.ApiException
import com.example.warehouseandroid.util.ApiSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(val contractorRemoteDataSource: ContractorRemoteDataSource) : ViewModel() {

    val contractorList: MutableStateFlow<List<ContractorDto>> =
        MutableStateFlow(emptyList())

    init {
        getAllContractors()
    }


    fun getAllContractors(){
        viewModelScope.launch {
            contractorRemoteDataSource.getAllContractors().let {networkResult ->
                println("something12")
                when (networkResult){
                    is ApiSuccess -> {
                        contractorList.value = networkResult.data
                        println("something123 ${networkResult.data}")
                    }
                    is ApiError -> {println("something1234 error")}//todo
                    is ApiException -> {println("something12345 exception ${networkResult.e.message}")} //todo
                }
            }
        }
    }
}