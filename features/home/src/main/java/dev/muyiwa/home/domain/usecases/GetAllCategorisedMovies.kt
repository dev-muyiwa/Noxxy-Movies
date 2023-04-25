package dev.muyiwa.home.domain.usecases

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import kotlinx.coroutines.flow.*
import java.util.Locale
import javax.inject.*

class GetAllCategorisedMovies @Inject constructor(private val repository: MovieRepository) {
	operator fun invoke() = repository.getAllCategorisedMovies(Locale.getDefault().toLanguageTag())
}

class GetAllCategoriesOfMovies @Inject constructor(private val repository: AppRepository) {
	operator fun invoke(): List<Flow<List<MovieWithGenres>>> {
		val popularFlow = repository.getCategorisedMovies(Category.POPULAR)
		val nowPlayingFlow = repository.getCategorisedMovies(Category.NOW_PLAYING)
		val topRatedFlow = repository.getCategorisedMovies(Category.TOP_RATED)
		val upcomingFlow = repository.getCategorisedMovies(Category.UPCOMING)
	}
}