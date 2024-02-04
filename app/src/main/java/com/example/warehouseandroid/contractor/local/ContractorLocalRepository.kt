package com.example.warehouseandroid.contractor.local

import com.example.warehouseandroid.util.LocalResult
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class ContractorLocalRepository(private val contactorDao: ContractorDao) :
    ContractorLocalDataSource {
    override fun observeAllContractors(): Flow<ResultsChange<ContractorEntity>> =
        contactorDao.observeAllContractors()

    override suspend fun insertContractors(contractors: List<ContractorEntity>): LocalResult<Boolean> {
        return try {
            contactorDao.insertContractors(contractors)
            LocalResult.Success(true)
        } catch (e: Exception) {
            LocalResult.Error(e)
        }

    }

    override suspend fun deleteAllContractors(): LocalResult<Boolean> {
        return try {
            contactorDao.deleteAllContractors()
            LocalResult.Success(true)
        } catch (e: Exception) {
            LocalResult.Error(e)
        }

    }
}