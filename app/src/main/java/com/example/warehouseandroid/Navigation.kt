package com.example.warehouseandroid

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.warehouseandroid.contractoradd.view.ContractorAddScreen
import com.example.warehouseandroid.contractordetails.view.ContractorDetailsScreen
import com.example.warehouseandroid.contractoredit.view.ContractorEditScreen
import com.example.warehouseandroid.contractorlist.view.ContractorListScreen
import com.example.warehouseandroid.documentitemdetails.view.DocumentItemDetailsScreen
import com.example.warehouseandroid.home.view.HomeScreen
import com.example.warehouseandroid.receiptdocumentdetails.view.ReceiptDocumentDetailsScreen
import com.example.warehouseandroid.receiptdocumentedit.view.ReceiptDocumentEditScreen
import com.example.warehouseandroid.receiptdocumentlist.view.ReceiptDocumentListScreen
import com.example.warehouseandroid.ui.ErrorToast


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(onContractorsButtonClick = { navController.navigate(Screen.ContractorList.route) },
                onReceiptDocumentsButtonClick = { navController.navigate(Screen.ReceiptDocumentList.route) })
        }
        composable(route = Screen.ContractorList.route) {
            ContractorListScreen(onContractorClick = { contractorId ->
                navController.navigate(Screen.ContractorDetails.route + "/$contractorId")
            }, onAddContractorClick = {
                navController.navigate(Screen.ContractorAdd.route)
            })
        }
        composable(route = Screen.ContractorDetails.route + "/{contractorId}") { backStackEntry ->
            val contractorIdString = backStackEntry.arguments?.getString("contractorId")
            val contractorId = contractorIdString?.toLongOrNull()
            if (contractorId != null) {
                ContractorDetailsScreen(contractorId = contractorId,
                    onEditContractorClick = { contractorJson -> navController.navigate(Screen.ContractorEdit.route + "/$contractorJson") })
            } else {
                //todo
                ErrorToast(errorMessage = "Error: Contractor id is null")
            }
        }
        composable(route = Screen.ContractorEdit.route + "/{contractorJson}") { backStackEntry ->
            val contractorJson = backStackEntry.arguments?.getString("contractorJson")
            if (contractorJson != null) {
                ContractorEditScreen(contractorJson = contractorJson) { navController.popBackStack() }
            } else {
                //todo
                ErrorToast(errorMessage = "Error: Contractor is null")
            }
        }
        composable(route = Screen.ContractorAdd.route) {
            ContractorAddScreen { navController.popBackStack() }
        }
        composable(route = Screen.ReceiptDocumentList.route) {
            // TODO:
            ReceiptDocumentListScreen(onReceiptDocumentClick = { receiptDocumentId ->
                navController.navigate(Screen.ReceiptDocumentDetails.route + "/$receiptDocumentId")
            }, onAddReceiptDocumentClick = {})
        }
        //todo
        composable(route = Screen.ReceiptDocumentDetails.route + "/{receiptDocumentId}") { backStackEntry ->
            val receiptDocumentIdString = backStackEntry.arguments?.getString("receiptDocumentId")
            val receiptDocumentId = receiptDocumentIdString?.toLongOrNull()
            if (receiptDocumentId != null) {
                ReceiptDocumentDetailsScreen(
                    onDocumentItemClick = { documentItemId ->
                        navController.navigate(Screen.DocumentItemDetails.route + "/$documentItemId")
                    },
                    onAddDocumentItemClick = {},
                    onEditReceiptDocumentClick = { receiptDocumentJson ->
                        navController.navigate(
                            Screen.ReceiptDocumentEdit.route + "/$receiptDocumentJson"
                        )
                    },
                    receiptDocumentId = receiptDocumentId
                )
            } else {
                ErrorToast(errorMessage = "Error: Receipt document id is null")
            }
        }
        composable(route = Screen.ReceiptDocumentEdit.route + "/{receiptDocumentJson}") { backStackEntry ->
            val receiptDocumentJson = backStackEntry.arguments?.getString("receiptDocumentJson")
            if (receiptDocumentJson != null) {
                ReceiptDocumentEditScreen(receiptDocumentJson = receiptDocumentJson) { navController.popBackStack() }
            } else {
                //todo
                ErrorToast(errorMessage = "Error: ReceiptDocument is null")
            }
        }
        composable(route = Screen.DocumentItemDetails.route + "/{documentItemId}") { backStackEntry ->
            val documentItemIdString = backStackEntry.arguments?.getString("documentItemId")
            val documentItemId = documentItemIdString?.toLongOrNull()
            if (documentItemId != null) {
                DocumentItemDetailsScreen(
                    onEditDocumentItemClick = { documentItemJson ->
                        //todo
//                        navController.navigate(
//                            Screen.DocumentItemEdit.route + "/$documentItemJson"
//                        )
                    },
                    documentItemId = documentItemId
                )
            } else {
                //todo
                ErrorToast(errorMessage = "Error: Document item id is null")
            }
        }
    }
}