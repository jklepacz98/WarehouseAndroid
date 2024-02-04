package com.example.warehouseandroid.contractor.mapper

import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractor.local.ContractorEntity

class ContractorEntityMapper {
    companion object {
        fun mapToContractorEntity(contractor: Contractor): ContractorEntity {
            return ContractorEntity().apply {
                id = contractor.id
                symbol = contractor.symbol
                name = contractor.name
            }
        }

        fun mapToContractorEntities(contractorList: List<Contractor>): List<ContractorEntity> =
            contractorList.map { contractor -> mapToContractorEntity(contractor) }

    }
}