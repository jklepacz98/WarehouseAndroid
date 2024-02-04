package com.example.warehouseandroid.contractor.local

import com.example.warehouseandroid.util.LocalResult
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import kotlinx.coroutines.flow.Flow

interface ContractorLocalDataSource {
    suspend fun insertContractor(contractorEntity: ContractorEntity): LocalResult<Boolean>
    suspend fun deleteContractor(id: Long): LocalResult<Boolean>
    fun observeContractor(id: Long): Flow<SingleQueryChange<ContractorEntity>>
    suspend fun insertContractors(contractorEntityList: List<ContractorEntity>): LocalResult<Boolean>
    suspend fun deleteAllContractors(): LocalResult<Boolean>
    fun observeAllContractors(): Flow<ResultsChange<ContractorEntity>>
}