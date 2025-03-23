package com.softspire.core_data.remote

import com.softspire.core_domain.util.NetworkError
import com.softspire.core_domain.util.ResponseModel
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun<reified T> responseToResult(
    response: HttpResponse
): ResponseModel<T, NetworkError> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                ResponseModel.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                ResponseModel.Error(NetworkError.SERIALIZATION)
            }
        }
        408 -> ResponseModel.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> ResponseModel.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> ResponseModel.Error(NetworkError.SERVER_ERROR)
        else -> ResponseModel.Error(NetworkError.UNKNOWN)
    }
}