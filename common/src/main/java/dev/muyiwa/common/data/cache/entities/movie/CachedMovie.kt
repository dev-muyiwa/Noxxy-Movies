package dev.muyiwa.common.data.cache.entities.movie

import androidx.room.*
import dev.muyiwa.common.domain.utils.*

@Entity(tableName = CachedMovie.tableName)
data class CachedMovie(
	@PrimaryKey val movieId: Int,
	val isAdult: Boolean,
	val backdropPath: String,
	val originalLanguage: String,
	val originalTitle: String,
	val overview: String,
	val popularity: Double,
	val posterPath: String,
	val releaseDate: String,
	val title: String,
	val video: Boolean,
	val voteAverage: Double,
	val voteCount: Int,
){
	companion object {
		const val tableName = "movie"
		const val primaryKey = "movieId"
	}
}
