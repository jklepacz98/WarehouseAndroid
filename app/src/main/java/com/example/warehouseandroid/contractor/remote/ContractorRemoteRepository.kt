package com.example.warehouseandroid.contractor.remote

import com.example.warehouseandroid.ApiService
import com.example.warehouseandroid.util.NetworkResult
import com.example.warehouseandroid.util.handleApi

class ContractorRemoteRepository(private val apiService: ApiService) : ContractorRemoteDataSource {
    override suspend fun getAllContractors(): NetworkResult<List<ContractorDto>> =
        handleApi { apiService.getAllContractors() }
}