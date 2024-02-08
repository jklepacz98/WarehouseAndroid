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
        //todo
        val newReceiptDocument = ReceiptDocument(0, symbol.value.text, receiptDocument.contractor)
        val id = receiptDocument.id
        viewModelScope.launch {
            receiptDocumentDataSource.putReceiptDocument(id, newReceiptDocument)
                .collect { resource ->
                    when (resource) {
                        //todo
                        is Resource.Success -> {
                            isReceiptDocumentEdited.value = true
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