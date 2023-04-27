package dev.muyiwa.bookmarks.presentation

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.presentation.model.*

data class BookmarksViewState(
	val bookmarkedMovies: List<MovieWithGenres> = emptyList(),
	val failure: Event<Throwable>? = null
)