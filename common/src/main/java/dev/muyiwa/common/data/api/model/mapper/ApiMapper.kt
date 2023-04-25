package dev.muyiwa.common.data.api.model.mapper

import dev.muyiwa.common.data.api.model.casts.*
import dev.muyiwa.common.data.api.model.categorised_movie.*
import dev.muyiwa.common.data.api.model.details.*
import dev.muyiwa.common.data.api.model.reviews.*
import dev.muyiwa.common.data.api.model.search.*
import dev.muyiwa.common.data.api.utils.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.utils.*

fun ApiCategorisedMovieResponse.toPaginatedModel(): Pagination {
	return Pagination(
		currentPage = currentPage ?: 0,
		totalPage = totalPages ?: Pagination.UNKNOWN_TOTAL
	)
}

fun ApiCategorisedMovieResponse.toPagination(): MoviePagination {
	return MoviePagination(
		movies = movies.orEmpty().map { MovieWithGenres(it !!.toDomainModel(), it.toGenre()) },
		pagination = toPaginatedModel()
	)
}

fun ApiMovie.toDomainModel(): Movie {
	return Movie(
		isAdult = isAdult ?: false,
		backdropPath = backdropPath.formatUrl(IMAGE_BASE_ENDPOINT + BACKDROP_SIZE),
		movieId = movieId ?: throw MappingException("Movie ID cannot be be null."),
		originalLanguage = originalLanguage.asUnknown(),
		originalTitle = originalTitle.asUnknown(),
		overview = overview.asUnknown(),
		popularity = popularity ?: 0.0,
		posterPath = posterPath.formatUrl(IMAGE_BASE_ENDPOINT + POSTER_SIZE),
		releaseDate = releaseDate.asUnknown(),
		title = title.asUnknown(),
		voteAverage = voteAverage ?: 0.0,
		voteCount = voteCount ?: 0,
	)
}

fun ApiMovie.toGenre(): List<Genre> {
	return genreIds.orEmpty().map { Genre(it ?: 0, GenreType.fromApi(it)) }
}

fun ApiMovieDetails.toDomainModel(): Detail {
	return Detail(
		budget = budget ?: 0,
		homepage = homepage.asUnknown(),
		revenue = revenue ?: 0,
		runtime = runtime ?: 0,
		status = status.asUnknown(),
		tagline = tagline.asUnknown(),
	)
}

fun ApiReview.toDomainModel(): Review {
	return Review(author = author.asUnknown(), content = content.asUnknown())
}

fun ApiReviewResponse.toDomainModel(): List<Review> {
	return apiReviews.orEmpty().map { it !!.toDomainModel() }
}

fun ApiSearchResponse.toPagination(): MoviePagination {
	return MoviePagination(
		movies = results.orEmpty().map { MovieWithGenres(it !!.toDomainModel(), it.toGenre()) },
		pagination = Pagination(
			currentPage = currentPage ?: 0,
			totalPage = totalPages ?: Pagination.UNKNOWN_TOTAL
		)
	)
}

fun ApiCast.toDomainModel(): Cast {
	return Cast(
		name = originalName.asUnknown(),
		character = character.asUnknown(),
		profilePath.formatUrl(IMAGE_BASE_ENDPOINT + PROFILE_SIZE)
	)
}

fun ApiCasts.toDomainModel(): List<Cast> {
	return cast.map { it.toDomainModel() }
}

