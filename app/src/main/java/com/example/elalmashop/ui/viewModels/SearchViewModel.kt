package com.example.elalmashop.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elalmashop.data.db.remote.RetrofitInstance
import com.example.elalmashop.data.model.Products
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel(){

    private val _searchResult =  MutableLiveData<Products>()
    private val searchResult: LiveData<Products> = _searchResult



    private suspend fun searchProducts(query: String){
        _searchResult.postValue(RetrofitInstance.api.searchResult(query))
    }


     fun getSearchProducts(query: String): LiveData<Products>{
        viewModelScope.launch {
            searchProducts(query)
        }
        return searchResult
    }
}