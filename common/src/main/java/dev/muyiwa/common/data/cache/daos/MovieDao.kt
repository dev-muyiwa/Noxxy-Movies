package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.*
import dev.muyiwa.common.data.cache.entities.test_run.bookmark.*
import dev.muyiwa.common.data.cache.entities.test_run.cast.*
import dev.muyiwa.common.data.cache.entities.test_run.category.*
import dev.muyiwa.common.data.cache.entities.test_run.cross_ref.*
import dev.muyiwa.common.data.cache.entities.test_run.detail.*
import dev.muyiwa.common.data.cache.entities.test_run.genre.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*
import dev.muyiwa.common.data.cache.entities.test_run.relation_class.*
import dev.muyiwa.common.data.cache.entities.test_run.review.*
import kotlinx.coroutines.flow.*

@Dao
interface MovieDao {

	@Query("SELECT * FROM ${CachedMovie.tableName} WHERE movieId = :movieId")
	suspend fun getMovieBy(movieId: Int): CachedMovie?

	/** Returns the movie ID after a successful insert.*/
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertMovie(movie: CachedMovie)

//	@Insert(onConflict = OnConflictStrategy.REPLACE)
//	suspend fun insertMovies(movies: List<CachedMovie>)

	/**
	 * This query joins the movie table with the movie_category_cross_ref table using
	 * the movieId foreign key relationship, and then joins the category table using the
	 * categoryId foreign key relationship. It filters the results by the given categoryId.
	To get the list of movies for multiple categories, you can call this function for each category
	and merge the resulting lists.*/
	@Transaction
	@RewriteQueriesToDropUnusedColumns
	@Query(
		"SELECT ${CachedMovie.tableName}.*, ${CachedGenre.tableName}.* FROM ${CachedMovie.tableName} " +
				"INNER JOIN ${MovieCategoryCrossRef.tableName} " +
				"ON ${CachedMovie.tableName}.movieId = ${MovieCategoryCrossRef.tableName}.movieId " +
				"INNER JOIN ${CachedCategory.tableName} " +
				"ON ${CachedCategory.tableName}.categoryName = ${MovieCategoryCrossRef.tableName}.categoryName " +
				"INNER JOIN ${MovieGenreCrossRef.tableName} " +
				"ON ${CachedMovie.tableName}.movieId = ${MovieGenreCrossRef.tableName}.movieId " +
				"INNER JOIN ${CachedGenre.tableName} " +
				"ON ${CachedGenre.tableName}.genreId = ${MovieGenreCrossRef.tableName}.genreId " +
				"WHERE ${MovieCategoryCrossRef.tableName}.categoryName = :categoryName " +
				"ORDER BY ${CachedMovie.tableName}.created_at ASC"
	)
	fun getMoviesAsFlowBy(categoryName: String): Flow<List<CachedMovieWithGenres>>

	@Transaction
	@RewriteQueriesToDropUnusedColumns
	@Query(
		"SELECT ${CachedMovie.tableName}.*, ${CachedGenre.tableName}.* FROM ${CachedMovie.tableName} " +
				"INNER JOIN ${MovieCategoryCrossRef.tableName} ON ${CachedMovie.tableName}.movieId = ${MovieCategoryCrossRef.tableName}.movieId " +
				"INNER JOIN ${CachedCategory.tableName} ON ${CachedCategory.tableName}.categoryName = ${MovieCategoryCrossRef.tableName}.categoryName " +
				"INNER JOIN ${MovieGenreCrossRef.tableName} ON ${CachedMovie.tableName}.movieId = ${MovieGenreCrossRef.tableName}.movieId " +
				"INNER JOIN ${CachedGenre.tableName} ON ${CachedGenre.tableName}.genreId = ${MovieGenreCrossRef.tableName}.genreId " +
				"WHERE ${MovieCategoryCrossRef.tableName}.categoryName = :categoryName " +
				"ORDER BY ${CachedMovie.tableName}.created_at ASC LIMIT :count "
	)
	suspend fun getMoviesBy(categoryName: String, count: Int): List<CachedMovieWithGenres>

	@Transaction
	@RewriteQueriesToDropUnusedColumns
	@Query(
		"SELECT ${CachedMovie.tableName}.*, ${CachedGenre.tableName}.name AS name " +
				"FROM ${CachedMovie.tableName} " +
				"LEFT JOIN ${MovieGenreCrossRef.tableName} " +
				"ON ${CachedMovie.tableName}.movieId = ${MovieGenreCrossRef.tableName}.movieId " +
				"LEFT JOIN ${CachedGenre.tableName} " +
				"ON ${MovieGenreCrossRef.tableName}.genreId = ${CachedGenre.tableName}.genreId " +
				"INNER JOIN ${CachedBookmark.tableName} " +
				"ON ${CachedMovie.tableName}.movieId = ${CachedBookmark.tableName}.movieId " +
				"ORDER BY ${CachedBookmark.tableName}.bookmarked_at DESC"
	)
	fun getBookmarkedMoviesWithGenres(): Flow<List<CachedMovieWithGenres>>

	@Query(
		"""
		    SELECT * FROM ${CachedMovie.tableName} 
		    INNER JOIN ${MovieGenreCrossRef.tableName} ON ${CachedMovie.tableName}.movieId = movie_genre_cross_ref.movieId 
		    INNER JOIN genre ON ${MovieGenreCrossRef.tableName}.genreId = ${CachedGenre.tableName}.genreId 
		    WHERE ${CachedMovie.tableName}.title LIKE '%' || :query || '%'  
			ORDER BY ${CachedMovie.tableName}.created_at ASC
		"""
	)
	fun searchMoviesByTitleAndGenres(query: String): Flow<List<CachedMovieWithGenres>>


	@Transaction
	@Query("SELECT * FROM ${CachedMovie.tableName} WHERE movieId = :movieId")
	suspend fun getMovieWithCompleteDetailsBy(movieId: Int): CachedMovieWithDetailsAndGenres

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertMovieWithCompleteDetails(
		movie: CachedMovie,
		detail: CachedDetail,
		genres: List<CachedGenre>,
		casts: List<CachedCast>,
		reviews: List<CacheReview>,
		bookmark: CachedBookmark
	)

	@Upsert
	suspend fun upsertMovieWithCompleteDetails(
		movie: CachedMovie,
		detail: CachedDetail,
		genres: List<CachedGenre>,
		casts: List<CachedCast>,
		reviews: List<CacheReview>
	)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertMovieWithGenres(
		movie: CachedMovie,
		genres: List<CachedGenre>
	)

	@Transaction
	@Upsert
	suspend fun upsertMovieWithGenres(
		movie: CachedMovie,
		genres: List<CachedGenre>,
		categories: List<CachedCategory>
	)
}