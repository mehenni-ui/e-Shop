package com.example.elalmashop.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elalmashop.data.model.Products
import com.example.elalmashop.data.db.remote.RetrofitInstance

class FeedViewModel: ViewModel() {

    private val _categoryList = MutableLiveData<List<String>>()
    private val categoryList: LiveData<List<String>> = _categoryList

    private val _productList = MutableLiveData<Products>()
    private val productList: LiveData<Products> = _productList

    private val _productNumber = MutableLiveData<Int>(1)
    private val productNumber: LiveData<Int> = _productNumber

    private suspend fun getAllCategoriesFromServer():LiveData<List<String>> {
        _categoryList.postValue(RetrofitInstance.api.getAllCategories())
        return categoryList
    }

    private suspend fun getAllProductFromServer():LiveData<Products> {
        _productList.postValue(RetrofitInstance.api.getAllProducts())
        return productList
    }

    suspend fun getAllCategories() = getAllCategoriesFromServer()

    suspend fun getAllProduct() = getAllProductFromServer()

    private fun increaseProductNumberP (stock: Int){
        if (_productNumber.value!! < stock){
            _productNumber.value = _productNumber.value?.plus(1)
        }
    }

    private fun decreaseProductNumberP (){
        if (_productNumber.value!! > 0){
            _productNumber.value = _productNumber.value?.minus(1)
        }
    }


    fun increaseProductNumber(stock: Int): LiveData<Int>{
        increaseProductNumberP(stock)
        return productNumber
    }

    fun decreaseProductNumber(): LiveData<Int>{
        decreaseProductNumberP()
        return productNumber
    }


    fun getProductNumber() = productNumber

    suspend fun getProductsByCategory(cate: String): LiveData<Products>{
        _productList.postValue(RetrofitInstance.api.getProductsByCategory(cate))
        return productList
    }


}