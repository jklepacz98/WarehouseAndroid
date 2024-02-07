package com.example.warehouseandroid.receiptdocument.remote

import com.example.warehouseandroid.ApiService
import com.example.warehouseandroid.receiptdocument.ReceiptDocument
import com.example.warehouseandroid.util.ApiResult
import com.example.warehouseandroid.util.handleApi

class ReceiptDocumentRemoteRepository(private val apiService: ApiService) :
    ReceiptDocumentRemoteDataSource {
    override suspend fun getAllReceiptDocuments(): ApiResult<List<ReceiptDocument>> =
        handleApi { apiService.getAllReceiptDocuments() }

    override suspend fun getReceiptDocument(id: Long): ApiResult<ReceiptDocument> =
        handleApi { apiService.getReceiptDocument(id) }

    override suspend fun postReceiptDocument(receiptDocument: ReceiptDocument): ApiResult<ReceiptDocument> =
        handleApi { apiService.postReceiptDocument(receiptDocument) }

    override suspend fun putReceiptDocument(
        id: Long,
        receiptDocument: ReceiptDocument
    ): ApiResult<ReceiptDocument> =
        handleApi { apiService.putReceiptDocument(id, receiptDocument) }
}