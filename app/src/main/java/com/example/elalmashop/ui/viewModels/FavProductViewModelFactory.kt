package com.example.elalmashop.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.elalmashop.data.repo.FavProductRepo

class FavProductViewModelFactory(val favProductRepo: FavProductRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return FavProductViewModel(favProductRepo) as T
    }

}