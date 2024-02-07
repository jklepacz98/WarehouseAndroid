package com.example.warehouseandroid.documentitem.local

import com.example.warehouseandroid.util.DatabaseResult
import kotlinx.coroutines.flow.Flow

interface DocumentItemLocalDataSource {
    suspend fun insertDocumentItem(documentItemEntity: DocumentItemEntity): DatabaseResult<Unit>
    suspend fun deleteDocumentItem(id: Long): DatabaseResult<Unit>
    fun observeDocumentItem(id: Long): Flow<DatabaseResult<DocumentItemEntity>>
    suspend fun insertDocumentItems(documentItemEntityList: List<DocumentItemEntity>): DatabaseResult<Unit>
    suspend fun deleteDocumentItems(receiptDocumentId: Long): DatabaseResult<Unit>
    fun observeDocumentItems(receiptDocumentId: Long): Flow<DatabaseResult<List<DocumentItemEntity>>>
}