package dev.muyiwa.search.domain.model

import dev.muyiwa.common.domain.model.category.*

data class SearchResult(
	val query: String,
	val movies: List<Movie>
)
