package com.example.warehouseandroid.documentitem

import com.example.warehouseandroid.documentitem.local.DocumentItemLocalDataSource
import com.example.warehouseandroid.documentitem.remote.DocumentItemRemoteDataSource
import com.example.warehouseandroid.util.ApiResult
import com.example.warehouseandroid.util.DatabaseResult
import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class DocumentItemRepository(
    private val documentItemLocalDataSource: DocumentItemLocalDataSource,
    private val documentItemRemoteDataSource: DocumentItemRemoteDataSource
) : DocumentItemDataSource {
    override suspend fun getDocumentItem(id: Long): Flow<Resource<DocumentItem>> = flow {
        emit(Resource.Loading())
        val networkResult = documentItemRemoteDataSource.getDocumentItem(id)
        when (networkResult) {
            //todo
            //is ApiResult.Success -> clearCache(networkResult.data)
            is ApiResult.Success -> emit(Resource.Success(networkResult.data))
            is ApiResult.Error -> emit(Resource.Error(networkResult.message))
            is ApiResult.Exception -> emit(Resource.Error(networkResult.e.message ?: UNKNOWN_ERROR))
        }
    }

    override suspend fun postDocumentItem(documentItem: DocumentItem): Flow<Resource<DocumentItem>> =
        flow {
            emit(Resource.Loading())
            val networkResult = documentItemRemoteDataSource.postDocumentItem(documentItem)
            when (networkResult) {
                is ApiResult.Success -> clearCache(networkResult.data)
                is ApiResult.Error -> emit(Resource.Error(networkResult.message))
                is ApiResult.Exception -> emit(
                    Resource.Error(
                        networkResult.e.message ?: UNKNOWN_ERROR
                    )
                )
            }
        }

    override suspend fun putDocumentItem(
        id: Long,
        documentItem: DocumentItem
    ): Flow<Resource<DocumentItem>> = flow {
        emit(Resource.Loading())
        val networkResult = documentItemRemoteDataSource.putDocumentItem(id, documentItem)
        when (networkResult) {
            is ApiResult.Success -> clearCache(networkResult.data)
            is ApiResult.Error -> emit(Resource.Error(networkResult.message))
            is ApiResult.Exception -> emit(
                Resource.Error(
                    networkResult.e.message ?: UNKNOWN_ERROR
                )
            )
        }
    }

    override suspend fun observeDocumentItem(id: Long): Flow<Resource<DocumentItem>> = flow {
        emit(Resource.Loading())

        documentItemLocalDataSource.observeDocumentItem(id).collect { result ->
            when (result) {
                is DatabaseResult.Success -> {
                    emit(Resource.Success(result.data))
                }

                is DatabaseResult.Error -> emit(
                    Resource.Error(
                        result.e.message ?: UNKNOWN_ERROR
                    )
                )
            }
        }
    }

    override suspend fun getDocumentItems(receiptDocumentId: Long): Flow<Resource<List<DocumentItem>>> =
        flow {
            emit(Resource.Loading())
            val networkResult = documentItemRemoteDataSource.getDocumentItems(receiptDocumentId)
            when (networkResult) {
                is ApiResult.Success -> clearCache(networkResult.data, receiptDocumentId)
                is ApiResult.Error -> emit(Resource.Error(networkResult.message))
                is ApiResult.Exception -> emit(
                    Resource.Error(
                        networkResult.e.message ?: UNKNOWN_ERROR
                    )
                )
            }
        }

    override suspend fun observeDocumentItems(receiptDocumentId: Long): Flow<Resource<List<DocumentItem>>> =
        flow {
            emit(Resource.Loading())
            documentItemLocalDataSource.observeDocumentItems(receiptDocumentId).collect { result ->
                when (result) {
                    is DatabaseResult.Success -> {
                        emit(Resource.Success(result.data))
                    }

                    is DatabaseResult.Error -> emit(
                        Resource.Error(
                            result.e.message ?: UNKNOWN_ERROR
                        )
                    )
                }
            }
        }

    private suspend fun FlowCollector<Resource<DocumentItem>>.clearCache(data: DocumentItem) {
        val result = documentItemLocalDataSource.deleteDocumentItem(data.id)
        when (result) {
            is DatabaseResult.Error -> emit(
                Resource.Error(
                    result.e.message ?: UNKNOWN_ERROR
                )
            )

            is DatabaseResult.Success -> fillCache(data)
        }
    }

    private suspend fun FlowCollector<Resource<DocumentItem>>.fillCache(data: DocumentItem) {
        val result = documentItemLocalDataSource.insertDocumentItem(data)
        when (result) {
            is DatabaseResult.Error -> emit(
                Resource.Error(
                    result.e.message ?: UNKNOWN_ERROR
                )
            )

            is DatabaseResult.Success -> emit(Resource.Success(data))
        }
    }

    private suspend fun FlowCollector<Resource<List<DocumentItem>>>.clearCache(
        data: List<DocumentItem>,
        receiptDocumentId: Long
    ) {
        val result = documentItemLocalDataSource.deleteDocumentItems(receiptDocumentId)
        when (result) {
            is DatabaseResult.Error -> emit(
                Resource.Error(
                    result.e.message ?: UNKNOWN_ERROR
                )
            )

            is DatabaseResult.Success -> fillCache(data)
        }
    }

    private suspend fun FlowCollector<Resource<List<DocumentItem>>>.fillCache(data: List<DocumentItem>) {
        val result = documentItemLocalDataSource.insertDocumentItems(data)
        when (result) {
            is DatabaseResult.Error -> emit(
                Resource.Error(
                    result.e.message ?: UNKNOWN_ERROR
                )
            )

            is DatabaseResult.Success -> emit(Resource.Success(data))
        }
    }

    companion object {
        private const val UNKNOWN_ERROR: String = "Unknown Error"
    }
}