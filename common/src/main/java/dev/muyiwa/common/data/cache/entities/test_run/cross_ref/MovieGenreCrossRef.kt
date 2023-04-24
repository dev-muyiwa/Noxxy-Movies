package dev.muyiwa.common.data.cache.entities.test_run.cross_ref

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.genre.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*

@Entity(
	tableName = MovieGenreCrossRef.tableName,
	primaryKeys = ["movieId", "genreId"],
	foreignKeys = [
		ForeignKey(entity = CachedMovie::class, parentColumns = ["movieId"], childColumns = ["movieId"]),
		ForeignKey(entity = CachedGenre::class, parentColumns = ["genreId"], childColumns = ["genreId"])
	]
)
data class MovieGenreCrossRef(
	val movieId: Int,
	val genreId: Int
) {
	companion object {
		const val tableName = "movie_genre_cross_ref"
	}
}
