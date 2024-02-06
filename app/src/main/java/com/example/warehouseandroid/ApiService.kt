package com.example.warehouseandroid

import com.example.warehouseandroid.contractor.Contractor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("contractor")
    suspend fun getAllContractors(): Response<List<Contractor>>

    @GET("contractor/{id}")
    suspend fun getContractor(@Path("id") id: Long): Response<Contractor>

    @POST("contractor")
    suspend fun postContractor(contractor: Contractor): Response<Contractor>

    @PUT("contractor/{id}")
    suspend fun putContractor(@Path("id") id: Long, contractor: Contractor): Response<Contractor>

    companion object {
        const val BASE_URL = "https://spring-warehouse.onrender.com"
    }
}

