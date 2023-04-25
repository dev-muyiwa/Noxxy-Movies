package dev.muyiwa.common.data.repository

import dev.muyiwa.common.data.api.*
import dev.muyiwa.common.data.api.model.categorised_movie.*
import dev.muyiwa.common.data.api.model.mapper.*
import dev.muyiwa.common.data.cache.*
import dev.muyiwa.common.data.cache.entities.mapper.*
import dev.muyiwa.common.data.preferences.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.model.mapper.*
import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.logging.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.*
import javax.inject.*

class NoxxyAppRepository @Inject constructor(
	private val api: NoxxyApi,
	private val cache: Cache,
	private val preferences: Preferences,
	private val dispatchersProvider: DispatchersProvider
) : AppRepository {
	override fun getCategorisedMovies(category: Category): Flow<List<MovieWithGenres>> {
		return cache.getMoviesBy(category.toCacheModel())
			.distinctUntilChanged()
			.map { list -> list.map { it.toDomainModel() } }
	}

	override fun getAllCategoriesOfMovies(): List<Flow<List<MovieWithGenres>>> {
		return flow {
			withContext(dispatchersProvider.io()) {
				val result = async {
					val popularFlow = getCategorisedMovies(Category.POPULAR)
					val nowPlayingFlow = getCategorisedMovies(Category.NOW_PLAYING)
					val topRatedFlow = getCategorisedMovies(Category.TOP_RATED)
					val upcomingFlow = getCategorisedMovies(Category.UPCOMING)
					listOf(popularFlow, nowPlayingFlow, topRatedFlow, upcomingFlow)
				}
				emitAll(result)
			}
		}
	}

	override suspend fun requestForMoreCategorisedMovies(
		pageToLoad: Int,
		category: Category
	): MoviePagination {
		val lang = preferences.languageTag
		try {
			val apiMovie: ApiCategorisedMovieResponse = retry {
				when (category) {
					Category.POPULAR -> {
						api.fetchPopularMovies(lang, pageToLoad)
					}
					Category.NOW_PLAYING -> {
						api.fetchNowPlayingMovies(lang, pageToLoad)
					}
					Category.TOP_RATED -> {
						api.fetchTopRatedMovies(lang, pageToLoad)
					}
					Category.UPCOMING -> {
						api.fetchUpcomingMovies(lang, pageToLoad)
					}
				}
			}
			return apiMovie.toPagination()
		} catch (exception: HttpException) {
			throw NetworkException(
				exception.message() ?: "Code ${exception.code()}"
			)
		}
	}

	override suspend fun storeCategorisedMovies(category: Category?, movies: List<MovieWithGenres>) {
		cache.storeMoviesWithGenreBy(
			category = category?.toCacheModel(),
			movies = movies.map { it.toCacheModel() }
		)
	}

	override suspend fun getMovieDetail(movieId: Int): MovieWithFullDetail {
		try {
			val movie = cache.getMovieWithCompleteDetails(movieId)
			if (movie.detail != null) {
				return movie.toDomainModel()
			} else {
				// get detail, casts and reviews endpoints. combine it with genre, bookmark and movie
				val result = withContext(dispatchersProvider.io()) {
					val apiDetail = async { api.fetchMovieDetailsById(movieId.toLong()) }
					val apiCasts = async { api.fetchCastsByMovieId(movieId.toLong()) }
					val apiReviews = async { api.getReviews(movieId.toLong(), preferences.languageTag, 1) }
					Triple(apiDetail.await(), apiCasts.await(), apiReviews.await())
				}
				cache.storeDetails(
					movie = movie.movie,
					detail = result.first.toDomainModel().toCacheModel(movieId),
					genres = movie.genres,
					casts = result.second.toDomainModel().map { it.toCacheModel(movieId) },
					reviews = result.third.toDomainModel().map { it.toCacheModel(movieId) }
				)
				return cache.getMovieWithCompleteDetails(movieId).toDomainModel()
			}
		} catch (exception: HttpException) {
			throw NetworkException(
				exception.message() ?: "Code ${exception.code()}"
			)
		}
	}

	override fun searchCachedMoviesBy(query: String): Flow<List<MovieWithGenres>> {
		return cache.searchMoviesBy(query)
			.distinctUntilChanged()
			.map { moviesList -> moviesList.map { it.toDomainModel() } }
	}

	override suspend fun searchMoviesRemotely(query: String, pageToLoad: Int): MoviePagination {
		val lang = preferences.languageTag
		try {
			val apiSearchResponse = retry {
				api.searchForMovies(lang, query, pageToLoad)
			}
			return apiSearchResponse.toPagination()
		} catch (exception: HttpException) {
			throw NetworkException(
				exception.message() ?: "Code ${exception.code()}"
			)
		}
	}

	private suspend fun <T> retry(
		times: Int = 3,
		initialDelayMillis: Long = 1000,
		maxDelayMillis: Long = 20000,
		factor: Double = 2.0,
		block: suspend () -> T
	): T {
		var currentDelay = initialDelayMillis
		repeat(times) {
			try {
				return block()
			} catch (e: Exception) {
				Logger.e("${e.message}", e)
			}
			delay(currentDelay)
			currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
		}
		return block()
	}
}