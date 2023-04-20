package dev.muyiwa.common.data.cache.entities.video

import androidx.room.*

@Entity(tableName = CachedVideo.tableName)
data class CachedVideo(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val movieId: Int,
	val name: String,
	val videoKey: String,
	val type: String,
	val publishedAt: String,
	val videoId: String
) {
	companion object {
		const val tableName = "video"
	}
}
