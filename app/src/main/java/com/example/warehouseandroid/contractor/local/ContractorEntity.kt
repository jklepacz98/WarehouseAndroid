package com.example.warehouseandroid.contractor.local

import io.realm.kotlin.types.RealmObject

class ContractorEntity : RealmObject {
    var id: Long? = null
    var symbol: String? = null
    var name: String? = null
}