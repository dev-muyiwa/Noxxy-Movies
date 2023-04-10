package dev.muyiwa.common.data.cache.entities.references

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.category.*
import dev.muyiwa.common.data.cache.entities.genre.*
import dev.muyiwa.common.data.cache.entities.movie.*

@Entity(
	tableName = CachedMovieCategoryCrossRef.tableName,
	primaryKeys = ["movieId", "categoryName"],
	foreignKeys = [
		ForeignKey(entity = CachedMovie::class, parentColumns = [CachedMovie.primaryKey], childColumns = ["movieId"]),
		ForeignKey(entity = CachedCategory::class, parentColumns = [CachedCategory.primaryKey], childColumns = ["categoryName"])
	]
)
data class CachedMovieCategoryCrossRef(
	val movieId: Int,
	val categoryName: String
) {
	companion object {
		const val tableName = "movie_category_ref"
	}
}
