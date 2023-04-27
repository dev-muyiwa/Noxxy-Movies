package dev.muyiwa.common.data.cache

import dev.muyiwa.common.data.cache.entities.test_run.cast.*
import dev.muyiwa.common.data.cache.entities.test_run.category.*
import dev.muyiwa.common.data.cache.entities.test_run.detail.*
import dev.muyiwa.common.data.cache.entities.test_run.genre.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*
import dev.muyiwa.common.data.cache.entities.test_run.relation_class.*
import dev.muyiwa.common.data.cache.entities.test_run.review.*
import kotlinx.coroutines.flow.*

interface Cache {
	fun getMoviesAsFlowBy(category: CachedCategory): Flow<List<CachedMovieWithGenres>>
	suspend fun getMoviesWithGenreBy(
		category: CachedCategory,
		count: Int
	): List<CachedMovieWithGenres>

	suspend fun getMovieWithCompleteDetails(movieId: Int): CachedMovieWithDetailsAndGenres
	fun searchMoviesBy(title: String): Flow<List<CachedMovieWithGenres>>
	suspend fun storeMoviesBy(movies: List<CachedMovie>, category: CachedCategory)
	suspend fun storeMoviesWithGenreBy(
		moviesWithGenres: List<CachedMovieWithGenres>,
		category: CachedCategory? = null
	)

	suspend fun storeDetails(
		movie: CachedMovie, detail: CachedDetail, genres: List<CachedGenre>,
		casts: List<CachedCast>, reviews: List<CacheReview>
	)
}