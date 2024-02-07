package com.example.warehouseandroid.documentitem

import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.Flow

interface DocumentItemDataSource {
    suspend fun getDocumentItem(id: Long): Flow<Resource<DocumentItem>>
    suspend fun postDocumentItem(documentItem: DocumentItem): Flow<Resource<DocumentItem>>
    suspend fun putDocumentItem(id: Long, documentItem: DocumentItem): Flow<Resource<DocumentItem>>
    suspend fun observeDocumentItem(id: Long): Flow<Resource<DocumentItem>>
    suspend fun getDocumentItems(receiptDocumentId: Long): Flow<Resource<List<DocumentItem>>>
    suspend fun observeDocumentItems(receiptDocumentId: Long): Flow<Resource<List<DocumentItem>>>
}