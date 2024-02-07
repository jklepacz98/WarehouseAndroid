package com.example.warehouseandroid.receiptdocument

import com.example.warehouseandroid.receiptdocument.local.ReceiptDocumentLocalDataSource
import com.example.warehouseandroid.receiptdocument.remote.ReceiptDocumentRemoteDataSource
import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.Flow

class ReceiptDocumentRepository(
    private val receiptDocumentLocalDataSource: ReceiptDocumentLocalDataSource,
    private val receiptDocumentRemoteDataSource: ReceiptDocumentRemoteDataSource
) : ReceiptDocumentDataSource {
    override suspend fun getReceiptDocument(id: Long): Flow<Resource<ReceiptDocument>> {
        TODO("Not yet implemented")
    }

    override suspend fun postReceiptDocument(receiptDocument: ReceiptDocument): Flow<Resource<ReceiptDocument>> {
        TODO("Not yet implemented")
    }

    override suspend fun putReceiptDocument(
        id: Long,
        receiptDocument: ReceiptDocument
    ): Flow<Resource<ReceiptDocument>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllReceiptDocuments(): Flow<Resource<List<ReceiptDocument>>> {
        TODO("Not yet implemented")
    }

    override suspend fun observeAllContractors(): Flow<Resource<List<ReceiptDocument>>> {
        TODO("Not yet implemented")
    }

    override suspend fun observeContractor(id: Long): Flow<Resource<List<ReceiptDocument>>> {
        TODO("Not yet implemented")
    }
}