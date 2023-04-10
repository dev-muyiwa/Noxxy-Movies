package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.*
import dev.muyiwa.common.data.cache.entities.category.*
import dev.muyiwa.common.data.cache.entities.detail.*
import dev.muyiwa.common.data.cache.entities.genre.*
import dev.muyiwa.common.data.cache.entities.movie.*
import dev.muyiwa.common.data.cache.entities.references.*
import dev.muyiwa.common.domain.utils.*
import kotlinx.coroutines.flow.*

@Dao
interface NoxxyMoviesDao {
	@Query("SELECT *  FROM ${CachedCategorisedMovie.tableName} WHERE category = :category ORDER BY id ASC")
	suspend fun getCategorisedMovies(category: Category): List<CachedCategorisedMovie>

	@Query("SELECT *  FROM ${CachedCategorisedMovie.tableName} WHERE category = :category")
	fun getAllMoviesByCategory(category: Category): Flow<List<CachedCategorisedMovie>>

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



//	@Dao
//	interface MovieDao {
//		@Transaction
//		@Query("SELECT * FROM movies " +
//				"INNER JOIN movie_details ON movies.id = movie_details.movieId " +
//				"INNER JOIN movies_genres ON movies.id = movies_genres.movieId" +
//				"INNER JOIN genres ON movies_genres.genreId = genres.id " +
//				"WHERE genres.name = :categoryName")
//		fun getMoviesWithDetailsAndGenresByCategory(categoryName: String): List<MovieWithDetailsAndGenres>
//	}


	// Relationships
//	@Transaction
//	@Query("SELECT * FROM ${CachedMovie.tableName} " +
//			"INNER JOIN ${CachedMovieGenreCrossRef.tableName} ON ${CachedMovie.tableName}.movieId = ${CachedMovieGenreCrossRef.tableName}.movieId " +
//			"INNER JOIN ${CachedGenre.tableName} ON ${CachedMovieGenreCrossRef.tableName}.genreId = ${CachedGenre.tableName}.genreId " +
//			"INNER JOIN ${CachedDetail.tableName} ON ${CachedMovie.tableName}.movieId = ${CachedDetail.tableName}.movieId " +
//			"WHERE ${CachedMovie.tableName}.movieId = :id")
//	suspend fun getMovieWithDetail(id: Int): CachedMovieWithDetailAndGenres
//
//	@Transaction
//	@Query(
//		"SELECT * FROM ${CachedMovie.tableName} " +
//				"INNER JOIN ${CachedMovieCategoryCrossRef.tableName} ON ${CachedMovie.tableName}.movieId = ${CachedMovieCategoryCrossRef.tableName}.movieId " +
//				"INNER JOIN ${CachedCategory.tableName} ON ${CachedMovieCategoryCrossRef.tableName}.categoryName = ${CachedCategory.tableName}.categoryName " +
//				"INNER JOIN ${CachedMovieGenreCrossRef.tableName} ON ${CachedMovie.tableName}.movieId = ${CachedMovieGenreCrossRef.tableName}.movieId " +
//				"INNER JOIN ${CachedGenre.tableName} ON ${CachedMovieGenreCrossRef.tableName}.genreId = ${CachedGenre.tableName}.genreId " +
//				"WHERE ${CachedCategory.tableName}.categoryName = :categoryName"
//	)
//	fun getMoviesByCategory(categoryName: String): Flow<List<CachedMovieWithGenresAndCategories>>

//	@Query("SELECT * FROM Movie INNER JOIN Category ON Movie.id = Category.movie_id WHERE Category.variable = :categoryVariable")
//	fun getMoviesByCategory(categoryVariable: String): List<Movie>

}