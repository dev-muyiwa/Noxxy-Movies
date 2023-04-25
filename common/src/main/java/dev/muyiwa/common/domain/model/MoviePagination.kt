package dev.muyiwa.common.domain.model

data class MoviePagination(
	val movies: List<MovieWithGenres>,
	val pagination: Pagination
)
