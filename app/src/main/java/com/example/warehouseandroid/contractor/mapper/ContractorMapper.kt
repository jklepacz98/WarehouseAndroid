package com.example.warehouseandroid.contractor.mapper

import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractor.local.ContractorEntity

class ContractorMapper {
    companion object {
        fun mapToContractor(contractorEntity: ContractorEntity): Contractor =
            //todo !!
            Contractor(contractorEntity.id!!, contractorEntity.symbol, contractorEntity.name)

        fun mapToContractors(contractorEntityList: List<ContractorEntity>): List<Contractor> {
            return contractorEntityList.map {
                mapToContractor(it)
            }
        }
    }
}