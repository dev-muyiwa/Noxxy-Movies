package dev.muyiwa.common.presentation.model

data class UiCategorisedMovieComplete(
	val basicCategorisedMovie: UiCategorisedMovie,
	val overview: String,
	val genreIds: List<String>,
	val backdropPath: String,
	val originalLanguage: String,
	val originalTitle: String,
	val popularity: Double,
	val releaseDate: String,
	val voteCount: Int
)
