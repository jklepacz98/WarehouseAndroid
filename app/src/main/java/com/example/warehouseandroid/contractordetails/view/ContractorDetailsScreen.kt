@file:OptIn(ExperimentalMaterialApi::class)

package com.example.warehouseandroid.contractordetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.warehouseandroid.R
import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractordetails.viewmodel.ContractorDetailsViewModel
import com.example.warehouseandroid.ui.ErrorToast
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ContractorDetailsScreen(contractorId: Long) {
    val viewModel = koinViewModel<ContractorDetailsViewModel> { parametersOf(contractorId) }
    val contractor by viewModel.contractorFlow.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorFlow.collectAsStateWithLifecycle()
    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.refresh() })

    ErrorToast(errorMessage)
    contractor?.let { ContractorDetailsBox(it) }
}

@Composable
fun ContractorDetailsBox(contractor: Contractor) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Text(text = contractor.name ?: stringResource(R.string.no_name))
        Text(text = contractor.symbol ?: stringResource(R.string.no_symbol))
    }
}