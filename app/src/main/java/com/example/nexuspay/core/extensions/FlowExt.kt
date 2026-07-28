package com.example.nexuspay.core.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.onStartLoading(action: () -> Unit): Flow<T> =
    onStart { action() }

fun <T> Flow<T>.handleErrors(action: (Throwable) -> Unit): Flow<T> =
    catch { action(it) }
