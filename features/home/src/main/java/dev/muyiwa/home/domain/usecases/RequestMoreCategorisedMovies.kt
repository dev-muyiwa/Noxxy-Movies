package dev.muyiwa.home.domain.usecases

import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.utils.*
import kotlinx.coroutines.*
import javax.inject.*

class RequestMoreCategorisedMovies @Inject constructor(
	private val repository: MovieRepository,
	private val dispatchersProvider: DispatchersProvider
) {
	suspend operator fun invoke(
		pageToLoad: Int,
		category: Category
	): Pagination {
		return withContext(dispatchersProvider.io()) {
			val (movies, pagination)
					= repository.requestForMoreCategorisedMovies(pageToLoad, category)
			if (movies.isEmpty()) {
				throw NoMoreMoviesException("No more movies.")
			}
			repository.storeCategorisedMovies(category, movies)
			return@withContext pagination
		}
	}
}