package dev.muyiwa.common.data.cache.entities.test_run.review

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*

@Entity(
	tableName = CacheReview.tableName,
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
data class CacheReview(
	@PrimaryKey(autoGenerate = true) val reviewId: Int = 0,
	val author: String,
	val content: String,
	val movieId: Int
){
	companion object {
		const val tableName = "review"
	}
}
