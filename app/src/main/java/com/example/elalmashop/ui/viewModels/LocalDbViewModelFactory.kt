package com.example.elalmashop.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.elalmashop.data.repo.ProductRepo

class LocalDbViewModelFactory (val db: ProductRepo): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return LocalDbViewModel(db) as T
    }
}