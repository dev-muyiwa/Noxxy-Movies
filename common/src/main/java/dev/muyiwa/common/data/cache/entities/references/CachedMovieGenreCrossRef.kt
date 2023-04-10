package dev.muyiwa.common.data.cache.entities.references

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.genre.*
import dev.muyiwa.common.data.cache.entities.movie.*

@Entity(
	tableName = CachedMovieGenreCrossRef.tableName,
	primaryKeys = ["movieId", "genreId"],
	foreignKeys = [
		ForeignKey(entity = CachedMovie::class, parentColumns = [CachedMovie.primaryKey], childColumns = ["movieId"]),
		ForeignKey(entity = CachedGenre::class, parentColumns = [CachedGenre.primaryKey], childColumns = ["genreId"])
	]
)
data class CachedMovieGenreCrossRef(
	val movieId: Int,
	val genreId: Int
) {
	companion object {
		const val tableName = "movie_genre_ref"
	}
}