package com.example.warehouseandroid.contractor.local

import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow

class ContractorDao(private val realm: Realm) {
    suspend fun insertContractors(contractors: List<ContractorEntity>) {
        realm.write {
            contractors.forEach { contractor ->
                copyToRealm(contractor)
            }
        }
    }

    suspend fun deleteAllContractors() {
        realm.write {
            val contractors: RealmResults<ContractorEntity> = this.query(CLASS_NAME).find()
            delete(contractors)
        }
    }

    //todo Not sure if query is right
    fun observeContractor(id: Long): Flow<SingleQueryChange<ContractorEntity>> =
        realm.query(CLASS_NAME, "id == $0", id).first().asFlow()

    suspend fun insertContractor(contractor: ContractorEntity) {
        realm.write {
            copyToRealm(contractor)
        }
    }

    suspend fun deleteContractor(id: Long) {
        realm.write {
            delete(this.query(CLASS_NAME, "id == $0", id).first())
        }
    }

    fun observeAllContractors(): Flow<ResultsChange<ContractorEntity>> =
        realm.query(CLASS_NAME).asFlow()

    companion object {
        private val CLASS_NAME = ContractorEntity::class
    }
}