package dev.muyiwa.common.data.cache.entities.test_run.relation_class

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.cross_ref.*
import dev.muyiwa.common.data.cache.entities.test_run.genre.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*

data class CachedMovieWithGenres(
	@Embedded val movie: CachedMovie,
	@Relation(
		parentColumn = "movieId",
		entityColumn = "genreId",
		associateBy = Junction(
			value = MovieGenreCrossRef::class,
			parentColumn = "movieId",
			entityColumn = "genreId"
		)
	)
	val genres: List<CachedGenre>
)

