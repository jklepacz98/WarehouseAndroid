package com.example.warehouseandroid.util

import androidx.navigation.NavController
import com.example.warehouseandroid.Screen

fun NavController.navigateToContractorList() {
    navigate(Screen.ContractorList.route)
}

fun NavController.navigateToContractorDetails(contractorId: Long) {
    navigate(Screen.ContractorDetails.route + "/$contractorId")
}

fun NavController.navigateToContractorAdd() {
    navigate(Screen.ContractorAdd.route)
}

fun NavController.navigateToContractorEdit(contractorJson: String) {
    navigate(Screen.ContractorEdit.route + "/$contractorJson")
}

fun NavController.navigateToReceiptDocumentList() {
    navigate(Screen.ReceiptDocumentList.route)
}

fun NavController.navigateToReceiptDocumentDetails(receiptDocumentId: Long) {
    navigate(Screen.ReceiptDocumentDetails.route + "/$receiptDocumentId")
}

fun NavController.navigateToReceiptDocumentAdd() {
    //todo
}

fun NavController.navigateToReceiptDocumentEdit(receiptDocumentJson: String) {
    navigate(Screen.ReceiptDocumentEdit.route + "/$receiptDocumentJson")
}

fun NavController.navigateToDocumentItemDetails(documentItemId: Long) {
    navigate(Screen.DocumentItemDetails.route + "/$documentItemId")
}

fun NavController.navigateToDocumentItemAdd(receiptDocumentId: Long) {
    navigate(Screen.DocumentItemAdd.route + "/$receiptDocumentId")
}

fun NavController.navigateToDocumentItemEdit(documentItemJson: String) {
    navigate(Screen.DocumentItemEdit.route + "/$documentItemJson")
}