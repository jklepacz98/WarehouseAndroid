package com.example.warehouseandroid.contractordetails.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractor.ContractorDataSource
import com.example.warehouseandroid.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContractorDetailsViewModel(
    private val contractorDataSource: ContractorDataSource,
    private val gson: Gson,
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

    fun serializeContractor(): String {
        val contractor = contractorFlow.value
        return Uri.encode(gson.toJson(contractor))
    }

    fun refresh() {
        fetchContractor()
    }

    fun onEditClick() {
        //todo
    }

    private fun fetchContractor() {
        viewModelScope.launch {
            contractorDataSource.getContractor(contractorId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        isRefreshing.value = false
                    }

                    is Resource.Error -> {
                        errorFlow.value = resource.message
                        isRefreshing.value = false
                    }

                    is Resource.Loading -> isRefreshing.value = true
                }
            }
        }
    }


    private fun observeContractor() {
        viewModelScope.launch {
            contractorDataSource.observeContractor(contractorId).collect() { resource ->
                when (resource) {
                    is Resource.Success -> contractorFlow.value = resource.data
                    is Resource.Error -> errorFlow.value = resource.message
                    is Resource.Loading -> {}
                }
            }
        }
    }
}


