package com.example.warehouseandroid.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.warehouseandroid.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onContractorsButtonClick: () -> Unit, onReceiptDocumentsButtonClick: () -> Unit) {
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
                val iconModifier = Modifier.padding(16.dp)
                HomeCard(
                    icon = {
                        IconWithDescription(Icons.Filled.Person, R.string.contractors, iconModifier)
                    },
                    title = stringResource(R.string.contractors),
                    onClick = onContractorsButtonClick,
                )
                HomeCard(
                    icon = {
                        IconWithDescription(
                            Icons.Filled.Receipt, R.string.receipt_documents, iconModifier
                        )
                    },
                    title = stringResource(R.string.receipt_documents),
                    onClick = onReceiptDocumentsButtonClick
                )
            }
        }
    }
}

@Composable
fun IconWithDescription(
    imageVector: ImageVector, descriptionResId: Int, modifier: Modifier = Modifier
) {
    Icon(
        imageVector = imageVector,
        contentDescription = stringResource(descriptionResId),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCard(icon: @Composable () -> Unit, title: String, onClick: () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // This centers the contents vertically within the Row
            horizontalArrangement = Arrangement.Center // This centers the contents horizontally within the Row
        ) {
            icon()
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

