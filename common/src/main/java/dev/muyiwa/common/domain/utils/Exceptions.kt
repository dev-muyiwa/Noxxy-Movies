package dev.muyiwa.common.domain.utils

import java.io.*

class NetworkUnavailableException(message: String = "Server is currently unreachable. Check your internet connection.") :
	IOException(message)

class NetworkException(message: String) : Exception(message)

class NoMoreMoviesException(message: String) : Exception(message)

class MappingException(message: String) : Exception(message)