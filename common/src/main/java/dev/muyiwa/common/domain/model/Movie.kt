package dev.muyiwa.common.domain.model

data class Movie(
	val movieId: Int,
	val isAdult: Boolean,
	val backdropPath: String,
	val originalLanguage: String,
	val originalTitle: String,
	val overview: String,
	val popularity: Double,
	val posterPath: String,
	val releaseDate: String,
	val title: String,
	val voteAverage: Double,
	val voteCount: Int
)
