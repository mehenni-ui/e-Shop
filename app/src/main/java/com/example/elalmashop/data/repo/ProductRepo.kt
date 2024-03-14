package com.example.elalmashop.data.repo

import androidx.lifecycle.LiveData
import com.example.elalmashop.data.db.local.ProductDb
import com.example.elalmashop.data.model.Product

class ProductRepo(val db: ProductDb) {


    fun getAllProduct(): LiveData<List<Product>> = db.productDao().getAllProduct()

    suspend fun insertProduct(product: Product){
        db.productDao().insertProduct(product)
    }

    suspend fun deleteProduct(product: Product){
        db.productDao().deleteProduct(product)
    }

    fun getTheTotalPrice(): LiveData<Long> = db.productDao().getTotalPrice()

    suspend fun increaseProductNumber(productId: Int){
        db.productDao().increaseProductNumber(productId)
    }

    suspend fun decreaseProductNumber(productId: Int){
        db.productDao().decreaseProductNumber(productId)
    }


}