package com.example.elalmashop.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elalmashop.data.model.Product
import com.example.elalmashop.data.repo.ProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalDbViewModel(val db: ProductRepo): ViewModel() {




     fun getAllProduct()= db.getAllProduct()


    fun insertProduct(product: Product)= viewModelScope.launch(Dispatchers.IO) {
        db.insertProduct(product)
    }

    fun deleteProduct(product: Product)= viewModelScope.launch(Dispatchers.IO){
        db.deleteProduct(product)
    }

    fun getTotalPrice() = db.getTheTotalPrice()


    fun increaseProductNumber(productId: Int) = viewModelScope.launch{
        db.increaseProductNumber(productId)
    }

    fun decreaseProductNumber(productId: Int) = viewModelScope.launch(Dispatchers.IO){
        db.decreaseProductNumber(productId)
    }


}