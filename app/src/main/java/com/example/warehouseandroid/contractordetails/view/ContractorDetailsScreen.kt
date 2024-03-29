@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.example.warehouseandroid.contractordetails.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    onEditContractorClick: (String) -> Unit
) {
    val viewModel = koinViewModel<ContractorDetailsViewModel> { parametersOf(contractorId) }
    val contractor by viewModel.contractorFlow.collectAsState()
    val errorMessage by viewModel.errorFlow.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                androidx.compose.material3.Text(
                    contractor?.symbol ?: stringResource(R.string.no_symbol)
                )
            },
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
}

@Composable
fun ContractorDetailsBox(contractor: Contractor) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = contractor.name ?: stringResource(id = R.string.no_name),
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}