package com.softspire.core_domain.util

typealias DomainError = Error

sealed interface ResponseModel<out D, out E : DomainError> {
    data class Success<out D>(val data: D) : ResponseModel<D, Nothing>
    data class Error<out E : DomainError>(val error: E) : ResponseModel<Nothing, E>
    data object Loading : ResponseModel<Nothing, Nothing>
}

inline fun <T, E : DomainError, R> ResponseModel<T, E>.map(map: (T) -> R): ResponseModel<R, E> {
    return when (this) {
        is ResponseModel.Error -> ResponseModel.Error(error)
        is ResponseModel.Success -> ResponseModel.Success(map(data))
        ResponseModel.Loading -> ResponseModel.Loading
    }
}