package dev.muyiwa.home.domain.usecases

import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import javax.inject.Inject

class GetCategorisedMovies @Inject constructor(
	private val repository: MovieRepository
) {
	operator fun invoke(category: Category) = repository.getCategorisedMovies(category)
}