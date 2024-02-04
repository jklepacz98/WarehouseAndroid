package com.example.warehouseandroid.contractor

import com.example.warehouseandroid.util.Resource
import kotlinx.coroutines.flow.Flow

interface ContractorDataSource {
    suspend fun getAllContractors(): Flow<Resource<List<Contractor>>>
    suspend fun observeAllContractors(): Flow<Resource<List<Contractor>>>
}