package dev.muyiwa.common.domain.model

data class MovieWithFullDetail(
	val movie: Movie,
	val detail: Detail,
	val genres: List<Genre>,
	val casts: List<Cast>,
	val reviews: List<Review>,
	val isBookmarked: Boolean
)
