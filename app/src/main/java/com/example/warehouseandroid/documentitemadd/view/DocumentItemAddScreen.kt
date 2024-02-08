@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.warehouseandroid.documentitemadd.view

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.warehouseandroid.R
import com.example.warehouseandroid.documentitemadd.viewmodel.DocumentItemAddViewModel
import com.example.warehouseandroid.ui.ErrorToast
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DocumentItemAddScreen(onGoBackRequested: () -> Unit, receiptDocumentId: Long) {
    val viewModel = koinViewModel<DocumentItemAddViewModel> { parametersOf(receiptDocumentId) }
    val productName by viewModel.productName.collectAsStateWithLifecycle()
    val unitOfMeasure by viewModel.unitOfMeasure.collectAsStateWithLifecycle()
    val amount by viewModel.amount.collectAsStateWithLifecycle()
    val focusRequesterProductName = remember { FocusRequester() }
    val focusRequesterUnitOfMeasure = remember { FocusRequester() }
    val focusRequesterAmount = remember { FocusRequester() }
    val errorMessage by viewModel.errorFlow.collectAsStateWithLifecycle()
    val goBack by viewModel.isDocumentItemEdited.collectAsStateWithLifecycle()

    LaunchedEffect(goBack) {
        if (goBack) onGoBackRequested()
    }
    ErrorToast(errorMessage)
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(id = R.string.add_document_item)) },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = { (viewModel.postDocumentItem()) }) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = stringResource(id = R.string.add_document_item)
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
                value = productName,
                onValueChange = { viewModel.setProductName(it) },
                label = { Text(text = stringResource(R.string.product_name)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onAny = { focusRequesterUnitOfMeasure.requestFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequesterProductName)
            )
            OutlinedTextField(
                value = unitOfMeasure,
                onValueChange = { viewModel.setUnitOfMeasure(it) },
                label = { Text(text = stringResource(R.string.unit_of_measure)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    //todo
                    onAny = { focusRequesterAmount.requestFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequesterUnitOfMeasure)
            )
            OutlinedTextField(
                value = amount,
                onValueChange = { viewModel.setAmount(it) },
                label = { Text(text = stringResource(R.string.amount)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number).copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    //todo
                    onAny = { viewModel.postDocumentItem() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequesterUnitOfMeasure)
            )
        }
    }
}