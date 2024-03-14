package com.example.elalmashop.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elalmashop.data.model.FavProduct
import com.example.elalmashop.data.repo.FavProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavProductViewModel(
    private val favProductRepo: FavProductRepo
) : ViewModel() {


    fun getAllFavProduct() = favProductRepo.getAllFavProduct()


    fun upsertFavProduct(favProduct: FavProduct) = viewModelScope.launch(Dispatchers.IO) {
        favProductRepo.upsertFavProduct(favProduct)
    }

    fun deleteFavProduct(favProduct: FavProduct) = viewModelScope.launch(Dispatchers.IO) {
        favProductRepo.deleteFavProduct(favProduct)
    }



}