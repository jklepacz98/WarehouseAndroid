package com.example.warehouseandroid.contractor

import com.example.warehouseandroid.contractor.local.ContractorLocalDataSource
import com.example.warehouseandroid.contractor.mapper.ContractorMapper
import com.example.warehouseandroid.contractor.remote.ContractorRemoteDataSource
import com.example.warehouseandroid.util.ApiResult
import com.example.warehouseandroid.util.DatabaseResult
import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow


class ContractorRepository(
    private val contractorLocalDataSource: ContractorLocalDataSource,
    private val contractorRemoteDataSource: ContractorRemoteDataSource
) : ContractorDataSource {

    override suspend fun getContractor(id: Long): Flow<Resource<Contractor>> = flow {
        emit(Resource.Loading())
        val networkResult = contractorRemoteDataSource.getContractor(id)
        when (networkResult) {
            //todo
            //is ApiResult.Success -> clearCache(networkResult.data)
            is ApiResult.Success -> emit(Resource.Success(networkResult.data))
            is ApiResult.Error -> emit(Resource.Error(networkResult.message))
            is ApiResult.Exception -> emit(Resource.Error(networkResult.e.message ?: UNKNOWN_ERROR))
        }
    }

    override suspend fun postContractor(contractor: Contractor): Flow<Resource<Contractor>> = flow {
        emit(Resource.Loading())
        val networkResult = contractorRemoteDataSource.postContractor(contractor)
        when (networkResult) {
            is ApiResult.Success -> clearCache(networkResult.data)
            is ApiResult.Error -> emit(Resource.Error(networkResult.message))
            is ApiResult.Exception -> emit(Resource.Error(networkResult.e.message ?: UNKNOWN_ERROR))
        }
    }

    override suspend fun putContractor(id: Long, contractor: Contractor) = flow {
        emit(Resource.Loading())
        val networkResult = contractorRemoteDataSource.putContractor(id, contractor)
        when (networkResult) {
            is ApiResult.Success -> clearCache(networkResult.data)
            is ApiResult.Error -> emit(Resource.Error(networkResult.message))
            is ApiResult.Exception -> emit(Resource.Error(networkResult.e.message ?: UNKNOWN_ERROR))
        }
    }

    override suspend fun observeContractor(id: Long): Flow<Resource<Contractor>> = flow {
        emit(Resource.Loading())

        contractorLocalDataSource.observeContractor(id).collect { result ->
            when (result) {
                is DatabaseResult.Success -> {
                    val contractorList = ContractorMapper.mapToContractor(result.data)
                    emit(Resource.Success(contractorList))
                }

                is DatabaseResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            }
        }
    }

    override suspend fun getAllContractors(): Flow<Resource<List<Contractor>>> = flow {
        emit(Resource.Loading())
        val networkResult = contractorRemoteDataSource.getAllContractors()
        when (networkResult) {
            is ApiResult.Success -> clearCache(networkResult.data)
            is ApiResult.Error -> emit(Resource.Error(networkResult.message))
            is ApiResult.Exception -> emit(Resource.Error(networkResult.e.message ?: UNKNOWN_ERROR))
        }
    }

    override suspend fun observeAllContractors(): Flow<Resource<List<Contractor>>> = flow {
        emit(Resource.Loading())
        contractorLocalDataSource.observeAllContractors().collect { result ->
            when (result) {
                is DatabaseResult.Success -> {
                    val contractorList = ContractorMapper.mapToContractors(result.data)
                    emit(Resource.Success(contractorList))
                }

                is DatabaseResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            }
        }
    }

    private suspend fun FlowCollector<Resource<Contractor>>.clearCache(data: Contractor) {
        val result = contractorLocalDataSource.deleteContractor(data.id)
        when (result) {
            is DatabaseResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is DatabaseResult.Success -> fillCache(data)
        }
    }

    private suspend fun FlowCollector<Resource<Contractor>>.fillCache(data: Contractor) {
        val contractorEntity = ContractorMapper.mapToContractorEntity(data)
        val result = contractorLocalDataSource.insertContractor(contractorEntity)
        when (result) {
            is DatabaseResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is DatabaseResult.Success -> emit(Resource.Success(data))
        }
    }

    private suspend fun FlowCollector<Resource<List<Contractor>>>.clearCache(data: List<Contractor>) {
        val result = contractorLocalDataSource.deleteAllContractors()
        when (result) {
            is DatabaseResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is DatabaseResult.Success -> fillCache(data)
        }
    }

    private suspend fun FlowCollector<Resource<List<Contractor>>>.fillCache(data: List<Contractor>) {
        val contractorEntityList = ContractorMapper.mapToContractorEntities(data)
        val result = contractorLocalDataSource.insertContractors(contractorEntityList)
        when (result) {
            is DatabaseResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is DatabaseResult.Success -> emit(Resource.Success(data))
        }
    }

    //todo
    companion object {
        private const val UNKNOWN_ERROR: String = "Unknown Error"
    }
}