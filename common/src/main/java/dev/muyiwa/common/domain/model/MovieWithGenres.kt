package dev.muyiwa.common.domain.model

data class MovieWithGenres(
	val movie: Movie,
	val genres: List<Genre>
)
