package com.example.warehouseandroid.documentitem.mapper

import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.documentitem.local.DocumentItemEntity


class DocumentItemMapper {
    companion object {
        fun mapToDocumentItemEntity(documentItem: DocumentItem): DocumentItemEntity {
            return DocumentItemEntity().apply {
                id = documentItem.id
                productName = documentItem.productName
                unitOfMeasure = documentItem.unitsOfMeasure
                amount = documentItem.amount
                receiptDocumentId = documentItem.receiptDocumentId
            }
        }

        fun mapToDocumentItemEntities(documentItemList: List<DocumentItem>): List<DocumentItemEntity> =
            documentItemList.map { documentItem -> mapToDocumentItemEntity(documentItem) }

        fun mapToDocumentItem(documentItemEntity: DocumentItemEntity): DocumentItem =
            DocumentItem(
                documentItemEntity.id!!,
                documentItemEntity.productName,
                documentItemEntity.unitOfMeasure,
                documentItemEntity.amount,
                documentItemEntity.receiptDocumentId!!
            )

        fun mapToDocumentItems(documentItemEntityList: List<DocumentItemEntity>): List<DocumentItem> {
            return documentItemEntityList.map {
                mapToDocumentItem(it)
            }
        }
    }
}