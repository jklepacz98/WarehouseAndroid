package com.example.warehouseandroid.contractor.local

import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.query.RealmSingleQuery
import kotlinx.coroutines.flow.Flow

class ContractorDao(private val realm: Realm) {
    suspend fun insertContractors(contractors: List<ContractorEntity>) {
        realm.write {
            contractors.forEach { contractorEntity ->
                copyToRealm(contractorEntity)
            }
        }
    }

    suspend fun deleteAllContractors() {
        realm.write {
            val contractors: RealmResults<ContractorEntity> = this.query(CLASS_NAME).find()
            delete(contractors)
        }
    }

    private fun queryContractor(id: Long?): RealmSingleQuery<ContractorEntity> =
        realm.query(CLASS_NAME, "id == $0", id).first()

    fun observeContractor(id: Long): Flow<SingleQueryChange<ContractorEntity>> =
        queryContractor(id).asFlow()

    suspend fun insertContractor(contractorEntity: ContractorEntity) {
        realm.write {
            copyToRealm(contractorEntity)
        }
    }

    suspend fun deleteContractor(id: Long) {
        realm.write {
            delete(queryContractor(id))
        }
    }

    fun observeAllContractors(): Flow<ResultsChange<ContractorEntity>> =
        realm.query(CLASS_NAME).asFlow()

    companion object {
        private val CLASS_NAME = ContractorEntity::class
    }
}