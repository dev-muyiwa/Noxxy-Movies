package dev.muyiwa.common.data.cache.entities.test_run.relation_class

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.bookmark.*
import dev.muyiwa.common.data.cache.entities.test_run.cast.*
import dev.muyiwa.common.data.cache.entities.test_run.cross_ref.*
import dev.muyiwa.common.data.cache.entities.test_run.detail.*
import dev.muyiwa.common.data.cache.entities.test_run.genre.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*
import dev.muyiwa.common.data.cache.entities.test_run.review.*

data class CachedMovieWithDetailsAndGenres(
	@Embedded val movie: CachedMovie,
	@Relation(
		parentColumn = "movieId",
		entityColumn = "movieId",
		entity = CachedDetail::class
	) val detail: CachedDetail?,
	@Relation(
		parentColumn = "movieId",
		entityColumn = "genreId",
		associateBy = Junction(MovieGenreCrossRef::class)
	) val genres: List<CachedGenre>,
	@Relation(
		parentColumn = "movieId",
		entityColumn = "movieId",
		entity = CachedBookmark::class
	) val bookmark: CachedBookmark?,
	@Relation(
		parentColumn = "movieId",
		entity = CachedCast::class,
		entityColumn = "movieId"
	)
	val casts: List<CachedCast>,
	@Relation(
		parentColumn = "movieId",
		entity = CacheReview::class,
		entityColumn = "movieId"
	)
	val reviews: List<CacheReview>
)
