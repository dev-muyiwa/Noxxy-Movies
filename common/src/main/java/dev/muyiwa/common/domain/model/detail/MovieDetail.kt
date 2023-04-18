package dev.muyiwa.common.domain.model.detail

import dev.muyiwa.common.domain.model.category.*

data class MovieDetail(
	val movie: CategorisedMovie,
	val budget: Long,
	val homepage: String,
	val revenue: Long,
	val runtime: Int,
	val status: String,
	val tagline: String,
	val casts: List<Cast>
)
