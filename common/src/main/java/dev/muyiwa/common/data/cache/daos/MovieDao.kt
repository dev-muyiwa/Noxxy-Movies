package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.category.*
import dev.muyiwa.common.data.cache.entities.test_run.cross_ref.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*
import dev.muyiwa.common.data.cache.entities.test_run.relation_class.*

@Dao
interface MovieDao {

	@Query("SELECT * FROM ${CachedMovie.tableName} WHERE movieId = :id")
	suspend fun getMovieById(id: Int): CachedMovie?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	/** Returns the movie ID after a successful insert.*/
	suspend fun insertMovie(movie: CachedMovie): Int

	@Transaction
	@Query(
		"SELECT ${CachedMovie.tableName}.* FROM ${CachedMovie.tableName} " +
				"INNER JOIN ${MovieCategoryCrossRef.tableName} " +
				"ON ${CachedMovie.tableName}.id = ${MovieCategoryCrossRef.tableName}movieId " +
				"INNER JOIN ${CachedCategory.tableName} " +
				"ON ${CachedCategory.tableName}.id = ${MovieCategoryCrossRef.tableName}.categoryId " +
				"WHERE ${MovieCategoryCrossRef.tableName}.categoryId = :categoryId"
	)
			/**
			 * This query joins the movie table with the movie_category_cross_ref table using
			 * the movieId foreign key relationship, and then joins the category table using the
			 * categoryId foreign key relationship. It filters the results by the given categoryId.
			To get the list of movies for multiple categories, you can call this function for each category
			and merge the resulting lists.*/
	fun getMoviesByCategory(categoryId: Int): List<CachedMovie>

	@Transaction
	@Query("SELECT * FROM ${CachedMovie.tableName} WHERE movieId = :id")
	suspend fun getMovieWithCompleteDetailsBy(id: Int): MovieWithDetailsAndGenres



	@Transaction
	@Update
	suspend fun updateMovieWithCategories(movieId: Int, categories: List<CachedCategory>)

}