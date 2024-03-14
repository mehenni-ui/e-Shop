package com.example.elalmashop.data.db.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.elalmashop.data.model.FavProduct

@Dao
interface FavProductDao {

    @Query("SELECT * FROM favProduct")
    fun getAllFavProduct(): LiveData<List<FavProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFavProduct(favProduct: FavProduct)

    @Delete
    suspend fun deleteFavProduct(favProduct: FavProduct)

}