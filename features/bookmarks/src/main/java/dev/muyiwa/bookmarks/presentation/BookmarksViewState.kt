package dev.muyiwa.bookmarks.presentation

import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.presentation.model.*

data class BookmarksViewState(
	val bookmarkedMovies: List<UiCategorisedMovieComplete> = emptyList(),
	val failure: Event<Throwable>? = null
)