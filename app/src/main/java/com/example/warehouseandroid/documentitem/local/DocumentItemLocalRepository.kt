package com.example.warehouseandroid.documentitem.local

import com.example.warehouseandroid.util.DatabaseResult
import com.example.warehouseandroid.util.handleDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DocumentItemLocalRepository(private val documentItemDao: DocumentItemDao) :
    DocumentItemLocalDataSource {
    override suspend fun insertDocumentItem(documentItemEntity: DocumentItemEntity): DatabaseResult<Unit> =
        handleDatabase { documentItemDao.insertDocumentItem(documentItemEntity) }

    override suspend fun deleteDocumentItem(id: Long): DatabaseResult<Unit> =
        handleDatabase { documentItemDao.deleteDocumentItem(id) }

    override fun observeDocumentItem(id: Long): Flow<DatabaseResult<DocumentItemEntity>> = flow {
        try {
            documentItemDao.observeDocumentItem(id).collect() { result ->
                val documentItemEntity = result.obj
                if (documentItemEntity != null) {
                    emit(DatabaseResult.Success(documentItemEntity))
                }
            }
        } catch (e: Exception) {
            emit(DatabaseResult.Error(e))
        }
    }

    override suspend fun insertDocumentItems(documentItemEntityList: List<DocumentItemEntity>): DatabaseResult<Unit> =
        handleDatabase { documentItemDao.insertDocumentItems(documentItemEntityList) }

    override suspend fun deleteDocumentItems(receiptDocumentId: Long): DatabaseResult<Unit> =
        handleDatabase { documentItemDao.deleteDocumentItems(receiptDocumentId) }

    override fun observeDocumentItems(receiptDocumentId: Long): Flow<DatabaseResult<List<DocumentItemEntity>>> =
        flow {
            try {
                documentItemDao.observeDocumentItems(receiptDocumentId).collect() { result ->
                    emit(DatabaseResult.Success(result.list))
                }
            } catch (e: Exception) {
                emit(DatabaseResult.Error(e))
            }
        }
}