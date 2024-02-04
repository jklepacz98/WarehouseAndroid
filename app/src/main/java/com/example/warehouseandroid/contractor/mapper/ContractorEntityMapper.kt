package com.example.warehouseandroid.contractor.mapper

import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractor.local.ContractorEntity

class ContractorEntityMapper {
    companion object {
        fun map(contractorList: List<Contractor>): List<ContractorEntity> {
            return contractorList.map {
                //todo
                ContractorEntity().apply {
                    id = it.id
                    symbol = it.symbol
                    name = it.name
                }
            }
        }
    }
}