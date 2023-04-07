package dev.muyiwa.common.data.preferences

import android.content.*
import dagger.hilt.android.qualifiers.*
import javax.inject.*

@Singleton
class NoxxyPreferences @Inject constructor(
	@ApplicationContext context: Context
) : Preferences {

	companion object {
		const val PREFERENCES_NAME = "NOXXY_PREFERENCES"
		const val IS_GRID_LAYOUT = "is_grid_layout"
		const val LANGUAGE = "language_tag"
	}

	private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

	private inline fun edit(block: SharedPreferences.Editor.() -> Unit) {
		with(preferences.edit()) {
			block()
			commit()
		}
	}

	override var isGridLayout: Boolean
		get() = preferences.getBoolean(IS_GRID_LAYOUT, false)
		set(value) = edit { putBoolean(IS_GRID_LAYOUT, value) }


	override var languageTag: String
		get() = preferences.getString(LANGUAGE, "en-US").orEmpty()
		set(value) = edit { putString(LANGUAGE, value) }

}