package com.example.warehouseandroid.contractor.mapper

import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractor.local.ContractorEntity

class ContractorMapper {
    companion object {
        fun map(contractorEntityList: List<ContractorEntity>): List<Contractor> {
            return contractorEntityList.map {
                //todo
                Contractor(it.id!!, it.symbol, it.name)
            }
        }
    }
}