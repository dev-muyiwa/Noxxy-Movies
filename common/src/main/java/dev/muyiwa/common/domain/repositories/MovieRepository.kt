package dev.muyiwa.common.domain.repositories

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.model.detail.*
import dev.muyiwa.common.domain.utils.*
import kotlinx.coroutines.flow.*

interface MovieRepository {
	fun getCategorisedMoviesAsFlow(category: Category, lang: String, pageToLoad: Int): Flow<Resource<List<CategorisedMovie>>>

	fun getCategorisedMovies(category: Category): Flow<List<CategorisedMovie>>
	suspend fun requestForMoreCategorisedMovies(pageToLoad: Int, category: Category): CategorisedPaginatedMovies
	suspend fun storeCategorisedMovies(movies: List<CategorisedMovie>)

	fun getAllCategorisedMovies(lang: String, pageToLoad: Int = 1, noOfItems: Int = 10): Flow<Resource<List<List<CategorisedMovie>>>>
	fun getMovieDetail(lang: String, movieId: Int): Flow<Resource<MovieDetail>>

	fun searchCachedMoviesBy(query: String): Flow<List<Movie>>
	suspend fun searchMoviesRemotely(query: String, pageToLoad: Int): PaginatedMovies

//	fun searchForMovies(lang: String, query: String): Flow<List<>>

//	suspend fun getListOfGenres(): List<String>

}