package dev.muyiwa.common.data.cache.entities.references

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.detail.*
import dev.muyiwa.common.data.cache.entities.genre.*
import dev.muyiwa.common.data.cache.entities.movie.*

data class CachedMovieWithDetailAndGenres(
	@Embedded val movie: CachedMovie,
	@Relation(
		parentColumn = CachedMovie.primaryKey,
		entityColumn = CachedDetail.primaryKeyRef
	) val detail: CachedDetail,
	@Relation(
		parentColumn = CachedMovie.primaryKey,
		entityColumn = CachedGenre.primaryKey,
		associateBy = Junction(CachedMovieGenreCrossRef::class)
	) val genres: List<CachedGenre>
)
