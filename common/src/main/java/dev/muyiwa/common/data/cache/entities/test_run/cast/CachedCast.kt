package dev.muyiwa.common.data.cache.entities.test_run.cast

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import dev.muyiwa.common.data.cache.entities.test_run.movie.*

@Entity(
	tableName = CachedCast.tableName,
	foreignKeys = [
		ForeignKey(
			entity = CachedMovie::class,
			parentColumns = ["movieId"],
			childColumns = ["movieId"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
/** Has a many-to-one relationship with the Movie table.*/
data class CachedCast(
	@PrimaryKey(autoGenerate = true) val castId: Int = 0,
	val name: String,
	val character: String,
	val profilePath: String,
	val movieId: Int
){
	companion object {
		const val tableName = "cast"
	}
}