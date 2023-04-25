package dev.muyiwa.common.data.cache.entities.mapper

import dev.muyiwa.common.data.cache.entities.test_run.cast.*
import dev.muyiwa.common.data.cache.entities.test_run.detail.*
import dev.muyiwa.common.data.cache.entities.test_run.genre.*
import dev.muyiwa.common.data.cache.entities.test_run.movie.*
import dev.muyiwa.common.data.cache.entities.test_run.relation_class.*
import dev.muyiwa.common.data.cache.entities.test_run.review.*
import dev.muyiwa.common.domain.model.*

fun CachedCast.toDomainModel(): Cast {
	return Cast(name = name, character = character, profilePath = profilePath)
}

fun CachedGenre.toDomainModel(): Genre {
	return Genre(id = genreId, name = name)
}

fun CacheReview.toDomainModel(): Review {
	return Review(author = author, content = content)
}

fun CachedDetail.toDomainModel(): Detail {
	return Detail(
		budget = budget,
		homepage = homepage,
		revenue = revenue,
		runtime = runtime,
		status = status,
		tagline = tagline,
	)
}

fun CachedMovie.toDomainModel(): Movie {
	return Movie(
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

fun CachedMovieWithDetailsAndGenres.toDomainModel(): MovieWithFullDetail {
	return MovieWithFullDetail(
		movie = movie.toDomainModel(),
		detail = detail !!.toDomainModel(),
		genres = genres.map { it.toDomainModel() },
		casts = casts.map { it.toDomainModel() },
		reviews = reviews.map { it.toDomainModel() },
		isBookmarked = bookmark != null
	)
}

fun CachedMovieWithGenres.toDomainModel(): MovieWithGenres {
	return MovieWithGenres(
		movie = movie.toDomainModel(),
		genres = genres.map { it.toDomainModel() }
	)
}