package com.dandycat.domain.util

sealed class ApiResult<out T> {
    data class Success<out T>(val value : T) : ApiResult<T>()
    data class Fail(val code : Int, val message : String) : ApiResult<Nothing>()
    data class Error(val code : Int? = null, val exception : Throwable? = null) : ApiResult<Nothing>()
}