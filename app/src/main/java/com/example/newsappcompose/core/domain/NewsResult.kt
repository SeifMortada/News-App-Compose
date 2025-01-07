package com.example.newsappcompose.core.domain

sealed class NewsResult<T>(val data: T? = null,val errorMessage: String?=null) {
    class Success<T>(data: T?) : NewsResult<T>(data=data)
    class Error<T>(errorMessage: String?): NewsResult<T>(errorMessage = errorMessage)
}