package dev.muyiwa.common.data.cache.entities.test_run.cross_ref

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.category.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*

@Entity(
	tableName = MovieCategoryCrossRef.tableName,
	primaryKeys = ["movieId", "categoryName"],
	foreignKeys = [
		ForeignKey(entity = CachedMovie::class,
			parentColumns = ["movieId"],
			childColumns = ["movieId"]),
		ForeignKey(entity = CachedCategory::class,
			parentColumns = ["categoryName"],
			childColumns = ["categoryName"])
	]
)
data class MovieCategoryCrossRef(
	val movieId: Int,
	val categoryName: String
) {
	companion object {
		const val tableName = "movie_category_cross_ref"
	}
}
