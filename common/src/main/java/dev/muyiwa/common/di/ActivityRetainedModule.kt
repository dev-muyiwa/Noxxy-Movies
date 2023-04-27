package dev.muyiwa.common.di

import android.content.*
import android.os.*
import coil.*
import coil.decode.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.components.*
import dagger.hilt.android.scopes.*
import dev.muyiwa.common.data.*
import dev.muyiwa.common.data.repository.*
import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.utils.*
import kotlinx.coroutines.*

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
	@Binds
	@ActivityRetainedScoped
	abstract fun bindMovieRepository(repository: NoxxyAppRepository): AppRepository

//	@Binds
////	@ActivityRetainedScoped
//	abstract fun bindBookmarkRepository(repository: NoxxyBookmarkRepository): BookmarkRepository

	@Binds
	abstract fun bindDispatchersProvider(dispatcherProvider: CoroutineDispatchersProvider): DispatchersProvider

}