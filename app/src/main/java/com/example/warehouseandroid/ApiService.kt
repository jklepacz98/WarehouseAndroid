package com.example.warehouseandroid

import com.example.warehouseandroid.contractor.Contractor
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("contractor")
    suspend fun getAllContractors(): Response<List<Contractor>>

    companion object {
        val BASE_URL = "https://spring-warehouse.onrender.com"
    }
}

