package com.testproject.data.network.api_call

data class Resource<out T>(val status: ResponseStatus, val responseData: T?=null,
                           val message: String? = null)
{
    companion object {
        // 200
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResponseStatus.SUCCESS, data)
        }
        // 400
        fun <T> validationError(data: T?): Resource<T> {
            return Resource(ResponseStatus.VALIDATION_ERROR, data)
        }
        //404
        fun <T> notFoundError(data: T?): Resource<T> {
            return Resource(ResponseStatus.NOT_FOUND_ERROR, data)
        }

        fun <T> networkError(): Resource<T> {
            return Resource(ResponseStatus.NETWORK_ERROR)
        }
        //401
        fun <T> authError(data: T?): Resource<T> {
            return Resource(ResponseStatus.AUTH_ERROR,data)
        }
        fun <T> error(message: String): Resource<T> {
            return Resource(ResponseStatus.ERROR,message = message)
        }
    }
}

enum class ResponseStatus
{
    // 200
    SUCCESS,
    // No match found with response codes
    ERROR,
    //401
    AUTH_ERROR,
    // 400
    VALIDATION_ERROR,
    //404
    NOT_FOUND_ERROR,
    NETWORK_ERROR,
}