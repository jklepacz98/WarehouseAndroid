package com.example.warehouseandroid

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.warehouseandroid.contractordetails.view.ContractorDetailsScreen
import com.example.warehouseandroid.contractoredit.view.ContractorEditScreen
import com.example.warehouseandroid.contractorlist.view.ContractorListScreen
import com.example.warehouseandroid.home.view.HomeScreen
import com.example.warehouseandroid.ui.ErrorToast

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen { navController.navigate(Screen.ContractorList.route) }
        }
        composable(route = Screen.ContractorList.route) {
            ContractorListScreen(
                onContractorClick = { contractorId ->
                    navController.navigate(Screen.ContractorDetails.route + "/$contractorId")
                },
                onAddContractorClick = {
                    navController.navigate(Screen.ContractorEdit.route)
                }
            )
        }
        composable(route = Screen.ContractorDetails.route + "/{contractorId}") { backStackEntry ->
            val contractorIdString = backStackEntry.arguments?.getString("contractorId")
            val contractorId = contractorIdString?.toLongOrNull()
            if (contractorId != null) {
                ContractorDetailsScreen(contractorId = contractorId) { navController.popBackStack() }
            } else {
                //todo
                ErrorToast(errorMessage = "Contractor id is null")
            }
        }
        composable(route = Screen.ContractorEdit.route) {
            ContractorEditScreen()
        }
    }
}