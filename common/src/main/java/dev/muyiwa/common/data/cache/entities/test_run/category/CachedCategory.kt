package dev.muyiwa.common.data.cache.entities.test_run.category

import androidx.room.*

@Entity(tableName = CachedCategory.tableName)
/** Has a many-to-many relationship with the Movie table.*/
data class CachedCategory(
	@PrimaryKey val categoryName: String
) {
	companion object {
		const val tableName = "category"
	}
}
