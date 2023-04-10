package dev.muyiwa.common.data.cache.entities.references

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.category.*
import dev.muyiwa.common.data.cache.entities.movie.*

data class CachedMovieWithCategories(
	@Embedded val movie: CachedMovie,
	@Relation(
		parentColumn = CachedMovie.primaryKey,
		entityColumn = CachedCategory.primaryKey
	) val categories: List<CachedCategory>
)
