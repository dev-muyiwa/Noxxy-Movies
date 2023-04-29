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
	override fun getCategorisedMoviesAsFlow(category: Category): Flow<List<MovieWithGenres>> {
		return cache.getMoviesAsFlowBy(category.toCacheModel())
			.distinctUntilChanged()
			.map { list -> list.map { it.toDomainModel() } }
	}

	override fun getAllCategoriesOfMovies(count: Int): Flow<List<Pair<Category, List<MovieWithGenres>>>> {
		return flow {
			val popularMovies =
//				CoroutineScope(dispatchersProvider.io()).async {
//				retry {
				Category.POPULAR to
						cache.getMoviesWithGenreBy(Category.POPULAR.toCacheModel(), count)
							.map { it.toDomainModel() }
//				}
//			}
			val nowPlayingMovies =
//				CoroutineScope(dispatchersProvider.io()).async {
//				retry {
				Category.NOW_PLAYING to
						cache.getMoviesWithGenreBy(Category.NOW_PLAYING.toCacheModel(), count)
							.map { it.toDomainModel() }
//				}
//			}
			val topRatedMovies =
//				CoroutineScope(dispatchersProvider.io()).async {
//				retry {
				Category.TOP_RATED to
						cache.getMoviesWithGenreBy(Category.TOP_RATED.toCacheModel(), count)
							.map { it.toDomainModel() }
//				}
//			}
			val upcomingMovies =
//				CoroutineScope(dispatchersProvider.io()).async {
//				retry {
				Category.UPCOMING to
						cache.getMoviesWithGenreBy(Category.UPCOMING.toCacheModel(), count)
							.map { it.toDomainModel() }
//				}
//			}
//			val result = listOf(popularMovies.await(), nowPlayingMovies.await(), topRatedMovies.await(), upcomingMovies.await())
			val result = listOf(popularMovies, nowPlayingMovies, topRatedMovies, upcomingMovies)
//			Logger.i("Cache Movies => $result")
			emit(result)
			try {
				val apiPopularMovies = CoroutineScope(dispatchersProvider.io()).async {
					retry {
						Category.POPULAR to
								api.fetchPopularMovies(preferences.languageTag, 1).toPagination().movies
					}
				}
				val apiNowPlayingMovies = CoroutineScope(dispatchersProvider.io()).async {
					retry {
						Category.NOW_PLAYING to
								api.fetchNowPlayingMovies(preferences.languageTag, 1).toPagination().movies
					}
				}
				val apiTopRatedMovies = CoroutineScope(dispatchersProvider.io()).async {
					retry {
						Category.TOP_RATED to
								api.fetchTopRatedMovies(preferences.languageTag, 1).toPagination().movies
					}
				}
				val apiUpcomingMovies = CoroutineScope(dispatchersProvider.io()).async {
					retry {
						Category.UPCOMING to
								api.fetchUpcomingMovies(preferences.languageTag, 1).toPagination().movies
					}
				}
				val apiResult = listOf(apiPopularMovies.await(), apiNowPlayingMovies.await(), apiTopRatedMovies.await(), apiUpcomingMovies.await())
				apiResult.forEach { (category, movieWithGenre) ->
					cache.storeMoviesWithGenreBy(movieWithGenre.map { it.toCacheModel() }, category.toCacheModel())
				}
			} catch (exception: HttpException) {
				throw NetworkException(
					exception.message() ?: "Code ${exception.code()}"
				)
			}
			val updatedPopularMovies =
//				CoroutineScope(dispatchersProvider.io()).async {
//				retry {
				Category.POPULAR to
						cache.getMoviesWithGenreBy(Category.POPULAR.toCacheModel(), count)
							.map { it.toDomainModel() }
//				}
//			}
			val updatedNowPlayingMovies =
//				CoroutineScope(dispatchersProvider.io()).async {
//				retry {
				Category.NOW_PLAYING to
						cache.getMoviesWithGenreBy(Category.NOW_PLAYING.toCacheModel(), count)
							.map { it.toDomainModel() }
//				}
//			}
			val updatedTopRatedMovies =
//				CoroutineScope(dispatchersProvider.io()).async {
//				retry {
				Category.TOP_RATED to
						cache.getMoviesWithGenreBy(Category.TOP_RATED.toCacheModel(), count)
							.map { it.toDomainModel() }
//				}
//			}
			val updatedUpcomingMovies =
//				CoroutineScope(dispatchersProvider.io()).async {
//				retry {
				Category.UPCOMING to
						cache.getMoviesWithGenreBy(Category.UPCOMING.toCacheModel(), count)
							.map { it.toDomainModel() }
//				}
//			}
			val updatedResult =
				listOf(updatedPopularMovies, updatedNowPlayingMovies, updatedTopRatedMovies, updatedUpcomingMovies)
			Logger.i("Cache Movies => $result")
			emit(updatedResult)
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
			moviesWithGenres = movies.map { it.toCacheModel() }
		)
	}

	override fun getMovieDetail(movieId: Int): Flow<MovieWithFullDetail> {
		return flow {
			val movie = cache.getMovieWithCompleteDetails(movieId)
			if(movie.detail != null) {
				emit(movie.toDomainModel())
			}
			try {
				retry {
					val apiDetails = retry { CoroutineScope(dispatchersProvider.io()).async {
							api.fetchMovieDetailsById(movieId.toLong())
						} }
					val apiCasts = retry { CoroutineScope(dispatchersProvider.io()).async {
						api.fetchCastsByMovieId(movieId.toLong())
					} }
					val apiReviews = retry { CoroutineScope(dispatchersProvider.io()).async {
						api.getReviews(movieId.toLong(), preferences.languageTag, 1)
					} }

//					val result = withContext(dispatchersProvider.io()) {
//						val apiDetail = async { api.fetchMovieDetailsById(movieId.toLong()) }
//						val apiCasts = async { api.fetchCastsByMovieId(movieId.toLong()) }
//						val apiReviews = async { api.getReviews(movieId.toLong(), preferences.languageTag, 1) }
//						Triple(apiDetail.await(), apiCasts.await(), apiReviews.await())
//					}
//					Logger.d("Movie ID => $movie, Movie => $movie")
//					cache.storeDetails(
//						movie = movie.movie,
//						detail = result.first.toDomainModel().toCacheModel(movieId),
//						genres = movie.genres.orEmpty(),
//						casts = result.second.toDomainModel().map { it.toCacheModel(movieId) },
//						reviews = result.third.toDomainModel().map { it.toCacheModel(movieId) }
//					)

					cache.storeDetails(
						movie = movie.movie,
						detail = apiDetails.await().toDomainModel().toCacheModel(movieId),
						genres = movie.genres.orEmpty(),
						casts = apiCasts.await().toDomainModel().map { it.toCacheModel(movieId) },
						reviews = apiReviews.await().toDomainModel().map { it.toCacheModel(movieId) }
					)

				}
			} catch (exception: HttpException) {
				throw NetworkException(
					exception.message() ?: "Code ${exception.code()}"
				)
			}
			val updatedMovie = cache.getMovieWithCompleteDetails(movieId)
			Logger.d(" Updated Movie ID => ${movie.movie.movieId}, Movie => $movie")
			emit(updatedMovie.toDomainModel())
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

	override suspend fun getVideosBy(movieId: Int): List<Video> {
		return try {
			retry { api.getVideos(movieId.toLong(), preferences.languageTag).apiVideos.toDomainModel() }
		} catch (e: HttpException) {
			throw NetworkException(
				e.message() ?: "Code ${e.code()}"
			)
		}
	}

	override suspend fun toggleBookmark(movieId: Int) {
		TODO("Not yet implemented")
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