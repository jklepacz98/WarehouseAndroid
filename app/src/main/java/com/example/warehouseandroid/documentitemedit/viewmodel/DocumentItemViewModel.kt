package com.example.warehouseandroid.documentitemedit.viewmodel

import android.net.Uri
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.documentitem.DocumentItemDataSource
import com.example.warehouseandroid.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class DocumentItemEditViewModel(
    private val documentItemDataSource: DocumentItemDataSource,
    private val gson: Gson,
    documentItemJson: String
) :
    ViewModel() {
    val productName: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val unitOfMeasure: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val amount: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val isDocumentItemEdited: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val documentItem: DocumentItem = deserializeDocumentItem(documentItemJson)

    init {
        documentItem.productName?.let { string ->
            setProductName(createTextFieldValueWithSelection(string))
        }
        documentItem.unitOfMeasure?.let { string ->
            setUnitOfMeasure(createTextFieldValueWithSelection(string))
        }
        documentItem.amount?.let { float ->
            setAmount(createTextFieldValueWithSelection(float.toString()))
        }
    }

    private fun deserializeDocumentItem(documentItemJson: String) : DocumentItem =
        gson.fromJson(Uri.decode(documentItemJson), DocumentItem::class.java)


    private fun createTextFieldValueWithSelection(string: String) =
        TextFieldValue(text = string, selection = TextRange(string.length))


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

    fun putDocumentItem() {
        val amount = this.amount.value.text.toFloatOrNull()
        val newDocumentItem = DocumentItem(
            0,
            productName.value.text,
            unitOfMeasure.value.text,
            amount,
            documentItem.receiptDocumentId
        )
        val id = documentItem.id
        viewModelScope.launch {
            documentItemDataSource.putDocumentItem(id, newDocumentItem).collect { resource ->
                when (resource) {
                    //todo
                    is Resource.Success -> {
                        isDocumentItemEdited.value = true
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