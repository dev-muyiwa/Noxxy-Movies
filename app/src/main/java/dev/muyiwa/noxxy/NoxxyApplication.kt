package dev.muyiwa.noxxy

import android.app.*
import dagger.hilt.android.HiltAndroidApp
import dev.muyiwa.logging.*

@HiltAndroidApp
class NoxxyApplication: Application() {

	override fun onCreate() {
		super.onCreate()
		Logger.init()
	}
}