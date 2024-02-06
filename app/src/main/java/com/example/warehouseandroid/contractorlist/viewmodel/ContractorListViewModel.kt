package com.example.warehouseandroid.contractorlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractor.ContractorDataSource
import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContractorListViewModel(private val contractorDataSource: ContractorDataSource) :
    ViewModel() {

    val contractorListFlow: MutableStateFlow<List<Contractor>> = MutableStateFlow(emptyList())
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        getAllContractors()
        observeAllContractors()
    }

    fun refresh() {
        getAllContractors()
    }

    //todo change name
    private fun getAllContractors() {
        viewModelScope.launch {
            contractorDataSource.getAllContractors().collect { resource ->
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

    private fun observeAllContractors() {
        viewModelScope.launch {
            contractorDataSource.observeAllContractors().collect() { resource ->
                when (resource) {
                    is Resource.Success -> {
                        //todo
                        contractorListFlow.value = resource.data
                    }
                    //todo
                    is Resource.Error -> {
                        errorFlow.value = resource.message
                    }
                    //todo
                    is Resource.Loading -> {}
                }
            }
        }
    }
}