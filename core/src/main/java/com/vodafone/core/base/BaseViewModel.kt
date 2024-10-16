package com.vodafone.core.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import java.io.IOException

abstract class BaseViewModel<E: BaseUiEffect> : ViewModel() {

    protected val _effect = MutableSharedFlow<E?>()
    val effect = _effect.asSharedFlow().mapNotNull { it }

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit,
        inScope: CoroutineScope = viewModelScope
    ): Job {
        return runWithErrorCheck(onError, inScope) {
            val result = function()
            Log.e("Result", "tryToExecute: $result")
            onSuccess(result)
        }
    }

    protected fun sendEffect(effect: E) {
        viewModelScope.launch(Dispatchers.IO) {
            _effect.emit(effect)
        }
    }

    private fun runWithErrorCheck(
        onError: (Exception) -> Unit,
        inScope: CoroutineScope = viewModelScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        function: suspend () -> Unit
    ): Job {
        return inScope.launch(dispatcher) {
            try {
                function()
            } catch (exception: Exception) {
                when (exception) {
                    is IOException -> {
                        Log.d("IOException", "${exception.message}")
                        onError(exception)
                    }

                    is IllegalArgumentException -> {
                        Log.d("IllegalException", "${exception.message}")
                        onError(exception)
                    }
                    else -> {
                        Log.d("UnknownException", "${exception.message}")
                        onError(exception)
                    }
                }
            }
        }
    }

}