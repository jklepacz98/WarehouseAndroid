package com.example.warehouseandroid.receiptdocument.local

import com.example.warehouseandroid.util.DatabaseResult
import kotlinx.coroutines.flow.Flow

interface ReceiptDocumentLocalDataSource {
    suspend fun insertReceiptDocument(receiptDocumentEntity: ReceiptDocumentEntity): DatabaseResult<Unit>
    suspend fun deleteReceiptDocument(id: Long): DatabaseResult<Unit>
    fun observeReceiptDocument(id: Long): Flow<DatabaseResult<ReceiptDocumentEntity>>
    suspend fun insertReceiptDocuments(receiptDocumentEntityList: List<ReceiptDocumentEntity>): DatabaseResult<Unit>
    suspend fun deleteAllReceiptDocuments(): DatabaseResult<Unit>
    fun observeAllReceiptDocuments(): Flow<DatabaseResult<List<ReceiptDocumentEntity>>>
}