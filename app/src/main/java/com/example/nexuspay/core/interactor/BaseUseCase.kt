package com.example.nexuspay.core.interactor

import com.example.nexuspay.core.data.models.Resource
import com.example.nexuspay.core.data.remote.ExceptionConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseUseCase<Model, Params> {

    abstract suspend fun execute(params: Params? = null): Model

    operator fun invoke(
        scope: CoroutineScope,
        params: Params? = null,
        onResult: (Resource<Model>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(Resource.loading())
            try {
                val result = withContext(Dispatchers.IO) {
                    execute(params)
                }
                onResult(Resource.success(result))
            } catch (e: Exception) {
                onResult(Resource.failure(ExceptionConverter.convert(e)))
            }
        }
    }
}
