package dev.muyiwa.home.presentation.all

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.*

data class HomeMoviesState(
	val isLoading: Boolean = true,
	val popularMovies: List<MovieWithGenres> = emptyList(),
	val nowPlayingMovies: List<MovieWithGenres> = emptyList(),
	val topRatedMovies: List<MovieWithGenres> = emptyList(),
	val upcomingMovies: List<MovieWithGenres> = emptyList(),
	val allMovies: List<Pair<Category, List<MovieWithGenres>>> = listOf(
		Category.POPULAR to popularMovies,
		Category.NOW_PLAYING to nowPlayingMovies,
		Category.TOP_RATED to topRatedMovies,
		Category.UPCOMING to upcomingMovies
	),
	val failure: Event<Throwable>? = null
) {
	fun updateToHasPopularMovies(movies: List<MovieWithGenres>): HomeMoviesState {
		return copy(popularMovies = movies)
	}

	fun updateToHasNowPlayingMovies(movies: List<MovieWithGenres>): HomeMoviesState {
		return copy(nowPlayingMovies = movies)
	}

	fun updateToHasTopRatedMovies(movies: List<MovieWithGenres>): HomeMoviesState {
		return copy(topRatedMovies = movies)
	}

	fun updateToHasUpcomingMovies(movies: List<MovieWithGenres>): HomeMoviesState {
		return copy(upcomingMovies = movies)
	}

	fun updateToHasCompletedLoading(): HomeMoviesState {
		return copy(isLoading = false,
		allMovies = listOf(
			Category.POPULAR to popularMovies.toSet().toList(),
			Category.NOW_PLAYING to nowPlayingMovies.toSet().toList(),
			Category.TOP_RATED to topRatedMovies.toSet().toList(),
			Category.UPCOMING to upcomingMovies.toSet().toList()
		))
	}

	fun updateToHasFailures(failure: Throwable): HomeMoviesState {
		return copy(isLoading = false, failure = Event(failure))
	}
}