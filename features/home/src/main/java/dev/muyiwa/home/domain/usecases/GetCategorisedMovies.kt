package dev.muyiwa.home.domain.usecases

import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import javax.inject.Inject

class GetCategorisedMovies @Inject constructor(
	private val repository: AppRepository
) {
	operator fun invoke(category: Category) = repository.getCategorisedMoviesAsFlow(category)
}