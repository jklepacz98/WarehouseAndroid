package com.example.warehouseandroid.receiptdocument

import com.example.warehouseandroid.receiptdocument.local.ReceiptDocumentLocalDataSource
import com.example.warehouseandroid.receiptdocument.mapper.ReceiptDocumentMapper
import com.example.warehouseandroid.receiptdocument.remote.ReceiptDocumentRemoteDataSource
import com.example.warehouseandroid.util.ApiResult
import com.example.warehouseandroid.util.DatabaseResult
import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class ReceiptDocumentRepository(
    private val receiptDocumentLocalDataSource: ReceiptDocumentLocalDataSource,
    private val receiptDocumentRemoteDataSource: ReceiptDocumentRemoteDataSource
) : ReceiptDocumentDataSource {
    override suspend fun getReceiptDocument(id: Long): Flow<Resource<ReceiptDocument>> = flow {
        emit(Resource.Loading())
        val networkResult = receiptDocumentRemoteDataSource.getReceiptDocument(id)
        when (networkResult) {
            //todo
            is ApiResult.Success -> emit(Resource.Success(networkResult.data))
            is ApiResult.Error -> emit(Resource.Error(networkResult.message))
            is ApiResult.Exception -> emit(Resource.Error(networkResult.e.message ?: UNKNOWN_ERROR))
        }
    }

    override suspend fun postReceiptDocument(receiptDocument: ReceiptDocument): Flow<Resource<ReceiptDocument>> =
        flow {
            emit(Resource.Loading())
            val networkResult = receiptDocumentRemoteDataSource.postReceiptDocument(receiptDocument)
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


    override suspend fun putReceiptDocument(
        id: Long,
        receiptDocument: ReceiptDocument
    ): Flow<Resource<ReceiptDocument>> = flow {
        emit(Resource.Loading())
        val networkResult = receiptDocumentRemoteDataSource.putReceiptDocument(id, receiptDocument)
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

    override suspend fun getAllReceiptDocuments(): Flow<Resource<List<ReceiptDocument>>> = flow {
        emit(Resource.Loading())
        val networkResult = receiptDocumentRemoteDataSource.getAllReceiptDocuments()
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

    private suspend fun FlowCollector<Resource<List<ReceiptDocument>>>.clearCache(data: List<ReceiptDocument>) {
        val result = receiptDocumentLocalDataSource.deleteAllReceiptDocuments()
        when (result) {
            is DatabaseResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is DatabaseResult.Success -> fillCache(data)
        }
    }

    private suspend fun FlowCollector<Resource<ReceiptDocument>>.clearCache(data: ReceiptDocument) {
        val result = receiptDocumentLocalDataSource.deleteReceiptDocument(data.id)
        when (result) {
            is DatabaseResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is DatabaseResult.Success -> fillCache(data)
        }
    }

    private suspend fun FlowCollector<Resource<List<ReceiptDocument>>>.fillCache(data: List<ReceiptDocument>) {
        val receiptDocumentEntityList =
            ReceiptDocumentMapper.mapToReceiptDocumentEntities(data)
        val result =
            receiptDocumentLocalDataSource.insertReceiptDocuments(receiptDocumentEntityList)
        when (result) {
            is DatabaseResult.Error -> emit(
                Resource.Error(result.e.message ?: UNKNOWN_ERROR)
            )

            is DatabaseResult.Success -> emit(Resource.Success(data))
        }
    }

    private suspend fun FlowCollector<Resource<ReceiptDocument>>.fillCache(data: ReceiptDocument) {
        val receiptDocumentEntity = ReceiptDocumentMapper.mapToReceiptDocumentEntity(data)
        val result = receiptDocumentLocalDataSource.insertReceiptDocument(receiptDocumentEntity)
        when (result) {
            is DatabaseResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is DatabaseResult.Success -> emit(Resource.Success(data))
        }
    }


    override suspend fun observeAllReceiptDocuments(): Flow<Resource<List<ReceiptDocument>>> =
        flow {
            emit(Resource.Loading())
            receiptDocumentLocalDataSource.observeAllReceiptDocuments().collect() { result ->
                when (result) {
                    is DatabaseResult.Success -> {
                        val receiptDocumentList =
                            ReceiptDocumentMapper.mapToReceiptDocuments(result.data)
                        emit(Resource.Success(receiptDocumentList))
                    }

                    is DatabaseResult.Error -> emit(
                        Resource.Error(
                            result.e.message ?: UNKNOWN_ERROR
                        )
                    )
                }
            }
        }

    override suspend fun observeReceiptDocument(id: Long): Flow<Resource<ReceiptDocument>> = flow {
        emit(Resource.Loading())
        receiptDocumentLocalDataSource.observeReceiptDocument(id).collect { result ->
            when (result) {
                is DatabaseResult.Success -> {
                    val receiptDocumentList =
                        ReceiptDocumentMapper.mapToReceiptDocument(result.data)
                    emit(Resource.Success(receiptDocumentList))
                }

                is DatabaseResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))

            }
        }
    }


    companion object {
        private const val UNKNOWN_ERROR: String = "Unknown Error"
    }
}