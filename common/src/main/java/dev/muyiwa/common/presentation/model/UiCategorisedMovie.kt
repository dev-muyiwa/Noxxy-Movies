package dev.muyiwa.common.presentation.model

import dev.muyiwa.common.domain.utils.*

data class UiCategorisedMovie(
	val isBookmarked: Boolean,
	val movieId: Int,
	val posterPath: String,
	val title: String,
	val voteAverage: Double,
	val category: Category?
)
