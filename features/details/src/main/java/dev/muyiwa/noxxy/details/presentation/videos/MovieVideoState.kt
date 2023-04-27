package dev.muyiwa.noxxy.details.presentation.videos

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.presentation.*

data class MovieVideoState(
	val isLoading: Boolean = true,
	val videos: List<Video> = emptyList(),
	val failure: Event<Throwable>? = null
)