package dev.muyiwa.common.data.repository

import dev.muyiwa.common.data.api.*
import dev.muyiwa.common.data.cache.daos.*
import dev.muyiwa.common.data.preferences.*
import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.model.detail.*
import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.logging.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.*
import java.util.*
import javax.inject.*

class NoxxyMovieRepository @Inject constructor(
	private val api: NoxxyApi,
	private val dao: NoxxyMoviesDao,
	private val preferences: Preferences,
	private val dispatchersProvider: DispatchersProvider
) : MovieRepository {

	override fun getCategorisedMoviesAsFlow(
		category: Category,
		lang: String,
		pageToLoad: Int
	): Flow<Resource<List<CategorisedMovie>>> {
		return flow {
			emit(Resource.Loading())
			val listOfCategorisedMovies =
				dao.getCategorisedMovies(category).map { it.toDomainModel() }
			emit(Resource.Loading(listOfCategorisedMovies))
			try {
				retry {
					val apiCategorisedMovie = when (category) {
						Category.POPULAR -> {
							api.fetchPopularMovies(lang, pageToLoad).movies
						}
						Category.NOW_PLAYING -> {
							api.fetchNowPlayingMovies(lang, pageToLoad).movies
						}
						Category.TOP_RATED -> {
							api.fetchTopRatedMovies(lang, pageToLoad).movies
						}
						Category.UPCOMING -> {
							api.fetchUpcomingMovies(lang, pageToLoad).movies
						}
					}
					apiCategorisedMovie.orEmpty().forEach { movie ->
						dao.deleteCategorisedMovie(movie !!.movieId !!, category)
						dao.insertCategorisedMovie(movie.toDomainModel(category).toCacheModel())
					}
				}
			} catch (e: NetworkUnavailableException) {
				Logger.e("${e.message}", e)
				emit(
					Resource.Error(
						message = "${e.message}",
						data = listOfCategorisedMovies
					)
				)
			} catch (e: HttpException) {
				Logger.e("${e.message}", e)
				emit(
					Resource.Error(
						message = "${e.message}",
						data = listOfCategorisedMovies
					)
				)
			}
			val updatedListOfCategorisedMovie = dao.getCategorisedMovies(category)
				.map { it.toDomainModel() }
			emit(Resource.Success(data = updatedListOfCategorisedMovie))
		}
	}

	override fun getAllCategorisedMovies(
		lang: String,
		pageToLoad: Int,
		noOfItems: Int
	): Flow<Resource<List<List<CategorisedMovie>>>> {
		return flow {
			emit(Resource.Loading())
			val localPopularMovies = CoroutineScope(dispatchersProvider.io()).async {
				dao.getCategorisedMovies(Category.POPULAR).map { it.toDomainModel() }
					.take(noOfItems)
			}
			val localNowPlayingMovies = CoroutineScope(dispatchersProvider.io()).async {
				dao.getCategorisedMovies(Category.NOW_PLAYING).map { it.toDomainModel() }
					.take(noOfItems)
			}
			val localTopRatedMovies = CoroutineScope(dispatchersProvider.io()).async {
				dao.getCategorisedMovies(Category.TOP_RATED).map { it.toDomainModel() }
					.take(noOfItems)
			}
			val localUpcomingMovies = CoroutineScope(dispatchersProvider.io()).async {
				dao.getCategorisedMovies(Category.UPCOMING).map { it.toDomainModel() }
					.take(noOfItems)
			}
			val allLocalMovies = awaitAll(
				localPopularMovies,
				localNowPlayingMovies,
				localTopRatedMovies,
				localUpcomingMovies
			)
			emit(Resource.Loading(allLocalMovies))
			try {
				retry {
					val apiPopularMovies = CoroutineScope(dispatchersProvider.io()).async {
						api.fetchPopularMovies(lang, pageToLoad).movies
					}
					val apiNowPlayingMovies = CoroutineScope(dispatchersProvider.io()).async {
						api.fetchNowPlayingMovies(lang, pageToLoad).movies
					}
					val apiTopRatedMovies = CoroutineScope(dispatchersProvider.io()).async {
						api.fetchTopRatedMovies(lang, pageToLoad).movies
					}
					val apiUpcomingMovies = CoroutineScope(dispatchersProvider.io()).async {
						api.fetchUpcomingMovies(lang, pageToLoad).movies
					}
					val (a, b, c, d) = awaitAll(
						apiPopularMovies,
						apiNowPlayingMovies,
						apiTopRatedMovies,
						apiUpcomingMovies
					)
					a.orEmpty().forEach { movie ->
						dao.deleteCategorisedMovie(movie !!.movieId !!, Category.POPULAR)
						dao.insertCategorisedMovie(
							movie.toDomainModel(Category.POPULAR).toCacheModel()
						)
					}
					b.orEmpty().forEach { movie ->
						dao.deleteCategorisedMovie(movie !!.movieId !!, Category.NOW_PLAYING)
						dao.insertCategorisedMovie(
							movie.toDomainModel(Category.NOW_PLAYING).toCacheModel()
						)
					}
					c.orEmpty().forEach { movie ->
						dao.deleteCategorisedMovie(movie !!.movieId !!, Category.TOP_RATED)
						dao.insertCategorisedMovie(
							movie.toDomainModel(Category.TOP_RATED).toCacheModel()
						)
					}
					d.orEmpty().forEach { movie ->
						dao.deleteCategorisedMovie(movie !!.movieId !!, Category.UPCOMING)
						dao.insertCategorisedMovie(
							movie.toDomainModel(Category.UPCOMING).toCacheModel()
						)
					}
				}
			} catch (e: NetworkUnavailableException) {
				Logger.e("${e.message}", e)
				emit(
					Resource.Error(
						message = "${e.message}",
						data = allLocalMovies
					)
				)
			} catch (e: HttpException) {
				Logger.e("${e.message}", e)
				emit(
					Resource.Error(
						message = "${e.message}",
						data = allLocalMovies
					)
				)
			}
			val updatedPopularMovies = CoroutineScope(dispatchersProvider.io()).async {
				dao.getCategorisedMovies(Category.POPULAR).map { it.toDomainModel() }
					.take(noOfItems)
			}
			val updatedNowPlayingMovies = CoroutineScope(dispatchersProvider.io()).async {
				dao.getCategorisedMovies(Category.NOW_PLAYING).map { it.toDomainModel() }
					.take(noOfItems)
			}
			val updatedTopRatedMovies = CoroutineScope(dispatchersProvider.io()).async {
				dao.getCategorisedMovies(Category.TOP_RATED).map { it.toDomainModel() }
					.take(noOfItems)
			}
			val updatedUpcomingMovies = CoroutineScope(dispatchersProvider.io()).async {
				dao.getCategorisedMovies(Category.UPCOMING).map { it.toDomainModel() }
					.take(noOfItems)
			}
			val updatedLocalMovies = awaitAll(
				updatedPopularMovies,
				updatedNowPlayingMovies,
				updatedTopRatedMovies,
				updatedUpcomingMovies
			)
			emit(Resource.Success(data = updatedLocalMovies))
		}
	}

	override fun getMovieDetail(
		lang: String,
		movieId: Int,
	): Flow<Resource<MovieDetail>> {
		return flow {
			emit(Resource.Loading())
			val categorisedMovie = dao.getCategorisedMovieById(movieId)
			val movieDetail = dao.getMovieDetails(movieId)?.toDomainModel(categorisedMovie)
			emit(Resource.Loading(movieDetail))
			try {
				retry {
					val apiMovieDetail = api.fetchMovieDetailsById(movieId.toLong())
					dao.apply {
						deleteMovieDetail(movieId)
						insertMovieDetails(
							apiMovieDetail.toDomainModel(
								categorisedMovie.toDomainModel()
							)
								.toCacheModel()
						)
					}
				}
			} catch (e: NetworkUnavailableException) {
				Logger.e("${e.message}", e)
				emit(
					Resource.Error(
						message = "${e.message}",
						data = movieDetail
					)
				)
			} catch (e: HttpException) {
				Logger.e("${e.message}", e)
				emit(
					Resource.Error(
						message = "${e.message}",
						data = movieDetail
					)
				)
			}
			val updatedMovieDetail = dao.getMovieDetails(movieId)?.toDomainModel(categorisedMovie)
			emit(Resource.Success(updatedMovieDetail))
		}
	}

	override fun getCategorisedMovies(category: Category): Flow<List<CategorisedMovie>> {
		return dao.getAllMoviesByCategory(category)
			.distinctUntilChanged()
			.map { moviesList -> moviesList.map { it.toDomainModel() } }
	}

	override suspend fun requestForMoreCategorisedMovies(
		pageToLoad: Int,
		category: Category
	): CategorisedPaginatedMovies {
		preferences.languageTag = Locale.getDefault().toLanguageTag()
		val lang = preferences.languageTag
		try {
			val apiPaginatedMovies = retry(times = 2) {
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
			return apiPaginatedMovies.toDomainModel(category)
		} catch (exception: HttpException) {
			throw NetworkException(
				exception.message() ?: "Code ${exception.code()}"
			)
		}
	}

	override suspend fun storeCategorisedMovies(
		movies: List<CategorisedMovie>
	) {
		dao.insertCategorisedMovies(movies.map { it.toCacheModel() })
	}

	override fun searchCachedMoviesBy(query: String): Flow<List<Movie>> {
		return dao.searchMoviesByTitle(query)
			.distinctUntilChanged()
			.map { moviesList -> moviesList.map { it.toMovie() } }
	}

	override suspend fun searchMoviesRemotely(
		query: String,
		pageToLoad: Int
	): PaginatedMovies {
		val lang = preferences.languageTag
		try {
			val apiSearchResponse = retry(2) {
				api.searchForMovies(lang, query, pageToLoad)
			}
			return apiSearchResponse.toDomainModel()
		} catch (exception: HttpException) {
			throw NetworkException(
				exception.message() ?: "Code ${exception.code()}"
			)
		}
	}

	private suspend fun <T> retry(
		times: Int = 5,
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

