package com.example.warehouseandroid.receiptdocumentdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.documentitem.DocumentItemDataSource
import com.example.warehouseandroid.receiptdocument.ReceiptDocument
import com.example.warehouseandroid.receiptdocument.ReceiptDocumentDataSource
import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReceiptDocumentDetailsViewModel(
    private val receiptDocumentDataSource: ReceiptDocumentDataSource,
    private val documentItemDataSource: DocumentItemDataSource,
    private val receiptDocumentId: Long
) :
    ViewModel() {

    val receiptDocumentFlow: MutableStateFlow<ReceiptDocument?> = MutableStateFlow(null)
    val documentItemListFlow: MutableStateFlow<List<DocumentItem>> = MutableStateFlow(emptyList())
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        fetchReceiptDocument()
        observeReceiptDocument()
        fetchAllDocumentItems()
        observeAllDocumentItems()
    }

    fun refresh() {
        fetchReceiptDocument()
        fetchAllDocumentItems()
    }

    private fun fetchReceiptDocument() {
        viewModelScope.launch {
            receiptDocumentDataSource.getReceiptDocument(receiptDocumentId).collect { resource ->
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


    private fun observeReceiptDocument() {
        viewModelScope.launch {
            receiptDocumentDataSource.observeReceiptDocument(receiptDocumentId)
                .collect() { resource ->
                    when (resource) {
                        is Resource.Success -> receiptDocumentFlow.value = resource.data
                        is Resource.Error -> errorFlow.value = resource.message
                        is Resource.Loading -> {}
                    }
                }
        }
    }

    private fun fetchAllDocumentItems() {
        viewModelScope.launch {
            documentItemDataSource.getDocumentItems(receiptDocumentId).collect() { resource ->
                when (resource) {
                    is Resource.Success -> isRefreshing.value = false
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

    private fun observeAllDocumentItems() {
        viewModelScope.launch {
            documentItemDataSource.observeDocumentItems(receiptDocumentId).collect() { resource ->
                when (resource) {
                    is Resource.Success -> documentItemListFlow.value = resource.data
                    is Resource.Error -> errorFlow.value = resource.message
                    is Resource.Loading -> {}
                }
            }
        }
    }
}