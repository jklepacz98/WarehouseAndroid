package com.example.warehouseandroid.home.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.warehouseandroid.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onContractorsButtonClick: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(id = R.string.home_screen)) },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            )
        )
    }) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                HomeCard(stringResource(R.string.contractors), onContractorsButtonClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCard(title: String, onClick: () -> Unit) {
    Card(
        onClick = onClick, modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(text = title, modifier = Modifier.padding(16.dp))
    }
}

