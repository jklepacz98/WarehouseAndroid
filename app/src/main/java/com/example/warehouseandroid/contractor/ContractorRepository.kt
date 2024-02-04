package com.example.warehouseandroid.contractor

import com.example.warehouseandroid.contractor.local.ContractorLocalDataSource
import com.example.warehouseandroid.contractor.mapper.ContractorEntityMapper
import com.example.warehouseandroid.contractor.mapper.ContractorMapper
import com.example.warehouseandroid.contractor.remote.ContractorRemoteDataSource
import com.example.warehouseandroid.util.ApiResult
import com.example.warehouseandroid.util.LocalResult
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
            is ApiResult.Success -> clearCache(networkResult.data)
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

    override suspend fun deleteContractor(id: Long): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val networkResult = contractorRemoteDataSource.deleteContractor(id)
        when (networkResult) {
            //todo
            is ApiResult.Success -> {}
            is ApiResult.Error -> emit(Resource.Error(networkResult.message))
            is ApiResult.Exception -> emit(Resource.Error(networkResult.e.message ?: UNKNOWN_ERROR))
        }
    }

    private suspend fun FlowCollector<Resource<Contractor>>.clearCache(data: Contractor) {
        val result = contractorLocalDataSource.deleteContractor(data.id)
        when (result) {
            is LocalResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is LocalResult.Success -> fillCache(data)
        }
    }

    private suspend fun FlowCollector<Resource<Contractor>>.fillCache(data: Contractor) {
        val contractorEntity = ContractorEntityMapper.mapToContractorEntity(data)
        val result = contractorLocalDataSource.insertContractor(contractorEntity)
        when (result) {
            is LocalResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is LocalResult.Success -> emit(Resource.Success(data))
        }
    }

    override suspend fun observeContractor(id: Long): Flow<Resource<Contractor>> = flow {
        emit(Resource.Loading())
        contractorLocalDataSource.observeContractor(id).collect { result ->
            val contractorEntity = result.obj
            if (contractorEntity != null) {
                val data = ContractorMapper.mapToContractor(contractorEntity)
                emit(Resource.Success(data))
            } else {
                //todo
                emit(Resource.Error(CONTRACTOR_NOT_FOUND))
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

    private suspend fun FlowCollector<Resource<List<Contractor>>>.clearCache(data: List<Contractor>) {
        val result = contractorLocalDataSource.deleteAllContractors()
        when (result) {
            is LocalResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is LocalResult.Success -> fillCache(data)
        }
    }

    private suspend fun FlowCollector<Resource<List<Contractor>>>.fillCache(data: List<Contractor>) {
        val contractorEntityList = ContractorEntityMapper.mapToContractorEntities(data)
        val result = contractorLocalDataSource.insertContractors(contractorEntityList)
        when (result) {
            is LocalResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is LocalResult.Success -> emit(Resource.Success(data))
        }
    }


    override suspend fun observeAllContractors(): Flow<Resource<List<Contractor>>> = flow {
        emit(Resource.Loading())
        contractorLocalDataSource.observeAllContractors().collect { result ->
            val data = ContractorMapper.mapToContractors(result.list)
            emit(Resource.Success(data))
        }
    }

    //todo
    companion object {
        private const val UNKNOWN_ERROR: String = "UnknownError"
        private const val CONTRACTOR_NOT_FOUND: String = "Contractor not found"
    }
}