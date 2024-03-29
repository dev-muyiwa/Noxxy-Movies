package dev.muyiwa.common.utils

import dev.muyiwa.common.data.api.model.casts.*
import dev.muyiwa.common.data.api.model.categorised_movie.*
import dev.muyiwa.common.data.api.model.details.*
import dev.muyiwa.common.data.api.model.videos.*
import dev.muyiwa.common.data.api.utils.*
import dev.muyiwa.common.data.cache.entities.video.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.model.category.Movie
import dev.muyiwa.common.domain.model.detail.*
import dev.muyiwa.common.domain.model.detail.Cast
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.model.*

//fun ApiCategorisedMovieResponse.toDomainModel(category: Category): CategorisedPaginatedMovies {
//	return CategorisedPaginatedMovies(
//		movies = movies.orEmpty().map { it !!.toDomainModel(category) },
//		pagination = Pagination(
//			currentPage = currentPage ?: 0,
//			totalPages = totalPages ?: Pagination.UNKNOWN_TOTAL
//		)
//	)
//}

//fun ApiSearchResponse.toDomainModel(): PaginatedMovies {
//	return PaginatedMovies(
//		movies = results.orEmpty().map { it!!.toMovie() },
//		pagination = Pagination(
//			currentPage = currentPage ?: 0,
//			totalPages = totalPages ?: Pagination.UNKNOWN_TOTAL
//		)
//	)
//}

//fun ApiMovie.toDomainModel(category: Category): CategorisedMovie {
//	return CategorisedMovie(
//		movie = this.toMovie(),
//		category = category
//	)
//}

fun ApiMovie.toMovie(): Movie {
	return Movie(
		isBookmarked = false,
		isAdult = isAdult ?: false,
		backdropPath = IMAGE_BASE_ENDPOINT + BACKDROP_SIZE + backdropPath.orEmpty(),
		genreIds = genreIds.orEmpty().map { it?.toGenreName().orEmpty() },
		movieId = movieId ?: throw MappingException("Movie ID cannot be be null."),
		originalLanguage = originalLanguage.orEmpty(),
		originalTitle = originalTitle.orEmpty(),
		overview = overview.orEmpty(),
		popularity = popularity ?: 0.0,
		posterPath = IMAGE_BASE_ENDPOINT + POSTER_SIZE + posterPath.orEmpty(),
		releaseDate = releaseDate.orEmpty(),
		title = title.orEmpty(),
		video = video ?: false,
		voteAverage = voteAverage ?: 0.0,
		voteCount = voteCount ?: 0,
	)
}

//fun CachedCategorisedMovie.toDomainModel(): CategorisedMovie {
//	return CategorisedMovie(
//		movie = this.toMovie(),
//		category = category
//	)
//}

fun Movie.toCategorisedMovie(): CategorisedMovie {
	return CategorisedMovie(
		movie = this,
		category = null
	)
}

//fun CachedCategorisedMovie.toMovie(): Movie {
//	return Movie(
//		isBookmarked = isBookmarked,
//		isAdult = isAdult,
//		backdropPath = backdropPath,
//		genreIds = genreIds,
//		movieId = movieId,
//		originalLanguage = originalLanguage,
//		originalTitle = originalTitle,
//		overview = overview,
//		popularity = popularity,
//		posterPath = posterPath,
//		releaseDate = releaseDate,
//		title = title,
//		video = video,
//		voteAverage = voteAverage,
//		voteCount = voteCount,
//	)
//}

fun ApiMovieDetails.toDomainModel(movie: CategorisedMovie): MovieDetail {
	return MovieDetail(
		movie = movie,
		budget = budget ?: 0,
		homepage = homepage.orEmpty(),
		revenue = revenue ?: 0,
		runtime = runtime ?: 0,
		status = status.orEmpty(),
		tagline = tagline.orEmpty(),
		casts = emptyList(),
		reviews = emptyList()
	)
}

fun ApiVideo.toDomainModel(): Video {
	return Video(
		name = name.orEmpty(),
		videoKey = key.orEmpty(),
		type = type.orEmpty(),
		publishedAt = publishedAt.orEmpty(),
		videoId = id.orEmpty()
	)
}

