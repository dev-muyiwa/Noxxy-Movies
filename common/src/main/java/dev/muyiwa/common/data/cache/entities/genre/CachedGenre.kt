package dev.muyiwa.common.data.cache.entities.genre

import androidx.room.*

@Entity(tableName = CachedGenre.tableName)
data class CachedGenre(
	@PrimaryKey val genreId: Int,
	val name: String,
) {
	companion object {
		const val tableName = "genre"
		const val primaryKey = "genreId"
	}
}
