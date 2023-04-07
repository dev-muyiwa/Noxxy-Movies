package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.*

@Dao
interface BookmarksDao {
	@Query("SELECT * FROM ${CachedBookmarkedMovie.tableName} WHERE movieId = :id")
	suspend fun getBookmarkedMovieId(id: Int): Int

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun addMovieIdToBookmarks(id: CachedBookmarkedMovie)

	@Query("DELETE FROM ${CachedBookmarkedMovie.tableName} WHERE movieId = :id")
	suspend fun removeMovieIdFromBookmarks(id: Int)
}