package dev.muyiwa.common.data.cache.entities

import androidx.room.*
import com.squareup.moshi.*

@Entity(tableName = CachedMovieDetails.tableName)
data class CachedMovieDetails(
	val budget: Long,
	val homepage: String,
	@PrimaryKey val movieId: Int,
	val revenue: Long,
	val runtime: Int,
	val status: String,
	val tagline: String,
){
	companion object {
		const val tableName = "movie_details"
	}
}
