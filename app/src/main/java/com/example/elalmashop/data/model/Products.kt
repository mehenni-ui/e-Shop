package com.example.elalmashop.data.model

import com.example.elalmashop.data.model.Product


data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)