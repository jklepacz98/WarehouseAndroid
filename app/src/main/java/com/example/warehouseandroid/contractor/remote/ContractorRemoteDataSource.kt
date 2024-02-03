package com.example.warehouseandroid.contractor.remote

import com.example.warehouseandroid.util.NetworkResult

interface ContractorRemoteDataSource {
    suspend fun getAllContractors(): NetworkResult<List<ContractorDto>>
}