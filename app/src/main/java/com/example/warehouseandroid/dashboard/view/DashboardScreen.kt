package com.example.warehouseandroid.dashboard.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.warehouseandroid.contractor.remote.ContractorDto
import com.example.warehouseandroid.dashboard.viewmodel.DashboardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen() {
    val dashboardViewModel = koinViewModel<DashboardViewModel>()
    val contractors by dashboardViewModel.contractorList.collectAsState()

    ContractorList(contractorList = contractors)
}

//todo
@Preview
@Composable
fun ContractorListPreview(){
    ContractorList(contractorList = listOf(ContractorDto(1, "symbol1", "name1")))
}

@Composable
fun ContractorList(contractorList: List<ContractorDto>){
    //todo
    Column {
        Text(text = "Dashboard")
        LazyColumn {
            items(contractorList) {contractor ->
                ContractorCard(contractor)
            }
        }
    }

}

@Composable
fun ContractorCard(contractorDto: ContractorDto) {
    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .fillMaxWidth()){
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = contractorDto.name)
            Text(text = contractorDto.symbol)
        }
    }
}