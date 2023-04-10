package dev.muyiwa.common.data.cache.entities.category

import androidx.room.*

@Entity(tableName = CachedCategory.tableName)
data class CachedCategory(
	@PrimaryKey val categoryName: String,
){
	companion object {
		const val tableName = "category"
		const val primaryKey = "categoryName"
	}
}
