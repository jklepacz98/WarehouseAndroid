package com.example.warehouseandroid.receiptdocument.local

import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow

class ReceiptDocumentDao(private val realm: Realm) {
    suspend fun insertReceiptDocuments(receiptDocuments: List<ReceiptDocumentEntity>) {
        realm.write {
            receiptDocuments.forEach { receiptDocument ->
                copyToRealm(receiptDocument)
            }
        }
    }

    suspend fun deleteAllReceiptDocuments() {
        realm.write {
            val receiptDocuments: RealmResults<ReceiptDocumentEntity> =
                this.query(CLASS_NAME).find()
            delete(receiptDocuments)
        }
    }

    fun observeReceiptDocument(id: Long): Flow<SingleQueryChange<ReceiptDocumentEntity>> =
        realm.query(CLASS_NAME, "id == $0", id).first().asFlow()

    suspend fun insertReceiptDocument(receiptDocument: ReceiptDocumentEntity) {
        realm.write {
            copyToRealm(receiptDocument)
        }
    }

    suspend fun deleteReceipt(id: Long) {
        realm.write {
            delete(this.query(CLASS_NAME, "id == $0", id).first())
        }
    }

    fun observeAllReceiptDocuments(): Flow<ResultsChange<ReceiptDocumentEntity>> =
        realm.query(CLASS_NAME).asFlow()

    companion object {
        private val CLASS_NAME = ReceiptDocumentEntity::class
    }
}