package com.example.warehouseandroid.documentitemdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.documentitem.DocumentItemDataSource
import com.example.warehouseandroid.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DocumentItemDetailsViewModel(
    private val documentItemDataSource: DocumentItemDataSource,
    private val gson: Gson,
    private val documentItemId: Long
) : ViewModel() {

    val documentItemFlow: MutableStateFlow<DocumentItem?> = MutableStateFlow(null)
    val errorFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        //todo
        fetchDocumentItem()
        observeDocumentItem()
    }

    fun serializeDocumentItem(): String {
        val documentItem = documentItemFlow.value
        return gson.toJson(documentItem)
    }

    private fun fetchDocumentItem() {
        viewModelScope.launch {
            documentItemDataSource.getDocumentItem(documentItemId).collect { resource ->
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


    private fun observeDocumentItem() {
        viewModelScope.launch {
            documentItemDataSource.observeDocumentItem(documentItemId).collect() { resource ->
                when (resource) {
                    is Resource.Success -> documentItemFlow.value = resource.data
                    is Resource.Error -> errorFlow.value = resource.message
                    is Resource.Loading -> {}
                }
            }
        }
    }
}