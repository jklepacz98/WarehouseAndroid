package com.example.warehouseandroid.documentitem.local

import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.documentitem.mapper.DocumentItemMapper
import com.example.warehouseandroid.util.DatabaseResult
import com.example.warehouseandroid.util.handleDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DocumentItemLocalRepository(private val documentItemDao: DocumentItemDao) :
    DocumentItemLocalDataSource {
    override suspend fun insertDocumentItem(documentItem: DocumentItem): DatabaseResult<Unit> {
        val documentItemEntity = DocumentItemMapper.mapToDocumentItemEntity(documentItem)
        return handleDatabase { documentItemDao.insertDocumentItem(documentItemEntity) }
    }

    override suspend fun deleteDocumentItem(id: Long): DatabaseResult<Unit> =
        handleDatabase { documentItemDao.deleteDocumentItem(id) }

    override fun observeDocumentItem(id: Long): Flow<DatabaseResult<DocumentItem>> = flow {
        try {
            documentItemDao.observeDocumentItem(id).collect { result ->
                val documentItemEntity = result.obj
                if (documentItemEntity != null) {
                    val documentItem = DocumentItemMapper.mapToDocumentItem(documentItemEntity)
                    emit(DatabaseResult.Success(documentItem))
                }
            }
        } catch (e: Exception) {
            emit(DatabaseResult.Error(e))
        }
    }

    override suspend fun insertDocumentItems(documentItemList: List<DocumentItem>): DatabaseResult<Unit> {
        val documentItemEntityList = DocumentItemMapper.mapToDocumentItemEntities(documentItemList)
        return handleDatabase { documentItemDao.insertDocumentItems(documentItemEntityList) }
    }


    override suspend fun deleteDocumentItems(receiptDocumentId: Long): DatabaseResult<Unit> =
        handleDatabase { documentItemDao.deleteDocumentItems(receiptDocumentId) }

    override fun observeDocumentItems(receiptDocumentId: Long): Flow<DatabaseResult<List<DocumentItem>>> =
        flow {
            try {
                documentItemDao.observeDocumentItems(receiptDocumentId).collect() { result ->
                    val documentItemList = DocumentItemMapper.mapToDocumentItems(result.list)
                    emit(DatabaseResult.Success(documentItemList))
                }
            } catch (e: Exception) {
                emit(DatabaseResult.Error(e))
            }
        }
}