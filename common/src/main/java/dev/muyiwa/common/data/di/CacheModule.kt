package dev.muyiwa.common.data.di

import android.content.*
import androidx.room.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*
import dev.muyiwa.common.data.cache.*
import dev.muyiwa.common.data.cache.daos.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

	companion object {
		@Provides
		@Singleton
		fun provideNoxxyDatabase(@ApplicationContext context: Context): NoxxyDatabase {
			return Room.databaseBuilder(
				context,
				NoxxyDatabase::class.java,
				"noxxy.db"
			).fallbackToDestructiveMigration()
				.build()
		}

		@Provides
		fun provideMoviesDao(
			db: NoxxyDatabase
		): NoxxyMoviesDao = db.getMoviesDao()

		@Provides
		fun provideBookmarksDao(
			db: NoxxyDatabase
		): BookmarksDao = db.getBookmarksDao()
	}
}