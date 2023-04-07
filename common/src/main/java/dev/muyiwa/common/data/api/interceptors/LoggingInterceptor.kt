package dev.muyiwa.common.data.api.interceptors

import dev.muyiwa.logging.*
import okhttp3.logging.*
import javax.inject.Inject

class LoggingInterceptor @Inject constructor(): HttpLoggingInterceptor.Logger {
	override fun log(message: String) {
		Logger.i(message)
	}
}