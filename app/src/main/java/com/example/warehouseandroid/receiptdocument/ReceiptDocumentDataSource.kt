package com.example.warehouseandroid.receiptdocument

import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.Flow

interface ReceiptDocumentDataSource {
    suspend fun getReceiptDocument(id: Long): Flow<Resource<ReceiptDocument>>
    suspend fun postReceiptDocument(receiptDocument: ReceiptDocument): Flow<Resource<ReceiptDocument>>
    suspend fun putReceiptDocument(
        id: Long,
        receiptDocument: ReceiptDocument
    ): Flow<Resource<ReceiptDocument>>

    suspend fun getAllReceiptDocuments(): Flow<Resource<List<ReceiptDocument>>>
    suspend fun observeAllReceiptDocuments(): Flow<Resource<List<ReceiptDocument>>>
    suspend fun observeReceiptDocument(id: Long): Flow<Resource<ReceiptDocument>>
}