package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.category.*
import dev.muyiwa.common.data.cache.entities.test_run.cross_ref.*

@Dao
interface CategoryDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertCategory(category: CachedCategory): Int

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertMovieCategoryCrossRef(ref: MovieCategoryCrossRef)

	@Query("SELECT ${CachedCategory.tableName}.* FROM ${CachedCategory.tableName} " +
			"INNER JOIN ${MovieCategoryCrossRef.tableName} " +
			"ON ${CachedCategory.tableName}.categoryId = ${MovieCategoryCrossRef.tableName}.categoryId " +
			"WHERE ${MovieCategoryCrossRef.tableName}.movieId = :movieId")
	suspend fun getCategoriesByMovieId(movieId: Long): List<CachedCategory>

}