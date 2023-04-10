package dev.muyiwa.common.data.cache.entities.references

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.category.*
import dev.muyiwa.common.data.cache.entities.genre.*
import dev.muyiwa.common.data.cache.entities.movie.*

data class CachedMovieWithGenresAndCategories(
	@Embedded val movie: CachedMovie,
	@Relation(
		parentColumn = CachedMovie.primaryKey,
		entityColumn = CachedGenre.primaryKey,
		associateBy = Junction(CachedMovieGenreCrossRef::class)
	) val genres: List<CachedGenre>,
	@Relation(
		parentColumn = CachedMovie.primaryKey,
		entityColumn = CachedCategory.primaryKey,
		associateBy = Junction(CachedMovieCategoryCrossRef::class)
	) val categories: List<CachedCategory>,

)
