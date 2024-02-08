package com.example.warehouseandroid

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.warehouseandroid.contractoradd.view.ContractorAddScreen
import com.example.warehouseandroid.contractordetails.view.ContractorDetailsScreen
import com.example.warehouseandroid.contractoredit.view.ContractorEditScreen
import com.example.warehouseandroid.contractorlist.view.ContractorListScreen
import com.example.warehouseandroid.documentitemdetails.view.DocumentItemDetailsScreen
import com.example.warehouseandroid.documentitemedit.view.DocumentItemEditScreen
import com.example.warehouseandroid.home.view.HomeScreen
import com.example.warehouseandroid.receiptdocumentdetails.view.ReceiptDocumentDetailsScreen
import com.example.warehouseandroid.receiptdocumentedit.view.ReceiptDocumentEditScreen
import com.example.warehouseandroid.receiptdocumentlist.view.ReceiptDocumentListScreen
import com.example.warehouseandroid.ui.ErrorToast
import com.example.warehouseandroid.util.navigateToContractorAdd
import com.example.warehouseandroid.util.navigateToContractorDetails
import com.example.warehouseandroid.util.navigateToContractorEdit
import com.example.warehouseandroid.util.navigateToContractorList
import com.example.warehouseandroid.util.navigateToDocumentItemDetails
import com.example.warehouseandroid.util.navigateToDocumentItemEdit
import com.example.warehouseandroid.util.navigateToReceiptDocumentDetails
import com.example.warehouseandroid.util.navigateToReceiptDocumentEdit
import com.example.warehouseandroid.util.navigateToReceiptDocumentList


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        addHomeRoute(navController)
        addContractorListRoute(navController)
        addContractorDetailsRoute(navController)
        addContractorAddRoute(navController)
        addContractorEditRoute(navController)
        addReceiptDocumentListRoute(navController)
        addReceiptDocumentDetailsRoute(navController)
        addReceiptDocumentAddRoute(navController)
        addReceiptDocumentEditRoute(navController)
        addDocumentItemDetailsRoute(navController)
        addDocumentItemAddRoute(navController)
        addDocumentItemEditRoute(navController)
    }
}

private fun NavGraphBuilder.addHomeRoute(navController: NavController) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            onContractorsButtonClick = { navController.navigateToContractorList() },
            onReceiptDocumentsButtonClick = { navController.navigateToReceiptDocumentList() })
    }
}

private fun NavGraphBuilder.addContractorListRoute(navController: NavController) {
    composable(route = Screen.ContractorList.route) {
        ContractorListScreen(
            onContractorClick = { contractorId ->
                navController.navigateToContractorDetails(contractorId)
            },
            onAddContractorClick = { navController.navigateToContractorAdd() })
    }
}

private fun NavGraphBuilder.addContractorDetailsRoute(navController: NavController) {
    composable(route = Screen.ContractorDetails.route + "/{contractorId}") { backStackEntry ->
        val contractorIdString = backStackEntry.arguments?.getString("contractorId")
        val contractorId = contractorIdString?.toLongOrNull()
        if (contractorId != null) {
            ContractorDetailsScreen(contractorId = contractorId,
                onEditContractorClick = { contractorJson ->
                    navController.navigateToContractorEdit(contractorJson)
                })
        } else {
            //todo
            ErrorToast(errorMessage = "Error: Contractor id is null")
        }
    }
}

private fun NavGraphBuilder.addContractorAddRoute(navController: NavController) {
    composable(route = Screen.ContractorAdd.route) {
        ContractorAddScreen { navController.popBackStack() }
    }
}

private fun NavGraphBuilder.addContractorEditRoute(navController: NavController) {
    composable(route = Screen.ContractorEdit.route + "/{contractorJson}") { backStackEntry ->
        val contractorJson = backStackEntry.arguments?.getString("contractorJson")
        if (contractorJson != null) {
            ContractorEditScreen(contractorJson = contractorJson) { navController.popBackStack() }
        } else {
            //todo
            ErrorToast(errorMessage = "Error: Contractor is null")
        }
    }
}

private fun NavGraphBuilder.addReceiptDocumentListRoute(navController: NavController) {
    composable(route = Screen.ReceiptDocumentList.route) {
        // TODO:
        ReceiptDocumentListScreen(onReceiptDocumentClick = { receiptDocumentId ->
            navController.navigateToReceiptDocumentDetails(receiptDocumentId)
        }, onAddReceiptDocumentClick = {})
    }
}

private fun NavGraphBuilder.addReceiptDocumentDetailsRoute(navController: NavController) {
    composable(route = Screen.ReceiptDocumentDetails.route + "/{receiptDocumentId}") { backStackEntry ->
        val receiptDocumentIdString = backStackEntry.arguments?.getString("receiptDocumentId")
        val receiptDocumentId = receiptDocumentIdString?.toLongOrNull()
        if (receiptDocumentId != null) {
            ReceiptDocumentDetailsScreen(
                onDocumentItemClick = { documentItemId ->
                    navController.navigateToDocumentItemDetails(documentItemId)
                },
                onAddDocumentItemClick = {},
                onEditReceiptDocumentClick = { receiptDocumentJson ->
                    navController.navigateToReceiptDocumentEdit(receiptDocumentJson)
                },
                receiptDocumentId = receiptDocumentId
            )
        } else {
            ErrorToast(errorMessage = "Error: Receipt document id is null")
        }
    }
}

private fun NavGraphBuilder.addReceiptDocumentAddRoute(navController: NavController) {

}

private fun NavGraphBuilder.addReceiptDocumentEditRoute(navController: NavController) {
    composable(route = Screen.ReceiptDocumentEdit.route + "/{receiptDocumentJson}") { backStackEntry ->
        val receiptDocumentJson = backStackEntry.arguments?.getString("receiptDocumentJson")
        if (receiptDocumentJson != null) {
            ReceiptDocumentEditScreen(receiptDocumentJson = receiptDocumentJson) { navController.popBackStack() }
        } else {
            //todo
            ErrorToast(errorMessage = "Error: ReceiptDocument is null")
        }
    }
}

private fun NavGraphBuilder.addDocumentItemDetailsRoute(navController: NavController) {
    composable(route = Screen.DocumentItemDetails.route + "/{documentItemId}") { backStackEntry ->
        val documentItemIdString = backStackEntry.arguments?.getString("documentItemId")
        val documentItemId = documentItemIdString?.toLongOrNull()
        if (documentItemId != null) {
            DocumentItemDetailsScreen(
                onEditDocumentItemClick = { documentItemJson ->
                    navController.navigateToDocumentItemEdit(documentItemJson)
                },
                documentItemId = documentItemId
            )
        } else {
            //todo
            ErrorToast(errorMessage = "Error: Document item id is null")
        }
    }
}

private fun NavGraphBuilder.addDocumentItemAddRoute(navController: NavController) {

}

private fun NavGraphBuilder.addDocumentItemEditRoute(navController: NavController) {
    composable(route = Screen.DocumentItemEdit.route + "/{documentItemJson}") { backStackEntry ->
        val documentItemJson = backStackEntry.arguments?.getString("documentItemJson")
        if (documentItemJson != null) {
            DocumentItemEditScreen(
                documentItemJson = documentItemJson,
                onGoBackRequested = { navController.popBackStack() })
        } else {
            //todo
            ErrorToast(errorMessage = "Error: Document item is null")
        }
    }
}

