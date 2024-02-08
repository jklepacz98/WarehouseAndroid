package com.example.warehouseandroid.contractoredit.viewmodel

import android.net.Uri
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
    contractorJson: String
) :
    ViewModel() {
    val symbol: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val name: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val isContractorEdited: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val contractor: Contractor = deserializeContractor(contractorJson)

    init {
        contractor.symbol?.let {
            val textFieldValue = TextFieldValue(
                text = it,
                selection = TextRange(it.length)
            )
            setSymbol(textFieldValue)
        }
        contractor.name?.let {
            val textFieldValue = TextFieldValue(
                text = it,
                selection = TextRange(it.length)
            )
            setName(textFieldValue)
        }
    }

    private fun deserializeContractor(contractorJson: String): Contractor =
        gson.fromJson(Uri.decode(contractorJson), Contractor::class.java)

    fun setSymbol(textFieldValue: TextFieldValue) {
        symbol.value = textFieldValue
    }

    fun setName(textFieldValue: TextFieldValue) {
        name.value = textFieldValue
    }

    fun putContractor() {
        val newContractor = Contractor(0, symbol.value.text, name.value.text)
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