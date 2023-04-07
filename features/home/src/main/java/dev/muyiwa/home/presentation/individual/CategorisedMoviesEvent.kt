package dev.muyiwa.home.presentation.individual

sealed class CategorisedMoviesEvent {
	object RequestMoreMovies: CategorisedMoviesEvent()
}