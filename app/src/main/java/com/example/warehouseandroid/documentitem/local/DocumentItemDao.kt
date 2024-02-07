package com.example.warehouseandroid.documentitem.local

import io.realm.kotlin.Realm
import io.realm.kotlin.notifications.SingleQueryChange
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow

class DocumentItemDao(private val realm: Realm) {

    suspend fun insertDocumentItem(documentItem: DocumentItemEntity) {
        realm.write {
            copyToRealm(documentItem)
        }
    }

    suspend fun deleteDocumentItem(id: Long) {
        realm.write {
            delete(this.query(CLASS_NAME, "id == $0", id).first())
        }
    }

    fun observeDocumentItem(id: Long): Flow<SingleQueryChange<DocumentItemEntity>> =
        realm.query(CLASS_NAME, "id == $0", id).first().asFlow()

    suspend fun insertDocumentItems(documentItems: List<DocumentItemEntity>) {
        realm.write {
            documentItems.forEach { documentItem ->
                copyToRealm(documentItem)
            }
        }
    }

    suspend fun deleteDocumentItems(receiptDocumentId: Long) {
        realm.write {
            val documentItems: RealmResults<DocumentItemEntity> =
                this.query(CLASS_NAME, "receiptDocumentId == $0", receiptDocumentId).find()
            delete(documentItems)
        }
    }

    fun observeDocumentItems(receiptDocumentId: Long) =
        realm.query(CLASS_NAME, "receiptDocumentId == $0", receiptDocumentId).asFlow()


    companion object {
        private val CLASS_NAME = DocumentItemEntity::class
    }
}