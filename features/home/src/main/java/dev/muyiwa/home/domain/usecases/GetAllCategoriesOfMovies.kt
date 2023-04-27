package dev.muyiwa.home.domain.usecases

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class GetAllCategoriesOfMovies @Inject constructor(private val repository: AppRepository) {
	operator fun invoke(): Flow<List<Pair<Category, List<MovieWithGenres>>>> =
		repository.getAllCategoriesOfMovies()
			.distinctUntilChanged()
}