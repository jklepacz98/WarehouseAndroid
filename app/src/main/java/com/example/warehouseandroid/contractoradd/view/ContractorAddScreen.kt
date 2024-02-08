@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.example.warehouseandroid.contractoradd.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.warehouseandroid.R
import com.example.warehouseandroid.contractoradd.viewmodel.ContractorAddViewModel
import com.example.warehouseandroid.ui.ErrorToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContractorAddScreen(onGoBackRequested: () -> Unit) {
    val viewModel = koinViewModel<ContractorAddViewModel>()
    val symbol by viewModel.symbol.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val focusRequesterSymbol = remember { FocusRequester() }
    val focusRequesterName = remember { FocusRequester() }
    val errorMessage by viewModel.errorFlow.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val goBack by viewModel.isContractorAdded.collectAsStateWithLifecycle()

    LaunchedEffect(goBack) {
        if (goBack) onGoBackRequested()
    }
    ErrorToast(errorMessage)
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(id = R.string.add_contractor)) },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                IconButton(onClick = { viewModel.postContractor() }) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = stringResource(id = R.string.add_contractor)
                    )
                }
            },
        )
    }) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
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
                        onAny = { focusRequesterName.requestFocus() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .focusRequester(focusRequesterSymbol)

                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { viewModel.setName(it) },
                    label = { Text(text = stringResource(R.string.name)) },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        //todo
                        onAny = { viewModel.postContractor() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .focusRequester(focusRequesterName)
                )
            }
        }
    }
}