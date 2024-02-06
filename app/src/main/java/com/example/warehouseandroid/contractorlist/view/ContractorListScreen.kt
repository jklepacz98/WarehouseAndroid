@file:OptIn(ExperimentalMaterialApi::class)

package com.example.warehouseandroid.contractorlist.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.warehouseandroid.R
import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.contractorlist.viewmodel.ContractorListViewModel
import com.example.warehouseandroid.ui.ErrorToast
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContractorListScreen(onContractorClick: (Long) -> Unit, onAddContractorClick: () -> Unit) {
    val viewModel = koinViewModel<ContractorListViewModel>()
    val contractors by viewModel.contractorListFlow.collectAsStateWithLifecycle(emptyList())
    val errorMessage by viewModel.errorFlow.collectAsStateWithLifecycle()
    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.refresh() })


    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(id = R.string.contractors)) },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = { onAddContractorClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_contractor)
                    )
                }
            },

            )
    }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ContractorsLazyColumn(
                contractors,
                pullRefreshState,
                refreshing,
                onContractorClick
            )
        }
        ErrorToast(errorMessage)
    }
}

@Composable
fun ContractorsLazyColumn(
    contractorList: List<Contractor>,
    pullRefreshState: PullRefreshState,
    refreshing: Boolean,
    onClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        items(contractorList) { contractor ->
            ContractorCard(contractor, onClick)
        }
    }
    PullRefreshIndicator(
        refreshing = refreshing,
        state = pullRefreshState,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContractorCard(contractor: Contractor, onClick: (Long) -> Unit) {
    Card(
        onClick = { onClick(contractor.id) },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = contractor.name ?: stringResource(R.string.no_name))
            Text(text = contractor.symbol ?: stringResource(R.string.no_symbol))
        }
    }
}
