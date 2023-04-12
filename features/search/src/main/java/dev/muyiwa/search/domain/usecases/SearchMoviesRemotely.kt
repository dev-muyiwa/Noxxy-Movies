package dev.muyiwa.search.domain.usecases

import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.utils.*
import kotlinx.coroutines.*
import javax.inject.*

class SearchMoviesRemotely @Inject constructor(
	private val repository: MovieRepository,
	private val dispatchersProvider: DispatchersProvider
) {
	suspend operator fun invoke(pageToLoad: Int, query: String): Pagination {
		return withContext(dispatchersProvider.io()) {
			val (movies, pagination) = repository.searchMoviesRemotely(query, pageToLoad)
			if (movies.isEmpty()) {
				throw NoMoreMoviesException("Unable to get movies like $query")
			}
			repository.storeCategorisedMovies(movies.map { it.toCategorisedMovie() })
			return@withContext pagination
		}
	}
}