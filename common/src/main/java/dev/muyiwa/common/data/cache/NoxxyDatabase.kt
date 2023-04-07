package dev.muyiwa.common.data.cache

import androidx.room.*
import dev.muyiwa.common.data.cache.daos.*
import dev.muyiwa.common.data.cache.entities.*
import dev.muyiwa.common.data.cache.utils.*

@Database(
	entities = [CachedCategorisedMovie::class, CachedMovieDetails::class, CachedBookmarkedMovie::class],
	exportSchema = true,
	version = 3
)
@TypeConverters(Converter::class)
abstract class NoxxyDatabase : RoomDatabase() {
	abstract fun getMoviesDao(): NoxxyMoviesDao
	abstract fun getBookmarksDao(): BookmarksDao
}