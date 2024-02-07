package com.example.warehouseandroid.contractoradd.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractor.ContractorDataSource
import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContractorAddViewModel(private val contractorDataSource: ContractorDataSource) :
    ViewModel() {
    val symbol: MutableStateFlow<String> = MutableStateFlow("")
    val name: MutableStateFlow<String> = MutableStateFlow("")
    val isContractorAdded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun setSymbol(text: String) {
        symbol.value = text
    }

    fun setName(text: String) {
        name.value = text
    }

    fun postContractor() {
        val contractor = Contractor(0, symbol.value, name.value)
        viewModelScope.launch {
            contractorDataSource.postContractor(contractor).collect { resource ->
                when (resource) {
                    //todo
                    is Resource.Success -> {
                        isContractorAdded.value = true
                        isLoading.value = false
                    }

                    is Resource.Error -> {
                        errorFlow.value = resource.message
                        isLoading.value = false
                    }

                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                }
            }
        }
    }
}