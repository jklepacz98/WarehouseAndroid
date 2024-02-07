package com.example.warehouseandroid.receiptdocument.local

import com.example.warehouseandroid.util.DatabaseResult
import com.example.warehouseandroid.util.handleDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReceiptDocumentLocalRepository(private val receiptDocumentDao: ReceiptDocumentDao) :
    ReceiptDocumentLocalDataSource {
    override suspend fun insertReceiptDocument(receiptDocumentEntity: ReceiptDocumentEntity): DatabaseResult<Unit> =
        handleDatabase { receiptDocumentDao.insertReceiptDocument(receiptDocumentEntity) }

    override suspend fun deleteReceiptDocument(id: Long): DatabaseResult<Unit> =
        handleDatabase { receiptDocumentDao.deleteReceiptDocument(id) }

    override fun observeReceiptDocument(id: Long): Flow<DatabaseResult<ReceiptDocumentEntity>> =
        flow {
            try {
                receiptDocumentDao.observeReceiptDocument(id).collect() { result ->
                    val receiptDocumentEntity = result.obj
                    if (receiptDocumentEntity != null) {
                        emit(DatabaseResult.Success(receiptDocumentEntity))
                    }
                }
            } catch (e: Exception) {
                emit(DatabaseResult.Error(e))
            }
        }

    override suspend fun insertReceiptDocuments(receiptDocumentEntityList: List<ReceiptDocumentEntity>): DatabaseResult<Unit> =
        handleDatabase { receiptDocumentDao.insertReceiptDocuments(receiptDocumentEntityList) }

    override suspend fun deleteAllReceiptDocuments(): DatabaseResult<Unit> =
        handleDatabase { receiptDocumentDao.deleteAllReceiptDocuments() }

    override fun observeAllReceiptDocuments(): Flow<DatabaseResult<List<ReceiptDocumentEntity>>> =
        flow {
            try {
                receiptDocumentDao.observeAllReceiptDocuments().collect() { result ->
                    emit(DatabaseResult.Success(result.list))
                }
            } catch (e: Exception) {
                emit(DatabaseResult.Error(e))
            }
        }
}