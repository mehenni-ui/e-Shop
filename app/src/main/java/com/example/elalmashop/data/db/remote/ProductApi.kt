package com.example.elalmashop.data.db.remote


import com.example.elalmashop.data.model.Products
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ProductApi {

    @GET("products")
    suspend fun getAllProducts() : Products


    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

    @GET("products/category/{cate}")
    suspend fun getProductsByCategory(@Path(value = "cate") cate: String): Products

    @GET("products/search")
    suspend fun searchResult(@Query("q") q: String): Products



}