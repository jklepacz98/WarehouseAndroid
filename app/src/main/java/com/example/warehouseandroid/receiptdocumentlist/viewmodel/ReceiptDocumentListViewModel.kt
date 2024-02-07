package com.example.warehouseandroid.receiptdocumentlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.receiptdocument.ReceiptDocument
import com.example.warehouseandroid.receiptdocument.ReceiptDocumentDataSource
import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReceiptDocumentListViewModel(private val receiptDocumentDataSource: ReceiptDocumentDataSource) :
    ViewModel() {

    val receiptDocumentListFlow: MutableStateFlow<List<ReceiptDocument>> =
        MutableStateFlow(emptyList())
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        getAllReceiptDocuments()
        observeAllReceiptDocuments()
    }

    fun refresh() {
        getAllReceiptDocuments()
    }

    private fun getAllReceiptDocuments() {
        viewModelScope.launch {
            receiptDocumentDataSource.getAllReceiptDocuments().collect() { resource ->
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

    private fun observeAllReceiptDocuments() {
        viewModelScope.launch {
            receiptDocumentDataSource.observeAllReceiptDocuments().collect() { resource ->
                when (resource) {
                    is Resource.Success -> receiptDocumentListFlow.value = resource.data
                    is Resource.Error -> errorFlow.value = resource.message
                    is Resource.Loading -> {}
                }
            }
        }
    }
}