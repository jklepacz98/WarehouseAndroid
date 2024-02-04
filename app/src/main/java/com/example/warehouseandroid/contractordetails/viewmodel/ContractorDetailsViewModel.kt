package com.example.warehouseandroid.contractordetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractor.ContractorDataSource
import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContractorDetailsViewModel(
    private val contractorDataSource: ContractorDataSource,
    private val contractorId: Long
) : ViewModel() {

    val contractorFlow: MutableStateFlow<Contractor?> = MutableStateFlow(null)
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {

        //todo
        fetchContractor()
        observeContractor()
    }

    fun refresh() {
        fetchContractor()
    }

    private fun fetchContractor() {
        viewModelScope.launch {
            contractorDataSource.getContractor(contractorId).collect { resource ->
                when (resource) {
                    //todo
                    is Resource.Success -> {
                        isRefreshing.value = false
                    }

                    is Resource.Error -> {
                        errorFlow.value = resource.message
                        isRefreshing.value = false
                    }

                    is Resource.Loading -> {
                        isRefreshing.value = true
                    }
                }
            }
        }
    }

    private fun observeContractor() {
        viewModelScope.launch {
            contractorDataSource.observeContractor(contractorId).collect() { resource ->
                when (resource) {
                    is Resource.Success -> {
                        //todo
                        println("Cos123 success")
                        contractorFlow.value = resource.data
                    }
                    //todo
                    is Resource.Error -> {
                        errorFlow.value = resource.message
                    }
                    //todo
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }
}
