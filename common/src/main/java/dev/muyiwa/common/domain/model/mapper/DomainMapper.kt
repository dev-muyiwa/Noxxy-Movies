package dev.muyiwa.common.domain.model.mapper

import dev.muyiwa.common.data.cache.entities.test_run.bookmark.*
import dev.muyiwa.common.data.cache.entities.test_run.cast.*
import dev.muyiwa.common.data.cache.entities.test_run.category.*
import dev.muyiwa.common.data.cache.entities.test_run.detail.*
import dev.muyiwa.common.data.cache.entities.test_run.genre.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*
import dev.muyiwa.common.data.cache.entities.test_run.relation_class.*
import dev.muyiwa.common.data.cache.entities.test_run.review.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.utils.*

fun Movie.toCacheModel(): CachedMovie {
	return CachedMovie(
		isAdult = isAdult,
		backdropPath = backdropPath,
		movieId = movieId,
		originalLanguage = originalLanguage,
		originalTitle = originalTitle,
		overview = overview,
		popularity = popularity,
		posterPath = posterPath,
		releaseDate = releaseDate,
		title = title,
		voteAverage = voteAverage,
		voteCount = voteCount,
	)
}

fun Detail.toCacheModel(movieId: Int): CachedDetail {
	return CachedDetail(
		movieId = movieId,
		budget = budget,
		homepage = homepage,
		revenue = revenue,
		runtime = runtime,
		status = status,
		tagline = tagline
	)
}

fun Cast.toCacheModel(movieId: Int): CachedCast {
	return CachedCast(
		movieId = movieId,
		name = name,
		profilePath = profilePath,
		character = character
	)
}

fun Category.toCacheModel(): CachedCategory {
	return CachedCategory(categoryName = name)
}

fun Genre.toCacheModel(): CachedGenre {
	return CachedGenre(genreId = id)
}

fun Review.toCacheModel(movieId: Int): CacheReview {
	return CacheReview(
		author = author,
		content = content,
		movieId = movieId
	)
}

fun MovieWithGenres.toCacheModel(): CachedMovieWithGenres {
	return CachedMovieWithGenres(
		movie = movie.toCacheModel(),
		genres = genres.map { it.toCacheModel() }
	)
}

fun MovieWithFullDetail.toCacheModel(): CachedMovieWithDetailsAndGenres {
	val movieId = movie.movieId
	return CachedMovieWithDetailsAndGenres(
		movie = movie.toCacheModel(),
		detail = detail.toCacheModel(movieId),
		genres = genres.map { it.toCacheModel() },
		casts = casts.map { it.toCacheModel(movieId) },
		reviews = reviews.map { it.toCacheModel(movieId) },
		bookmark = if (isBookmarked) CachedBookmark(movieId) else null
	)
}
