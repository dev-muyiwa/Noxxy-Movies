package dev.muyiwa.home.domain.usecases

import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.utils.*
import kotlinx.coroutines.*
import javax.inject.*

class RequestMoreCategorisedMovies @Inject constructor(
	private val repository: AppRepository,
	private val dispatchersProvider: DispatchersProvider
) {
	suspend operator fun invoke(
		pageToLoad: Int = 1,
		category: Category
	): dev.muyiwa.common.domain.model.Pagination {
		return withContext(dispatchersProvider.io()) {
			val (moviesWithGenres, pagination)
					= repository.requestForMoreCategorisedMovies(pageToLoad, category)
			if (moviesWithGenres.isEmpty()) {
				throw NoMoreMoviesException("No more moviesWithGenres.")
			}
			repository.storeCategorisedMovies(category, moviesWithGenres)
			return@withContext pagination
		}
	}
}