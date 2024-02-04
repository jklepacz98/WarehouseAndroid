package com.example.warehouseandroid.contractor.remote

import com.example.warehouseandroid.ApiService
import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.util.ApiResult
import com.example.warehouseandroid.util.handleApi

class ContractorRemoteRepository(private val apiService: ApiService) : ContractorRemoteDataSource {
    override suspend fun getAllContractors(): ApiResult<List<Contractor>> =
        handleApi { apiService.getAllContractors() }

    override suspend fun getContractor(id: Long): ApiResult<Contractor> =
        handleApi { apiService.getContractor(id) }

    override suspend fun postContractor(contractor: Contractor): ApiResult<Contractor> =
        handleApi { apiService.postContractor(contractor) }

    override suspend fun putContractor(id: Long, contractor: Contractor): ApiResult<Contractor> =
        handleApi { apiService.putContractor(id, contractor) }

    override suspend fun deleteContractor(id: Long): ApiResult<Unit> =
        handleApi { apiService.deleteContractor(id) }
}