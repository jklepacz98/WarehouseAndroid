package com.example.warehouseandroid.documentitem.remote

import com.example.warehouseandroid.ApiService
import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.util.ApiResult
import com.example.warehouseandroid.util.handleApi

class DocumentItemRemoteRepository(private val apiService: ApiService) :
    DocumentItemRemoteDataSource {
    override suspend fun getDocumentItems(receiptDocumentId: Long): ApiResult<List<DocumentItem>> =
        handleApi { apiService.getDocumentItems(receiptDocumentId) }

    override suspend fun getDocumentItem(id: Long): ApiResult<DocumentItem> =
        handleApi { apiService.getDocumentItem(id) }

    override suspend fun postDocumentItem(contractor: DocumentItem): ApiResult<DocumentItem> =
        handleApi { apiService.postDocumentItem(contractor) }

    override suspend fun putDocumentItem(
        id: Long,
        newDocumentItem: DocumentItem
    ): ApiResult<DocumentItem> =
        handleApi { apiService.putDocumentItem(id, newDocumentItem) }
}