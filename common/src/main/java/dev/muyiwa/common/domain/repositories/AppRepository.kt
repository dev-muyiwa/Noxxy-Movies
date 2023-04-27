package dev.muyiwa.common.domain.repositories

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.utils.*
import kotlinx.coroutines.flow.*

interface AppRepository {
	fun getCategorisedMoviesAsFlow(category: Category): Flow<List<MovieWithGenres>>
	fun getAllCategoriesOfMovies(count: Int = 40): Flow<List<Pair<Category, List<MovieWithGenres>>>>
	suspend fun getAllCategoriesOfMoviesRemotely(pageToLoad: Int, count: Int = 10): List<Pair<Category, MoviePagination>>
	suspend fun requestForMoreCategorisedMovies(pageToLoad: Int, category: Category): MoviePagination
	suspend fun storeCategorisedMovies(category: Category? = null, movies: List<MovieWithGenres>)

	suspend fun getMovieDetail(movieId: Int): MovieWithFullDetail

	fun searchCachedMoviesBy(query: String): Flow<List<MovieWithGenres>>
	suspend fun searchMoviesRemotely(query: String, pageToLoad: Int): MoviePagination

	suspend fun getVideosBy(movieId: Int): List<Video>
}