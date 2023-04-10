package dev.muyiwa.common.data.cache

import androidx.room.*
import dev.muyiwa.common.data.cache.daos.*
import dev.muyiwa.common.data.cache.entities.*
import dev.muyiwa.common.data.cache.entities.detail.*
import dev.muyiwa.common.data.cache.entities.movie.*
import dev.muyiwa.common.data.cache.utils.*
import okhttp3.*

@Database(
	entities = [
		CachedCategorisedMovie::class,
		CachedMovieDetails::class,
		CachedCast::class,
		CachedBookmarkedMovie::class,
		CachedMovie::class,
	CachedDetail::class
	],
	exportSchema = true,
	version = 5
)
@TypeConverters(Converter::class)
abstract class NoxxyDatabase : RoomDatabase() {
	abstract fun getMoviesDao(): NoxxyMoviesDao
	abstract fun getBookmarksDao(): BookmarksDao
}