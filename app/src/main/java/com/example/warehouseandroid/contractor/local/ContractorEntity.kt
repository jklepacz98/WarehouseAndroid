package com.example.warehouseandroid.contractor.local

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ContractorEntity : RealmObject {
    @PrimaryKey
    var id: Long? = null
    var symbol: String? = null
    var name: String? = null
}