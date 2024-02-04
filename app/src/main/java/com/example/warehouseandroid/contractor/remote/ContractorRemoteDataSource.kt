package com.example.warehouseandroid.contractor.remote

import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.util.ApiResult

interface ContractorRemoteDataSource {
    suspend fun getAllContractors(): ApiResult<List<Contractor>>
}