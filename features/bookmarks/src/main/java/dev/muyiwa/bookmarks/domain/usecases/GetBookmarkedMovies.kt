package dev.muyiwa.bookmarks.domain.usecases

import dev.muyiwa.common.domain.repositories.*
import javax.inject.*

class GetBookmarkedMovies @Inject constructor(private val repository: MovieRepository) {
	operator fun invoke() = repository.getAllBookmarkedMovies()
}