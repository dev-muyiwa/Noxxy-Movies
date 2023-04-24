package dev.muyiwa.common.data.cache.entities.test_run.bookmark

import androidx.room.*
import java.util.*

@Entity(tableName = CachedBookmark.tableName)
/** Has a one-to-one relationship with the Movie table.*/
data class CachedBookmark(
	@PrimaryKey val movieId: Long,
	val isBookmarked: Boolean
){
	companion object {
		const val tableName = "bookmark"
	}
}
