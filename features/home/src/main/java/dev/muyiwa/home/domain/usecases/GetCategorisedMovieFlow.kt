package dev.muyiwa.home.domain.usecases

import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import java.util.*
import javax.inject.*

class GetCategorisedMovieFlow @Inject constructor(private val repository: MovieRepository) {
	operator fun invoke(
		category: Category,
		pageToLoad: Int,
	) = repository.getCategorisedMoviesAsFlow(category, Locale.getDefault().toLanguageTag(), pageToLoad)
}