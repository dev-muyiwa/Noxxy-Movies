package dev.muyiwa.common.data.cache.entities.test_run

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.category.*
import dev.muyiwa.common.data.cache.entities.test_run.cross_ref.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*

data class MovieWithCategory(
	@Embedded val movie: CachedMovie,
	@ColumnInfo(name = "name") val categoryName: String
)
