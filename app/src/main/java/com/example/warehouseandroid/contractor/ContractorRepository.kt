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

    //todo Tidy this function
    override suspend fun getAllContractors(): Flow<Resource<List<Contractor>>> = flow {
        emit(Resource.Loading())
        val networkResult = contractorRemoteDataSource.getAllContractors()
        when (networkResult) {
            is ApiResult.Success -> deleteResult(networkResult.data)
            is ApiResult.Error -> emit(Resource.Error(networkResult.message))
            //todo Remove !!
            is ApiResult.Exception -> emit(Resource.Error(networkResult.e.message ?: UNKNOWN_ERROR))

        }
    }

    //todo change name
    private suspend fun FlowCollector<Resource<List<Contractor>>>.deleteResult(data: List<Contractor>) {
        //todo Remove !!
        val result = contractorLocalDataSource.deleteAllContractors()
        when (result) {
            //todo
            is LocalResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is LocalResult.Success -> insertResult(data)
        }
    }

    private suspend fun FlowCollector<Resource<List<Contractor>>>.insertResult(data: List<Contractor>) {
        val contractorEntityList = ContractorEntityMapper.map(data)
        val result = contractorLocalDataSource.insertContractors(contractorEntityList)
        when (result) {
            //todo Remove!!
            is LocalResult.Error -> emit(Resource.Error(result.e.message ?: UNKNOWN_ERROR))
            is LocalResult.Success -> emit(Resource.Success(data))
        }
    }


    override suspend fun observeAllContractors(): Flow<Resource<List<Contractor>>> = flow {
        emit(Resource.Loading())
        contractorLocalDataSource.observeAllContractors().collect { result ->
            val data = ContractorMapper.map(result.list)
            emit(Resource.Success(data))
        }
    }

    //todo
    companion object {
        private val UNKNOWN_ERROR: String = "UnknownError"
    }
}