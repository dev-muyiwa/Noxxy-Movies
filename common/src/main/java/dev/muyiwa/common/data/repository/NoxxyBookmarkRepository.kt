package dev.muyiwa.common.data.repository

import dev.muyiwa.common.data.cache.daos.*
import dev.muyiwa.common.data.cache.entities.*
import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.utils.*
import kotlinx.coroutines.*
import javax.inject.Inject

class NoxxyBookmarkRepository @Inject constructor(
	private val dao: BookmarksDao,
	private val dispatchersProvider: DispatchersProvider
): BookmarkRepository {
	override fun getBookmarkedMovieId(movieId: Int): Int {
		TODO("Not yet implemented")
	}

	override fun addMovieIdToBookmarks(movieId: Int) {
		CoroutineScope(dispatchersProvider.io()).launch {
			dao.addMovieIdToBookmarks(CachedBookmarkedMovie(movieId))
		}
	}

	override fun removeMovieIdFromBookmarks(movieId: Int) {
		CoroutineScope(dispatchersProvider.io()).launch {
			dao.removeMovieIdFromBookmarks(movieId)
		}
	}
}