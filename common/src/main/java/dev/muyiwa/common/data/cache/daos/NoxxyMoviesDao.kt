package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.*
import dev.muyiwa.common.domain.utils.*
import kotlinx.coroutines.flow.*

@Dao
interface NoxxyMoviesDao {
	@Query("SELECT *  FROM ${CachedCategorisedMovie.tableName} WHERE category = :category ORDER BY id ASC")
	suspend fun getCategorisedMovies(category: Category): List<CachedCategorisedMovie>

	@Query("SELECT *  FROM ${CachedCategorisedMovie.tableName} WHERE category = :category")
	fun getAllMoviesByCategory(category: Category): Flow<List<CachedCategorisedMovie>>

//	@Query("SELECT * FROM movies WHERE title LIKE '%' || :searchQuery || '%'")
//	fun searchMovies(searchQuery: String): List<Movie>


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

	//
	@Query("SELECT * FROM ${CachedCategorisedMovie.tableName} WHERE isBookmarked = 1 ORDER BY id DESC")
	fun getBookmarkedMovies(): Flow<List<CachedCategorisedMovie>>
}