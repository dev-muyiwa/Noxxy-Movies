package dev.muyiwa.common.data.cache.entities

import androidx.room.*

@Entity(tableName = CachedCast.tableName)
data class CachedCast(
	@PrimaryKey(autoGenerate = false) val movieId: Int,
	val originalName: String,
	val profilePath: String,
	val character: String
) {
	companion object {
		const val tableName = "casts"
	}
}