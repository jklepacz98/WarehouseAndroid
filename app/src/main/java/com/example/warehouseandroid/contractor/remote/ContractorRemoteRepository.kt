package com.example.warehouseandroid.contractor.remote

import com.example.warehouseandroid.ApiService
import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.util.ApiResult
import com.example.warehouseandroid.util.handleApi

class ContractorRemoteRepository(private val apiService: ApiService) : ContractorRemoteDataSource {
    override suspend fun getAllContractors(): ApiResult<List<Contractor>> =
        handleApi { apiService.getAllContractors() }
}