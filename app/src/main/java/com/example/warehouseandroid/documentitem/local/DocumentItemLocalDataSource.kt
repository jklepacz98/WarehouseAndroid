package com.example.warehouseandroid.documentitem.local

import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.util.DatabaseResult
import kotlinx.coroutines.flow.Flow

interface DocumentItemLocalDataSource {
    suspend fun insertDocumentItem(documentItem: DocumentItem): DatabaseResult<Unit>
    suspend fun deleteDocumentItem(id: Long): DatabaseResult<Unit>
    fun observeDocumentItem(id: Long): Flow<DatabaseResult<DocumentItem>>
    suspend fun insertDocumentItems(documentItemList: List<DocumentItem>): DatabaseResult<Unit>
    suspend fun deleteDocumentItems(receiptDocumentId: Long): DatabaseResult<Unit>
    fun observeDocumentItems(receiptDocumentId: Long): Flow<DatabaseResult<List<DocumentItem>>>
}