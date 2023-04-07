package dev.muyiwa.home.domain.usecases

import dev.muyiwa.common.domain.repositories.*
import java.util.Locale
import javax.inject.*

class GetAllCategorisedMovies @Inject constructor(private val repository: MovieRepository) {
	operator fun invoke() = repository.getAllCategorisedMovies(Locale.getDefault().toLanguageTag())
}