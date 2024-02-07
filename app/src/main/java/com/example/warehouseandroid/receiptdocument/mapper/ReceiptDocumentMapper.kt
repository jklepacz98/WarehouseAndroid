package com.example.warehouseandroid.receiptdocument.mapper

import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractor.mapper.ContractorMapper
import com.example.warehouseandroid.receiptdocument.ReceiptDocument
import com.example.warehouseandroid.receiptdocument.local.ReceiptDocumentEntity

class ReceiptDocumentMapper {
    companion object {
        fun mapToReceiptDocumentEntity(receiptDocument: ReceiptDocument): ReceiptDocumentEntity {
            return ReceiptDocumentEntity().apply {
                id = receiptDocument.id
                symbol = receiptDocument.symbol
                //todo
                if (receiptDocument.contractor != null) {
                    contractorEntity =
                        ContractorMapper.mapToContractorEntity(receiptDocument.contractor)
                }
            }
        }

        fun mapToReceiptDocumentEntities(receiptDocumentList: List<ReceiptDocument>): List<ReceiptDocumentEntity> =
            receiptDocumentList.map { receiptDocument -> mapToReceiptDocumentEntity(receiptDocument) }

        fun mapToReceiptDocument(receiptDocumentEntity: ReceiptDocumentEntity): ReceiptDocument {
            val contractorEntity = receiptDocumentEntity.contractorEntity
            val contractor: Contractor? = if (contractorEntity != null) {
                ContractorMapper.mapToContractor(contractorEntity)
            } else {
                null
            }
            return ReceiptDocument(
                receiptDocumentEntity.id!!,
                receiptDocumentEntity.symbol,
                contractor
            )
        }

        fun mapToReceiptDocuments(receiptDocumentEntityList: List<ReceiptDocumentEntity>): List<ReceiptDocument> {
            return receiptDocumentEntityList.map {
                mapToReceiptDocument(it)
            }
        }
    }
}