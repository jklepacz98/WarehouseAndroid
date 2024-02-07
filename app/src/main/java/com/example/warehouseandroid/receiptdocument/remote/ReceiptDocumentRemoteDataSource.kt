package com.example.warehouseandroid.receiptdocument.remote

import com.example.warehouseandroid.receiptdocument.ReceiptDocument
import com.example.warehouseandroid.util.ApiResult

interface ReceiptDocumentRemoteDataSource {
    suspend fun getAllReceiptDocuments(): ApiResult<List<ReceiptDocument>>
    suspend fun getReceiptDocument(id: Long): ApiResult<ReceiptDocument>
    suspend fun postReceiptDocument(receiptDocument: ReceiptDocument): ApiResult<ReceiptDocument>
    suspend fun putReceiptDocument(receiptDocument: ReceiptDocument): ApiResult<ReceiptDocument>
}