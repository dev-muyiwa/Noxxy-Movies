package dev.muyiwa.common.domain.repositories

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.model.Movie
import dev.muyiwa.common.domain.model.detail.*
import dev.muyiwa.common.domain.utils.*
import kotlinx.coroutines.flow.*

interface AppRepository {
	fun getAllCategoriesOfMovies(): List<Flow<List<MovieWithGenres>>>
	fun getCategorisedMovies(category: Category): Flow<List<MovieWithGenres>>
	suspend fun requestForMoreCategorisedMovies(pageToLoad: Int, category: Category): MoviePagination
	suspend fun storeCategorisedMovies(category: Category? = null, movies: List<MovieWithGenres>)

	suspend fun getMovieDetail(movieId: Int): MovieWithFullDetail

	fun searchCachedMoviesBy(query: String): Flow<List<MovieWithGenres>>
	suspend fun searchMoviesRemotely(query: String, pageToLoad: Int): MoviePagination
}