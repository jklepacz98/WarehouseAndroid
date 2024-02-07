package com.example.warehouseandroid.receiptdocument.local

import com.example.warehouseandroid.util.DatabaseResult
import kotlinx.coroutines.flow.Flow

class ReceiptDocumentLocalRepository(private val receiptDocumentDao: ReceiptDocumentDao) :
    ReceiptDocumentLocalDataSource {
    override suspend fun insertReceiptDocument(receiptDocumentEntity: ReceiptDocumentEntity): DatabaseResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReceiptDocument(id: Long): DatabaseResult<Unit> {
        TODO("Not yet implemented")
    }

    override fun observeReceiptDocument(id: Long): Flow<DatabaseResult<ReceiptDocumentEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertReceiptDocuments(receiptDocumentEntityList: List<ReceiptDocumentEntity>): DatabaseResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllReceiptDocuments(): DatabaseResult<Unit> {
        TODO("Not yet implemented")
    }

    override fun observeAllReceiptDocuments(): Flow<DatabaseResult<List<ReceiptDocumentEntity>>> {
        TODO("Not yet implemented")
    }
}