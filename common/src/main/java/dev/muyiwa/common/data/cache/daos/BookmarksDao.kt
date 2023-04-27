package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.*
import dev.muyiwa.common.data.cache.entities.test_run.bookmark.*

@Dao
interface BookmarksDao {
//	@Query("SELECT * FROM ${CachedBookmarkedMovie.tableName} WHERE movieId = :movieId")
//	suspend fun getBookmarkBy(movieId: Int): CachedBookmark

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun addToBookmark(bookmark: CachedBookmark)

	@Delete
	suspend fun deleteFromBookmark(bookmark: CachedBookmark)

}