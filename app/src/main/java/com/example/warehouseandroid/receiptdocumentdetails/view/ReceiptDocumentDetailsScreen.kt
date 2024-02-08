@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.example.warehouseandroid.receiptdocumentdetails.view

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
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.warehouseandroid.R
import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.receiptdocument.ReceiptDocument
import com.example.warehouseandroid.receiptdocumentdetails.viewmodel.ReceiptDocumentDetailsViewModel
import com.example.warehouseandroid.ui.ErrorToast
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ReceiptDocumentDetailsScreen(
    onDocumentItemClick: (Long) -> Unit,
    onAddDocumentItemClick: (Long) -> Unit,
    onEditReceiptDocumentClick: (String) -> Unit,
    receiptDocumentId: Long
) {
    val viewModel =
        koinViewModel<ReceiptDocumentDetailsViewModel> { parametersOf(receiptDocumentId) }
    val receiptDocument by viewModel.receiptDocumentFlow.collectAsStateWithLifecycle()
    val documentItems by viewModel.documentItemListFlow.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorFlow.collectAsStateWithLifecycle()
    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.refresh() })

    ErrorToast(errorMessage)
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(receiptDocument?.symbol ?: stringResource(R.string.no_symbol)) },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = { onAddDocumentItemClick(receiptDocumentId) }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_receipt_document)
                    )
                }
                IconButton(onClick = { onEditReceiptDocumentClick(viewModel.serializeReceiptDocument()) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(id = R.string.edit_receipt_document)
                    )
                }
            },
        )
    }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            DocumentItemsLazyColumn(
                receiptDocument, documentItems, pullRefreshState, refreshing, onDocumentItemClick
            )
        }
    }
}

@Composable
fun DocumentItemsLazyColumn(
    receiptDocument: ReceiptDocument?,
    documentItemList: List<DocumentItem>,
    pullRefreshState: PullRefreshState,
    refreshing: Boolean,
    onClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        if (receiptDocument != null) {
            item {
                Text(
                    text = stringResource(R.string.contractor),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Text(
                    text = receiptDocument.contractor?.symbol ?: stringResource(R.string.no_symbol),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                Text(
                    text = receiptDocument.contractor?.name ?: stringResource(R.string.no_name),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
        items(documentItemList) { documentItem ->
            DocumentItemCard(documentItem, onClick)
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
fun DocumentItemCard(documentItem: DocumentItem, onClick: (Long) -> Unit) {
    Card(
        shape = MaterialTheme.shapes.medium,
        onClick = { onClick(documentItem.id) },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = documentItem.productName ?: stringResource(R.string.no_product_name))
            Text(text = documentItem.amount?.toString() + " " + documentItem.unitOfMeasure?.toString())
        }
    }
}