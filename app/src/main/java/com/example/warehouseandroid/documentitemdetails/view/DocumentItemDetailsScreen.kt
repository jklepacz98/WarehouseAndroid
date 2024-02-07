@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.warehouseandroid.documentitemdetails.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.documentitemdetails.viewmodel.DocumentItemDetailsViewModel
import com.example.warehouseandroid.ui.ErrorToast
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DocumentItemDetailsScreen(
    documentItemId: Long,
    onEditDocumentItemClick: (String) -> Unit
) {
    val viewModel = koinViewModel<DocumentItemDetailsViewModel> { parametersOf(documentItemId) }
    val documentItem by viewModel.documentItemFlow.collectAsState()
    val errorMessage by viewModel.errorFlow.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    documentItem?.productName ?: stringResource(R.string.no_symbol)
                )
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = { onEditDocumentItemClick(viewModel.serializeDocumentItem()) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(id = R.string.edit_document_item)
                    )
                }
            },
        )
    }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ErrorToast(errorMessage)
            documentItem?.let { DocumentItemDetailsBox(it) }
        }
    }
}

@Composable
fun DocumentItemDetailsBox(documentItem: DocumentItem) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        androidx.compose.material.Text(
            // TODO:
            text = documentItem.amount.toString() + " " + documentItem.unitOfMeasure,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}