fun Video.toCachedModel(movieId: Int): CachedVideo {
	return CachedVideo(
		movieId = movieId,
		name = name,
		videoKey = videoKey,
		type = type,
		publishedAt = publishedAt,
		videoId = videoId
	)
}

fun CachedVideo.toDomainModel(): Video {
	return Video(
		name = name,
		videoKey = videoKey,
		type = type,
		publishedAt = publishedAt,
		videoId = videoId
	)
}

fun ApiCast.toDomainModel(): Cast {
	return Cast(
		originalName = originalName.asUnknown(),
		profilePath = IMAGE_BASE_ENDPOINT + PROFILE_SIZE + profilePath.asUnknown(),
		character = character.asUnknown()
	)
}
//
//fun Cast.toCachedModel(movieId: Int): CachedCast {
//	return CachedCast(
//		movieId = movieId,
//		originalName = originalName,
//		profilePath = profilePath,
//		character = character
//	)
//}

//fun CachedCast.toDomainModel(): Cast {
//	return Cast(originalName, profilePath, character)
//}
//
//fun Review.toCachedModel(movieId: Int): CachedReview {
//	return CachedReview(movieId = movieId, author = author, content = comment)
//}
//
//fun CachedReview.toDomainModel(): Review {
//	return Review(author = author, comment = content)
//}

//fun ApiReview.toDomainModel(): Review {
//	return Review(author = author.orEmpty(), comment = content.orEmpty())
//}

//fun CachedMovieDetails.toDomainModel(
//	movie: CachedCategorisedMovie,
//	casts: List<CachedCast>,
//	reviews: List<CachedReview>
//): MovieDetail {
//	return MovieDetail(
//		movie = movie.toDomainModel(),
//		budget = budget,
//		homepage = homepage,
//		revenue = revenue,
//		runtime = runtime,
//		status = status,
//		tagline = tagline,
//		casts = casts.map { it.toDomainModel() },
//		reviews = reviews.map { it.toDomainModel() }
//	)
//}
//
//// Cache Mapping
//fun CategorisedMovie.toCacheModel(): CachedCategorisedMovie {
//	return CachedCategorisedMovie(
//		isBookmarked = movie.isBookmarked,
//		isAdult = movie.isAdult,
//		backdropPath = movie.backdropPath,
//		genreIds = movie.genreIds,
//		movieId = movie.movieId,
//		originalLanguage = movie.originalLanguage,
//		originalTitle = movie.originalTitle,
//		overview = movie.overview,
//		popularity = movie.popularity,
//		posterPath = movie.posterPath,
//		releaseDate = movie.releaseDate,
//		title = movie.title,
//		video = movie.video,
//		voteAverage = movie.voteAverage,
//		voteCount = movie.voteCount,
//		category = category
//	)
//}
//
//fun MovieDetail.toCacheModel(): CachedMovieDetails {
//	return CachedMovieDetails(
//		movieId = movie.movie.movieId,
//		budget = budget,
//		homepage = homepage,
//		revenue = revenue,
//		runtime = runtime,
//		status = status,
//		tagline = tagline
//	)
//}


// UI Mapping
fun CategorisedMovie.toUiModel(): UiCategorisedMovie {
	return UiCategorisedMovie(
		isBookmarked = movie.isBookmarked,
		movieId = movie.movieId,
		posterPath = movie.posterPath,
		title = movie.title,
		voteAverage = movie.voteAverage,
		category = category
	)
}

fun CategorisedMovie.toFullUiModel(): UiCategorisedMovieComplete {
	return UiCategorisedMovieComplete(
		basicCategorisedMovie = this.toUiModel(),
		overview = movie.overview,
		genreIds = movie.genreIds,
		backdropPath = movie.backdropPath,
		originalLanguage = movie.originalLanguage,
		originalTitle = movie.originalTitle,
		popularity = movie.popularity,
		releaseDate = movie.releaseDate,
		voteCount = movie.voteCount
	)
}

fun MovieDetail.toUiModel(): UiMovieDetails {
	return UiMovieDetails(
		movie = movie.toFullUiModel(),
		budget = budget,
		homepage = homepage,
		revenue = revenue,
		runtime = "$runtime mins",
		status = status,
		tagline = tagline,
		casts = casts,
		reviews = reviews
	)
}
