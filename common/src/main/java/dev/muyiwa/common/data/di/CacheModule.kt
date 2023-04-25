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

	@Binds
	abstract fun bindCache(cache: RoomCache): Cache

	companion object {
		@Provides
		@Singleton
		fun provideNoxxyDatabase(@ApplicationContext context: Context): NoxxyDb {
			return Room.databaseBuilder(
				context,
				NoxxyDb::class.java,
				"noxxy.db"
			).fallbackToDestructiveMigration()
				.build()
		}

		@Provides
		fun provideMovieDao(db: NoxxyDb): MovieDao = db.getMovieDao()

		@Provides
		fun provideBookmarksDao(db: NoxxyDb): BookmarksDao = db.getBookmarkDao()

		@Provides
		fun provideCastDao(db: NoxxyDb): CastDao = db.getCastDao()

		@Provides
		fun provideDetailDao(db: NoxxyDb): DetailDao = db.getDetailDao()

		@Provides
		fun provideGenreDao(db: NoxxyDb): GenreDao = db.getGenreDao()

		@Provides
		fun provideReviewDao(db: NoxxyDb): ReviewDao = db.getReviewDao()

		@Provides
		fun provideCategoryDao(db: NoxxyDb): CategoryDao = db.getCategoryDao()
	}
}