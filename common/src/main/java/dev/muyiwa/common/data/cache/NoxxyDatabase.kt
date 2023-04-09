package dev.muyiwa.common.data.cache

import androidx.room.*
import dev.muyiwa.common.data.cache.daos.*
import dev.muyiwa.common.data.cache.entities.*
import dev.muyiwa.common.data.cache.utils.*
import okhttp3.*

@Database(
	entities = [
		CachedCategorisedMovie::class,
		CachedMovieDetails::class,
		CachedCast::class,
		CachedBookmarkedMovie::class
	],
	exportSchema = true,
	version = 4
)
@TypeConverters(Converter::class)
abstract class NoxxyDatabase : RoomDatabase() {
	abstract fun getMoviesDao(): NoxxyMoviesDao
	abstract fun getBookmarksDao(): BookmarksDao
}