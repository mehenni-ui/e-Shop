package com.example.elalmashop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favProduct")
data class FavProduct(
    @PrimaryKey
    val id: Int,
    val price: Int,
    val thumbnail: String,
    val title: String,
    val isFavorite: Boolean = false
) : Serializable
