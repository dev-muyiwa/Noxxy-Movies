package dev.muyiwa.common.utils.mappers

import dev.muyiwa.common.data.api.model.categorised_movie.*
import dev.muyiwa.common.data.cache.entities.category.*
import dev.muyiwa.common.data.cache.entities.genre.*
import dev.muyiwa.common.data.cache.entities.references.*
import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.utils.*

//fun ApiCategorisedMovieResponse.toDomainModel(category: Category): CategorisedMovie {
//	return CategorisedMovie(
//		category = category,
//
//	)
//}

//fun CategorisedMovie.toCacheModel(category: Category): CachedMovieWithGenresAndCategories {
//	return CachedMovieWithGenresAndCategories(
//		movie = ,
//		genres = genreIds.map { CachedGenre(genreId = , name = it) },
//		categories = listOf(CachedCategory(category.name))
//	)
//}