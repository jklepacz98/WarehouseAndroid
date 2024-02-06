package com.example.warehouseandroid

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object ContractorList : Screen("contractor_list_screen")
    object ContractorDetails : Screen("contractor_details_screen")
    object ContractorEdit : Screen("contractor_edit_screen")
    object ReceiptDocumentList : Screen("receipt_document_screen")
    object ReceiptDocumentDetails : Screen("receipt_document_details")
    object DocumentItemDetails : Screen("document_item_details")
}