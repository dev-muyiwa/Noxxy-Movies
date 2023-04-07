package dev.muyiwa.common.data.cache.entities

import androidx.room.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.utils.*

@Entity(tableName = CachedCategorisedMovie.tableName)
data class CachedCategorisedMovie(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val isAdult: Boolean,
	val backdropPath: String,
	val genreIds: List<String>,
	val movieId: Int,
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
	val category: Category
) {
	companion object {
		const val tableName = "categorised_movie"
	}
}

