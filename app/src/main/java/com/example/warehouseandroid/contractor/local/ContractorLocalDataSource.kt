package com.example.warehouseandroid.contractor.local

import com.example.warehouseandroid.util.LocalResult
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface ContractorLocalDataSource {
    fun observeAllContractors(): Flow<ResultsChange<ContractorEntity>>
    suspend fun insertContractors(contractorEntityList: List<ContractorEntity>): LocalResult<Boolean>
    suspend fun deleteAllContractors(): LocalResult<Boolean>
}