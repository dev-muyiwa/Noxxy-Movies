package dev.muyiwa.noxxy.details.presentation

import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.presentation.model.*

data class MovieDetailState(
	val isLoading: Boolean = true,
	val movieDetail: UiMovieDetails? = null,
	val failure: Event<Throwable>? = null
)