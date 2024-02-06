package com.example.warehouseandroid.contractoredit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractor.ContractorDataSource
import com.example.warehouseandroid.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ContractorEditViewModel(
    private val contractorDataSource: ContractorDataSource,
    private val gson: Gson,
    private val contractorJson: String
) :
    ViewModel() {
    val symbol: MutableStateFlow<String> = MutableStateFlow("")
    val name: MutableStateFlow<String> = MutableStateFlow("")
    val isContractorEdited: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val contractor = gson.fromJson(contractorJson, Contractor::class.java)

    init {
        contractor.symbol?.let { setSymbol(it) }
        contractor.name?.let { setName(it) }
    }

    fun setSymbol(text: String) {
        symbol.value = text
    }

    fun setName(text: String) {
        name.value = text
    }

    fun PutContractor() {
        val newContractor = Contractor(0, symbol.value, name.value)
        val id = contractor.id
        viewModelScope.launch {
            contractorDataSource.putContractor(id, newContractor).collect { resource ->
                when (resource) {
                    //todo
                    is Resource.Success -> {
                        isContractorEdited.value = true
                    }

                    is Resource.Error -> {
                        errorFlow.value = resource.message

                    }

                    is Resource.Loading -> {}
                }
            }
        }
    }
}