package dev.muyiwa.common.utils

import kotlinx.coroutines.*

interface DispatchersProvider {
	fun io(): CoroutineDispatcher = Dispatchers.IO
}