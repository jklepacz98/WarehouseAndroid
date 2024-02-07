package com.example.warehouseandroid.receiptdocument.local

import com.example.warehouseandroid.contractor.local.ContractorEntity
import io.realm.kotlin.types.RealmObject

class ReceiptDocumentEntity : RealmObject {
    var id: Long? = null
    var symbol: String? = null
    var contractorEntity: ContractorEntity? = null
}