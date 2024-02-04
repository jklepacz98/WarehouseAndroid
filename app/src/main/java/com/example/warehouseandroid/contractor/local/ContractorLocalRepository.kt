package com.example.warehouseandroid.contractor.local

import com.example.warehouseandroid.util.LocalResult
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import kotlinx.coroutines.flow.Flow

class ContractorLocalRepository(private val contactorDao: ContractorDao) :
    ContractorLocalDataSource {

    override suspend fun insertContractor(contractorEntity: ContractorEntity): LocalResult<Boolean> =
        try {
            contactorDao.insertContractor(contractorEntity)
            LocalResult.Success(true)
        } catch (e: Exception) {
            LocalResult.Error(e)
        }

    override suspend fun deleteContractor(id: Long): LocalResult<Boolean> =
        try {
            contactorDao.deleteContractor(id)
            LocalResult.Success(true)
        } catch (e: Exception) {
            LocalResult.Error(e)
        }

    override fun observeContractor(id: Long): Flow<SingleQueryChange<ContractorEntity>> =
        contactorDao.observeContractor(id)

    override suspend fun insertContractors(contractors: List<ContractorEntity>): LocalResult<Boolean> =
        try {
            contactorDao.insertContractors(contractors)
            LocalResult.Success(true)
        } catch (e: Exception) {
            LocalResult.Error(e)
        }

    override suspend fun deleteAllContractors(): LocalResult<Boolean> =
        try {
            contactorDao.deleteAllContractors()
            LocalResult.Success(true)
        } catch (e: Exception) {
            LocalResult.Error(e)
        }

    override fun observeAllContractors(): Flow<ResultsChange<ContractorEntity>> =
        contactorDao.observeAllContractors()
}