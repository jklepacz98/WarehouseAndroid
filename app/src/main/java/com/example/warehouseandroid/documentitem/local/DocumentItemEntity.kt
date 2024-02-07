package com.example.warehouseandroid.documentitem.local

import io.realm.kotlin.types.EmbeddedRealmObject

class DocumentItemEntity : EmbeddedRealmObject {
    var id: Long? = null
    var productName: String? = null
    var unitOfMeasure: String? = null
    var amount: Float? = null
    var receiptDocumentId: Long? = null
}