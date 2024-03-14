package com.example.elalmashop.data.repo

import com.example.elalmashop.data.db.local.ProductDb
import com.example.elalmashop.data.model.FavProduct

class FavProductRepo(val db: ProductDb) {

    fun getAllFavProduct() = db.favProductDao().getAllFavProduct()


    suspend fun upsertFavProduct(favProduct: FavProduct){
        db.favProductDao().upsertFavProduct(favProduct)
    }

    suspend fun deleteFavProduct(favProduct: FavProduct){
        db.favProductDao().deleteFavProduct(favProduct)
    }
}