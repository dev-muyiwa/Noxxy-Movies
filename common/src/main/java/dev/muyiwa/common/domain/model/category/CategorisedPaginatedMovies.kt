package dev.muyiwa.common.domain.model.category

data class CategorisedPaginatedMovies(
	val movies: List<CategorisedMovie>,
	val pagination: Pagination,
)

data class PaginatedMovies(
	val movies: List<Movie>,
	val pagination: Pagination
)
