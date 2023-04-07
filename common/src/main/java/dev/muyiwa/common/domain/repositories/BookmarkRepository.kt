package dev.muyiwa.common.domain.repositories

interface BookmarkRepository {
	fun getBookmarkedMovieId(movieId: Int): Int
	fun addMovieIdToBookmarks(movieId: Int)
	fun removeMovieIdFromBookmarks(movieId: Int)
}