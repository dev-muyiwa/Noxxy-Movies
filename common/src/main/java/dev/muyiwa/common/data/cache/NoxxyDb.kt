package dev.muyiwa.common.data.cache

import androidx.room.*
import dev.muyiwa.common.data.cache.daos.*
import dev.muyiwa.common.data.cache.entities.test_run.bookmark.*
import dev.muyiwa.common.data.cache.entities.test_run.cast.*
import dev.muyiwa.common.data.cache.entities.test_run.category.*
import dev.muyiwa.common.data.cache.entities.test_run.cross_ref.*
import dev.muyiwa.common.data.cache.entities.test_run.detail.*
import dev.muyiwa.common.data.cache.entities.test_run.genre.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*
import dev.muyiwa.common.data.cache.entities.test_run.review.*

@Database(
	entities = [
		CachedMovie::class,
		CachedDetail::class,
		CachedGenre::class,
		CachedCategory::class,
		CachedBookmark::class,
		CachedCast::class,
		CacheReview::class,
		MovieCategoryCrossRef::class,
		MovieGenreCrossRef::class
	],
	exportSchema = true,
	version = 1
)
abstract class NoxxyDb : RoomDatabase() {
	abstract fun getMovieDao(): MovieDao
	abstract fun getDetailDao(): DetailDao
	abstract fun getGenreDao(): GenreDao
	abstract fun getCategoryDao(): CategoryDao
	abstract fun getBookmarkDao(): BookmarksDao
	abstract fun getCastDao(): CastDao
	abstract fun getReviewDao(): ReviewDao
}