package dev.muyiwa.common.presentation.model

import dev.muyiwa.common.domain.model.detail.*

data class UiMovieDetails(
	val movie: UiCategorisedMovieComplete,
	val budget: Long,
	val homepage: String,
	val revenue: Long,
	val runtime: String,
	val status: String,
	val tagline: String,
	val casts: List<Cast>,
	val reviews: List<Review>
)
