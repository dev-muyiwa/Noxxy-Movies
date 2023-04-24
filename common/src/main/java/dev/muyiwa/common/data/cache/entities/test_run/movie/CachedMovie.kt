package dev.muyiwa.common.data.cache.entities.test_run.movie

import androidx.room.*

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
	val voteAverage: Double,
	val voteCount: Int
) {
	companion object {
		const val tableName = "movie"
	}
}
