package dev.muyiwa.common.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.muyiwa.common.data.preferences.*

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {
	@Binds
	abstract fun bindPreferences(preferences: NoxxyPreferences): Preferences
}