package com.example.warehouseandroid

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.warehouseandroid.contractoradd.view.ContractorAddScreen
import com.example.warehouseandroid.contractordetails.view.ContractorDetailsScreen
import com.example.warehouseandroid.contractoredit.view.ContractorEditScreen
import com.example.warehouseandroid.contractorlist.view.ContractorListScreen
import com.example.warehouseandroid.home.view.HomeScreen
import com.example.warehouseandroid.receiptdocumentlist.view.ReceiptDocumentListScreen
import com.example.warehouseandroid.ui.ErrorToast


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onContractorsButtonClick = { navController.navigate(Screen.ContractorList.route) },
                onReceiptDocumentsButtonClick = { navController.navigate(Screen.ReceiptDocumentList.route) }
            )
        }
        composable(route = Screen.ContractorList.route) {
            ContractorListScreen(
                onContractorClick = { contractorId ->
                    navController.navigate(Screen.ContractorDetails.route + "/$contractorId")
                },
                onAddContractorClick = {
                    navController.navigate(Screen.ContractorAdd.route)
                }
            )
        }
        composable(route = Screen.ContractorDetails.route + "/{contractorId}") { backStackEntry ->
            val contractorIdString = backStackEntry.arguments?.getString("contractorId")
            val contractorId = contractorIdString?.toLongOrNull()
            if (contractorId != null) {
                ContractorDetailsScreen(
                    contractorId = contractorId,
                    onGoBackRequested = { navController.popBackStack() },
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
            ReceiptDocumentListScreen(onReceiptDocumentClick = {}, onAddReceiptDocumentClick = {})
        }
    }
}