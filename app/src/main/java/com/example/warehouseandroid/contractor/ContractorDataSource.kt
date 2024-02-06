package com.example.warehouseandroid.contractor

import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.Flow

interface ContractorDataSource {
    suspend fun getContractor(id: Long): Flow<Resource<Contractor>>
    suspend fun postContractor(contractor: Contractor): Flow<Resource<Contractor>>
    suspend fun putContractor(id: Long, contractor: Contractor): Flow<Resource<Contractor>>
    suspend fun getAllContractors(): Flow<Resource<List<Contractor>>>
    suspend fun observeAllContractors(): Flow<Resource<List<Contractor>>>
    suspend fun observeContractor(id: Long): Flow<Resource<Contractor>>
}