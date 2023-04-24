package dev.muyiwa.common.data.cache.entities.test_run.detail

import androidx.room.*

@Entity(tableName = CachedDetail.tableName)
/** Has a one-to-one relationship with the Movie table.*/
data class CachedDetail(
	@PrimaryKey val movieId: Int,
	val budget: Long,
	val homepage: String,
	val revenue: Long,
	val runtime: Int,
	val status: String,
	val tagline: String,
){
	companion object {
		const val tableName = "detail"
	}
}
