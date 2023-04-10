package dev.muyiwa.common.data.cache.entities.detail

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.movie.*

@Entity(
	tableName = CachedDetail.tableName,
	foreignKeys = [
		ForeignKey(
			entity = CachedMovie::class,
			parentColumns = [CachedMovie.primaryKey],
			childColumns = [CachedDetail.primaryKeyRef],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class CachedDetail(
	@PrimaryKey(autoGenerate = true) val detailId: Int,
	val movieId: Int,
	val budget: Long,
	val homepage: String,
	val revenue: Long,
	val runtime: Int,
	val status: String,
	val tagline: String,
) {
	companion object {
		const val primaryKeyRef = "movieId"
		const val tableName = "detail"
	}
}



@Entity(tableName = "Movie")
data class Movie(
	@PrimaryKey val id: Int,
	val title: String,
	// other properties
)

@Entity(tableName = "Genre")
data class Genre(
	@PrimaryKey val id: Int,
	val name: String,
	// other properties
)

@Entity(tableName = "MovieGenreCrossRef",
	primaryKeys = ["movieId", "genreId"],
	foreignKeys = [
		ForeignKey(entity = Movie::class, parentColumns = ["id"], childColumns = ["movieId"]),
		ForeignKey(entity = Genre::class, parentColumns = ["id"], childColumns = ["genreId"])
	]
)
data class MovieGenreCrossRef(
	val movieId: Int,
	val genreId: Int
)

data class MovieWithGenres(
	@Embedded val movie: Movie,
	@Relation(
		parentColumn = "id",
		entity = Genre::class,
		entityColumn = "id",
		associateBy = Junction(MovieGenreCrossRef::class)
	)
	val genres: List<Genre>
)

@Dao
interface MovieDao {
	@Transaction
	@Query("SELECT * FROM Movie")
	fun getMoviesWithGenres(): List<MovieWithGenres>
}
