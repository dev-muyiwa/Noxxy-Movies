package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.*
import dev.muyiwa.common.data.cache.entities.video.*
import dev.muyiwa.common.domain.utils.*
import kotlinx.coroutines.flow.*

@Dao
interface NoxxyMoviesDao {
	@Query("SELECT *  FROM ${CachedCategorisedMovie.tableName} WHERE category = :category ORDER BY id ASC")
	suspend fun getCategorisedMovies(category: Category): List<CachedCategorisedMovie>

	@Query("SELECT *  FROM ${CachedCategorisedMovie.tableName} WHERE category = :category")
	fun getAllMoviesByCategory(category: Category): Flow<List<CachedCategorisedMovie>>

	@Query("SELECT *  FROM ${CachedCategorisedMovie.tableName} " +
			"WHERE title LIKE '%' || :query || '%'")
	fun searchMoviesByTitle(query: String): Flow<List<CachedCategorisedMovie>>

	suspend fun insertCategorisedMovies(movies: List<CachedCategorisedMovie>){
		movies.forEach { movie -> insertCategorisedMovie(movie) }
	}

	@Query("SELECT * FROM ${CachedCategorisedMovie.tableName} WHERE movieId = :movieId")
	suspend fun getCategorisedMovieById(movieId: Int): CachedCategorisedMovie

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertCategorisedMovie(movie: CachedCategorisedMovie)

	@Query("DELETE FROM ${CachedCategorisedMovie.tableName} WHERE movieId = :id AND category = :category")
	suspend fun deleteCategorisedMovie(id: Int, category: Category)
	/** The category is required as a movie can appear in multiple categories*/

//	@Query("DELETE * FROM ${CachedCategorisedMovie.tableName} WHERE category = :category")
//	suspend fun deleteAllMoviesByCategory(category: Category)


//	Movie Details
	@Query("SELECT * FROM ${CachedMovieDetails.tableName} WHERE movieId = :id")
	suspend fun getMovieDetails(id: Int): CachedMovieDetails?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertMovieDetails(movie: CachedMovieDetails)

	@Query("DELETE FROM ${CachedMovieDetails.tableName} WHERE movieId = :id")
	suspend fun deleteMovieDetail(id: Int)

	@Query("SELECT * FROM ${CachedCast.tableName} WHERE movieId = :id")
	suspend fun getCastsById(id: Int): List<CachedCast>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertCast(cast: CachedCast)

	suspend fun insertAllCasts(casts: List<CachedCast>){
		casts.forEach { cast -> insertCast(cast) }
	}

	@Query("DELETE FROM ${CachedCast.tableName} WHERE movieId = :id")
	suspend fun deleteCastById(id: Int)

	@Query("SELECT * FROM ${CachedReview.tableName} WHERE movieId = :id")
	suspend fun getReviewsById(id: Int): List<CachedReview>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertReview(review: CachedReview)

	suspend fun insertAllReviews(reviews: List<CachedReview>){
		reviews.forEach { review -> insertReview(review) }
	}

	@Query("DELETE FROM ${CachedReview.tableName} WHERE movieId = :id")
	suspend fun deleteReviewById(id: Int)

	//
	@Query("SELECT * FROM ${CachedCategorisedMovie.tableName} WHERE isBookmarked = 1 ORDER BY id DESC")
	fun getBookmarkedMovies(): Flow<List<CachedCategorisedMovie>>


	@Query("SELECT * FROM ${CachedVideo.tableName} WHERE movieId = :id ORDER BY publishedAt DESC")
	suspend fun getAllVideosBy(id: Int): List<CachedVideo>

	@Query("SELECT * FROM ${CachedBookmarkedMovie.tableName} WHERE movieId = :id")
	suspend fun doesBookmarkedIdExist(id: Int): Boolean

	@Query("SELECT * FROM ${CachedBookmarkedMovie.tableName} ORDER BY movieId DESC")
	suspend fun getBookmarkedIds(): List<CachedBookmarkedMovie>

	fun getBookmarkedMovies(ids: List<CachedBookmarkedMovie>): Flow<List<CachedCategorisedMovie>> {
		return flow { emit(ids.map { id -> getCategorisedMovieById(id.movieId) }) }
	}

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun addToBookmarks(id: CachedBookmarkedMovie)

	@Delete
	suspend fun deleteFromBookmarks(id: CachedBookmarkedMovie)
}