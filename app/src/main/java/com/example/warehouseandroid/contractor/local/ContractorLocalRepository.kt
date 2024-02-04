package com.example.warehouseandroid.contractor.local

import com.example.warehouseandroid.util.DatabaseResult
import com.example.warehouseandroid.util.handleDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ContractorLocalRepository(private val contactorDao: ContractorDao) :
    ContractorLocalDataSource {

    override suspend fun insertContractor(contractorEntity: ContractorEntity): DatabaseResult<Unit> =
        handleDatabase { contactorDao.insertContractor(contractorEntity) }

    override suspend fun deleteContractor(id: Long): DatabaseResult<Unit> =
        handleDatabase { contactorDao.deleteContractor(id) }

    override fun observeContractor(id: Long): Flow<DatabaseResult<ContractorEntity>> = flow {
        try {
            contactorDao.observeContractor(id).collect() { result ->
                val contractorEntity = result.obj
                if (contractorEntity != null) {
                    emit(DatabaseResult.Success(contractorEntity))
                } else {
                    throw Exception(CONTRACTOR_NOT_FOUND)
                }
            }
        } catch (e: Exception) {
            emit(DatabaseResult.Error(e))
        }
    }


    override suspend fun insertContractors(contractors: List<ContractorEntity>): DatabaseResult<Unit> =
        handleDatabase { contactorDao.insertContractors(contractors) }

    override suspend fun deleteAllContractors(): DatabaseResult<Unit> =
        handleDatabase { contactorDao.deleteAllContractors() }

    override fun observeAllContractors(): Flow<DatabaseResult<List<ContractorEntity>>> = flow {
        try {
            contactorDao.observeAllContractors().collect() { result ->
                emit(DatabaseResult.Success(result.list))
            }
        } catch (e: Exception) {
            emit(DatabaseResult.Error(e))
        }

    }

    companion object {
        private const val CONTRACTOR_NOT_FOUND: String = "Contractor not found"
    }
}