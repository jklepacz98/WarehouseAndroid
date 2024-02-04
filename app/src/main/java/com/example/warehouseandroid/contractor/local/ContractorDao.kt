package com.example.warehouseandroid.contractor.local

import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow

class ContractorDao(private val realm: Realm) {
    suspend fun insertContractors(contractors: List<ContractorEntity>) {
        realm.write {
            contractors.forEach { contractorLocal ->
                copyToRealm(contractorLocal)
            }
        }
    }

    suspend fun deleteAllContractors() {
        realm.write {
            val contractors: RealmResults<ContractorEntity> = this.query(CLASS_NAME).find()
            delete(contractors)
        }
    }

    fun observeAllContractors(): Flow<ResultsChange<ContractorEntity>> =
        realm.query(CLASS_NAME).asFlow()

    companion object {
        private val CLASS_NAME = ContractorEntity::class
    }
}