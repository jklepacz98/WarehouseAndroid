package com.example.warehouseandroid.documentitem.remote


import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.util.ApiResult

interface DocumentItemRemoteDataSource {
    suspend fun getDocumentItems(receiptDocumentId: Long): ApiResult<List<DocumentItem>>
    suspend fun getDocumentItem(id: Long): ApiResult<DocumentItem>
    suspend fun postDocumentItem(contractor: DocumentItem): ApiResult<DocumentItem>
    suspend fun putDocumentItem(id: Long, newDocumentItem: DocumentItem): ApiResult<DocumentItem>
}