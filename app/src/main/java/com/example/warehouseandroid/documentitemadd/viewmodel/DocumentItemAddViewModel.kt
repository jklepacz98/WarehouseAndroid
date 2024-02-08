package com.example.warehouseandroid.documentitemadd.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.documentitem.DocumentItemDataSource
import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DocumentItemAddViewModel(
    private val documentItemDataSource: DocumentItemDataSource,
    private val receiptDocumentId: Long
) :
    ViewModel() {
    val productName: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val unitOfMeasure: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val amount: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val isDocumentItemEdited: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    fun setProductName(textFieldValue: TextFieldValue) {
        productName.value = textFieldValue
    }

    fun setUnitOfMeasure(textFieldValue: TextFieldValue) {
        unitOfMeasure.value = textFieldValue
    }

    fun setAmount(textFieldValue: TextFieldValue) {
        val canConvertToFloat = textFieldValue.text.toFloatOrNull() != null
        if (canConvertToFloat) amount.value = textFieldValue
    }

    fun postDocumentItem() {
        val amount = this.amount.value.text.toFloatOrNull()
        val newDocumentItem = DocumentItem(
            0,
            productName.value.text,
            unitOfMeasure.value.text,
            amount,
            receiptDocumentId
        )
        viewModelScope.launch {
            documentItemDataSource.postDocumentItem(newDocumentItem).collect { resource ->
                when (resource) {
                    //todo
                    is Resource.Success -> {
                        isLoading.value = false
                        isDocumentItemEdited.value = true
                    }

                    is Resource.Error -> {
                        isLoading.value = false
                        errorFlow.value = resource.message
                    }

                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                }
            }
        }
    }
}