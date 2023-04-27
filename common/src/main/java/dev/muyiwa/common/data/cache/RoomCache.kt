package dev.muyiwa.common.data.cache

import dev.muyiwa.common.data.cache.daos.*
import dev.muyiwa.common.data.cache.entities.test_run.cast.*
import dev.muyiwa.common.data.cache.entities.test_run.category.*
import dev.muyiwa.common.data.cache.entities.test_run.cross_ref.*
import dev.muyiwa.common.data.cache.entities.test_run.detail.*
import dev.muyiwa.common.data.cache.entities.test_run.genre.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*
import dev.muyiwa.common.data.cache.entities.test_run.relation_class.*
import dev.muyiwa.common.data.cache.entities.test_run.review.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class RoomCache @Inject constructor(
	private val movieDao: MovieDao,
	private val categoryDao: CategoryDao,
	private val genreDao: GenreDao,
	private val castDao: CastDao,
	private val reviewDao: ReviewDao,
	private val detailDao: DetailDao
) : Cache {
	override fun getMoviesAsFlowBy(category: CachedCategory): Flow<List<CachedMovieWithGenres>> {
		return movieDao.getMoviesAsFlowBy(category.categoryName)
	}

	override suspend fun getMoviesWithGenreBy(
		category: CachedCategory,
		count: Int
	): List<CachedMovieWithGenres> {
		return movieDao.getMoviesBy(category.categoryName, count)
	}

	override suspend fun storeMoviesBy(movies: List<CachedMovie>, category: CachedCategory) {
		movies.forEach { movie ->
//			movieDao.insertMovie(movie)
//			categoryDao.insertCategory(category)
//
//			val cachedMovie = movieDao.getMovieBy(movie.movieId)
//			if (cachedMovie != null) {
//				val existingCategories = categoryDao.getCategoriesBy(movie.movieId).toMutableList()
//				existingCategories.add(category)
//				existingCategories.forEach { category ->
//					movieDao.updateMovieWithCategory(movie.movieId, category)
//				}
////				movieDao.updateMovieWithCategory(movie.movieId, existingCategories)
//			} else {
				val ref = MovieCategoryCrossRef(movie.movieId, category.categoryName)
				movieDao.insertMovie(movie)
				categoryDao.insertCategory(category)
				categoryDao.insertMovieCategoryCrossRef(ref)
//			}
		}
	}

	// This will be used for the home, search and more movies screen
	override suspend fun storeMoviesWithGenreBy(
		moviesWithGenres: List<CachedMovieWithGenres>,
		category: CachedCategory?
	) {
		moviesWithGenres.forEach { (movie, genres) ->
			movieDao.insertMovie(movie)
			genreDao.insertGenres(genres)
			genres.forEach { genre ->
				val genreRef = MovieGenreCrossRef(movie.movieId, genre.genreId)
				genreDao.insertMovieGenreCrossRef(genreRef)
			}
			if (category != null) {
				val categoryRef = MovieCategoryCrossRef(movie.movieId, category.categoryName)
				categoryDao.insertCategory(category)
				categoryDao.insertMovieCategoryCrossRef(categoryRef)
			}
		}
		/**
		moviesWithGenres.forEach { list ->
			val (movie, genres) = list
			val cachedMovie = movieDao.getMovieBy(movie.movieId)
			if (cachedMovie != null) {
				val existingCategories = categoryDao.getCategoriesBy(movie.movieId).toMutableList()
				if (category != null) {
					existingCategories.add(category)
				}
//				existingCategories.forEach {
//					val ref = MovieCategoryCrossRef(movie.movieId, it.categoryName)
//					categoryDao.insertMovieCategoryCrossRef(ref)
//				}
				val categoryRefs =
					existingCategories.map { MovieCategoryCrossRef(movie.movieId, it.categoryName) }
				categoryDao.upsertMovieCategoryCrossRefs(categoryRefs)
				movieDao.upsertMovieWithGenres(cachedMovie, genres, existingCategories)
//				movieDao.updateMovieWithCategories(movie.movieId, existingCategories)
			} else {
				val categoryRef = category?.let { MovieCategoryCrossRef(movie.movieId, it.categoryName) }
				movieDao.insertMovie(movie)
				if (category != null) {
					categoryDao.insertCategory(category)
				}
				genres.forEach { genre ->
					val genreRef = MovieGenreCrossRef(movie.movieId, genre.genreId)
					genreDao.insertGenre(genre)
					genreDao.insertMovieGenreCrossRef(genreRef)
				}
				categoryRef?.let { categoryDao.insertMovieCategoryCrossRef(it) }
			}
		}
		*/
	}

	override suspend fun getMovieWithCompleteDetails(movieId: Int): CachedMovieWithDetailsAndGenres {
		return movieDao.getMovieWithCompleteDetailsBy(movieId)
	}

	override fun searchMoviesBy(title: String): Flow<List<CachedMovieWithGenres>> {
		return movieDao.searchMoviesByTitleAndGenres(title)
	}

	override suspend fun storeDetails(
		movie: CachedMovie,
		detail: CachedDetail,
		genres: List<CachedGenre>,
		casts: List<CachedCast>,
		reviews: List<CacheReview>
	) {
		movieDao.upsertMovieWithCompleteDetails(movie, detail, genres, casts, reviews)
		genres.forEach { genre ->
			val genreRef = MovieGenreCrossRef(movie.movieId, genre.genreId)
			genreDao.insertMovieGenreCrossRef(genreRef)
		}
	}
}