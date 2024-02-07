package com.example.warehouseandroid

import com.example.warehouseandroid.contractor.Contractor
import com.example.warehouseandroid.documentitem.DocumentItem
import com.example.warehouseandroid.receiptdocument.ReceiptDocument
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("contractor")
    suspend fun getAllContractors(): Response<List<Contractor>>

    @GET("contractor/{id}")
    suspend fun getContractor(@Path("id") id: Long): Response<Contractor>

    @POST("contractor")
    suspend fun postContractor(@Body contractor: Contractor): Response<Contractor>

    @PUT("contractor/{id}")
    suspend fun putContractor(
        @Path("id") id: Long,
        @Body contractor: Contractor
    ): Response<Contractor>

    @GET("receipt-document")
    suspend fun getAllReceiptDocuments(): Response<List<ReceiptDocument>>

    @GET("receipt-document/{id}")
    suspend fun getReceiptDocument(@Path("id") id: Long): Response<ReceiptDocument>

    @POST("receipt-document")
    suspend fun postReceiptDocument(@Body receiptDocument: ReceiptDocument): Response<ReceiptDocument>

    @PUT("receipt-document/id")
    suspend fun putReceiptDocument(
        @Path("id") id: Long,
        @Body receiptDocument: ReceiptDocument
    ): Response<ReceiptDocument>

    @GET("document-item")
    suspend fun getDocumentItems(@Query("recipt-document-id") receiptDocumentId: Long): Response<List<DocumentItem>>

    @GET("document-item/{id}")
    suspend fun getDocumentItem(@Path("id") id: Long): Response<DocumentItem>

    @POST("document-item")
    suspend fun postDocumentItem(@Body documentItem: DocumentItem): Response<DocumentItem>

    @PUT("document-item/{id}")
    suspend fun putDocumentItem(
        @Path("id") id: Long,
        @Body documentItem: DocumentItem
    ): Response<DocumentItem>

    companion object {
        const val BASE_URL = "https://spring-warehouse.onrender.com"
    }
}

