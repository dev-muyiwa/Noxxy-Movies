package dev.muyiwa.common.data.cache.entities.test_run.bookmark

import androidx.room.*

@Entity(tableName = CachedBookmark.tableName)
/** Has a one-to-one relationship with the Movie table.*/
data class CachedBookmark(
	@PrimaryKey val movieId: Int,
	val bookmarked_at: Long = System.currentTimeMillis()
){
	companion object {
		const val tableName = "bookmark"
	}
}
