package com.example.warehouseandroid.contractor.remote

import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.util.ApiResult

interface ContractorRemoteDataSource {
    suspend fun getAllContractors(): ApiResult<List<Contractor>>
    suspend fun getContractor(id: Long): ApiResult<Contractor>
    suspend fun postContractor(contractor: Contractor): ApiResult<Contractor>
    suspend fun putContractor(id: Long, newContractor: Contractor): ApiResult<Contractor>
    suspend fun deleteContractor(id: Long): ApiResult<Unit>
}