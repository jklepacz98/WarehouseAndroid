@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.warehouseandroid.receiptdocumentedit.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.warehouseandroid.R
import com.example.warehouseandroid.receiptdocumentedit.viewmodel.ReceiptDocumentEditViewModel
import com.example.warehouseandroid.ui.ErrorToast
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ReceiptDocumentEditScreen(receiptDocumentJson: String, onGoBackRequested: () -> Unit) {
    val viewModel =
        koinViewModel<ReceiptDocumentEditViewModel> { parametersOf(receiptDocumentJson) }
    val symbol by viewModel.symbol.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorFlow.collectAsStateWithLifecycle()
    val goBack by viewModel.isReceiptDocumentEdited.collectAsStateWithLifecycle()

    LaunchedEffect(goBack) {
        if (goBack) onGoBackRequested()
    }
    ErrorToast(errorMessage)
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(id = R.string.edit_receipt_document)) },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = { (viewModel.putReceiptDocument()) }) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = stringResource(id = R.string.edit_receipt_document)
                    )
                }
            },
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = symbol,
                onValueChange = { viewModel.setSymbol(it) },
                label = { Text(text = stringResource(R.string.symbol)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onAny = { viewModel.putReceiptDocument() }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}