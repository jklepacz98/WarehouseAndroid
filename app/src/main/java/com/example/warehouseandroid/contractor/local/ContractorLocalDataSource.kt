package com.example.warehouseandroid.contractor.local

import com.example.warehouseandroid.util.DatabaseResult
import kotlinx.coroutines.flow.Flow

interface ContractorLocalDataSource {
    suspend fun insertContractor(contractorEntity: ContractorEntity): DatabaseResult<Unit>
    suspend fun deleteContractor(id: Long): DatabaseResult<Unit>
    fun observeContractor(id: Long): Flow<DatabaseResult<ContractorEntity>>
    suspend fun insertContractors(contractorEntityList: List<ContractorEntity>): DatabaseResult<Unit>
    suspend fun deleteAllContractors(): DatabaseResult<Unit>
    fun observeAllContractors(): Flow<DatabaseResult<List<ContractorEntity>>>
}