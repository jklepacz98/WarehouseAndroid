@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.warehouseandroid.contractoredit.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContractorEditScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TextField(value = "", onValueChange = {}, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))
        TextField(value = "", onValueChange = {}, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))
    }
}