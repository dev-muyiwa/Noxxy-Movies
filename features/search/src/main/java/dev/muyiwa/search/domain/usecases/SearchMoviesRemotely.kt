package dev.muyiwa.search.domain.usecases

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.utils.*
import kotlinx.coroutines.*
import javax.inject.*

class SearchMoviesRemotely @Inject constructor(
	private val repository: AppRepository,
	private val dispatchersProvider: DispatchersProvider
) {
	suspend operator fun invoke(
		pageToLoad: Int,
		query: String
	): Pagination {
		return withContext(dispatchersProvider.io()) {
			val (moviesWithGenres, pagination) = repository
				.searchMoviesRemotely(query, pageToLoad)
			if (moviesWithGenres.isEmpty()) {
				throw NoMoreMoviesException("Unable to get moviesWithGenres like $query")
			}
			repository.storeCategorisedMovies(movies = moviesWithGenres)
			return@withContext pagination
		}
	}
}