package dev.muyiwa.common.domain.model.category

import dev.muyiwa.common.data.cache.entities.*
import dev.muyiwa.common.domain.utils.*

data class CategorisedMovie(
	val isAdult: Boolean,
	val backdropPath: String,
	val genreIds: List<String>,
	val movieId: Int,
	val originalLanguage: String,
	val originalTitle: String,
	val overview: String,
	val popularity: Double,
	val posterPath: String,
	val releaseDate: String,
	val title: String,
	val video: Boolean,
	val voteAverage: Double,
	val voteCount: Int,
	val category: Category
)

