package dev.muyiwa.common.presentation.model

import androidx.room.*

data class UiMovieDetails(
	val movie: UiCategorisedMovieComplete,
	val budget: Long,
	val homepage: String,
	val revenue: Long,
	val runtime: String,
	val status: String,
	val tagline: String
)
