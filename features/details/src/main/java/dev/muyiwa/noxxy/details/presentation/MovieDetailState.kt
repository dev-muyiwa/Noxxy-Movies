package dev.muyiwa.noxxy.details.presentation

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.presentation.model.*

data class MovieDetailState(
	val isLoading: Boolean = true,
	val movieDetail: MovieWithFullDetail? = null,
	val failure: Event<Throwable>? = null
)