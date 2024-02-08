package com.example.warehouseandroid.receiptdocumentedit.viewmodel

import android.net.Uri
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.receiptdocument.ReceiptDocument
import com.example.warehouseandroid.receiptdocument.ReceiptDocumentDataSource
import com.example.warehouseandroid.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReceiptDocumentEditViewModel(
    private val receiptDocumentDataSource: ReceiptDocumentDataSource,
    private val gson: Gson,
    receiptDocumentJson: String
) :
    ViewModel() {
    val symbol: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val name: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())
    val isReceiptDocumentEdited: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val receiptDocument: ReceiptDocument = deserializeReceiptDocument(receiptDocumentJson)


    init {
        receiptDocument.symbol?.let {
            val textFieldValue = TextFieldValue(
                text = it,
                selection = TextRange(it.length)
            )
            setSymbol(textFieldValue)
        }
    }

    private fun deserializeReceiptDocument(receiptDocumentJson: String): ReceiptDocument =
        gson.fromJson(Uri.decode(receiptDocumentJson), ReceiptDocument::class.java)

    fun setSymbol(textFieldValue: TextFieldValue) {
        symbol.value = textFieldValue
    }

    fun putReceiptDocument() {
        val newReceiptDocument = ReceiptDocument(0, symbol.value.text, receiptDocument.contractor)
        val id = receiptDocument.id
        viewModelScope.launch {
            receiptDocumentDataSource.putReceiptDocument(id, newReceiptDocument)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            isReceiptDocumentEdited.value = true
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