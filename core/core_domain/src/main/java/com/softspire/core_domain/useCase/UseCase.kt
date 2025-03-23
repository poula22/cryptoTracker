package com.softspire.core_domain.useCase

import com.softspire.core_domain.util.DomainError
import com.softspire.core_domain.util.NetworkError
import com.softspire.core_domain.util.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.CoroutineContext

abstract class UseCase<out Response, in Params> {

    abstract suspend fun run(params:Params) : ResponseModel<Response, DomainError>

    operator fun invoke(
        params: Params,
        coroutineContext: CoroutineContext = Dispatchers.IO
    ) = flow {
        val response = run(params)
        emit(response)
    }.onStart {
        ResponseModel.Loading
    }.catch {
        coroutineContext.ensureActive()
        emit(ResponseModel.Error(NetworkError.UNKNOWN))
    }.flowOn(coroutineContext)

    data object None
}