package com.example.warehouseandroid

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.warehouseandroid.contractorlist.view.ContractorListScreen
import com.example.warehouseandroid.home.view.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen { navController.navigate(Screen.ContractorList.route) }
        }
        composable(route = Screen.ContractorList.route) {
            ContractorListScreen()
        }
    }
}