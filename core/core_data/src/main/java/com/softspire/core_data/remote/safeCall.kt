package com.softspire.core_data.remote

import com.softspire.core_domain.util.NetworkError
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext
import com.softspire.core_domain.util.ResponseModel

suspend inline fun<reified T> safeCall(
    execute: () -> HttpResponse
): ResponseModel<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return ResponseModel.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return ResponseModel.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return ResponseModel.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}