package com.example.warehouseandroid.contractor.local

import com.example.warehouseandroid.util.DatabaseResult
import kotlinx.coroutines.flow.Flow

interface ContractorLocalDataSource {
    suspend fun insertContractor(contractorEntity: ContractorEntity): DatabaseResult<Boolean>
    suspend fun deleteContractor(id: Long): DatabaseResult<Boolean>
    fun observeContractor(id: Long): Flow<DatabaseResult<ContractorEntity>>
    suspend fun insertContractors(contractorEntityList: List<ContractorEntity>): DatabaseResult<Boolean>
    suspend fun deleteAllContractors(): DatabaseResult<Boolean>
    fun observeAllContractors(): Flow<DatabaseResult<List<ContractorEntity>>>
}