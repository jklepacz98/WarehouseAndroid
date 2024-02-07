@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.example.warehouseandroid.receiptdocumentlist.view

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
import com.example.warehouseandroid.receiptdocument.ReceiptDocument
import com.example.warehouseandroid.receiptdocumentlist.viewmodel.ReceiptDocumentListViewModel
import com.example.warehouseandroid.ui.ErrorToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReceiptDocumentListScreen(
    onReceiptDocumentClick: (Long) -> Unit, onAddReceiptDocumentClick: () -> Unit
) {
    val viewModel = koinViewModel<ReceiptDocumentListViewModel>()
    val receiptDocuments by viewModel.receiptDocumentListFlow.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorFlow.collectAsStateWithLifecycle()
    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.refresh() })

    ErrorToast(errorMessage)
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(id = R.string.receipt_documents)) },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = { onAddReceiptDocumentClick() }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_receipt_document)
                    )
                }
            },

            )
    }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ReceiptDocumentsLazyColumn(
                receiptDocuments, pullRefreshState, refreshing, onReceiptDocumentClick
            )
        }
    }
}

@Composable
fun ReceiptDocumentsLazyColumn(
    receiptDocumentList: List<ReceiptDocument>,
    pullRefreshState: PullRefreshState,
    refreshing: Boolean,
    onClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        items(receiptDocumentList) { receiptDocument ->
            ReceiptDocumentCard(receiptDocument, onClick)
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

@Composable
fun ReceiptDocumentCard(receiptDocument: ReceiptDocument, onClick: (Long) -> Unit) {
    Card(
        shape = MaterialTheme.shapes.medium,
        onClick = { onClick(receiptDocument.id) },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = receiptDocument.symbol ?: stringResource(R.string.no_symbol),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(text = stringResource(R.string.contractor))
            Text(text = receiptDocument.contractor?.name ?: stringResource(R.string.no_name))
            Text(text = receiptDocument.contractor?.symbol ?: stringResource(R.string.no_symbol))
        }
    }
}