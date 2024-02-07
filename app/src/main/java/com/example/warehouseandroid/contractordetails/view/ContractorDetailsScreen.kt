@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.example.warehouseandroid.contractordetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
fun ContractorDetailsScreen(
    contractorId: Long,
    onGoBackRequested: () -> Unit,
    onEditContractorClick: (String) -> Unit
) {
    val viewModel = koinViewModel<ContractorDetailsViewModel> { parametersOf(contractorId) }
    val contractor by viewModel.contractorFlow.collectAsState()
    val errorMessage by viewModel.errorFlow.collectAsStateWithLifecycle()
    //todo
    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val goBack by viewModel.goBack.collectAsStateWithLifecycle()
    //todo
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.refresh() })

    Scaffold(topBar = {
        TopAppBar(
            title = { androidx.compose.material3.Text(stringResource(id = R.string.contractor)) },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = { onEditContractorClick(viewModel.serializeContractor()) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(id = R.string.edit_contractor)
                    )
                }
            },
        )
    }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ErrorToast(errorMessage)
            contractor?.let { ContractorDetailsBox(it) }
        }
    }

    LaunchedEffect(goBack) {
        if (goBack) onGoBackRequested()
    }
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