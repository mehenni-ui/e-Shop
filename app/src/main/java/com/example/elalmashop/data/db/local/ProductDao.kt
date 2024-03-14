package com.example.elalmashop.data.db.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.elalmashop.data.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAllProduct(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT SUM(price * productNumber) AS totalPrice FROM product")
    fun getTotalPrice(): LiveData<Long>

    @Query("UPDATE product SET productNumber = productNumber + 1 WHERE id = :productId")
    suspend fun increaseProductNumber(productId: Int)

    @Query("UPDATE product SET productNumber =  CASE WHEN productNumber > 1 THEN productNumber - 1 ELSE 1 END WHERE id = :productId")
    suspend fun decreaseProductNumber(productId: Int)


    


}