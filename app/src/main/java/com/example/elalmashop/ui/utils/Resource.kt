package com.example.elalmashop.ui.utils

sealed class Resource<T>(
    data: T? = null,
    message: String?= null
) {

    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String?, data: T?=null) : Resource<T>(data, message)
}