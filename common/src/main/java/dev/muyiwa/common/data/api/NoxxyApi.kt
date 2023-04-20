package dev.muyiwa.common.data.api

import dev.muyiwa.common.data.api.model.*
import dev.muyiwa.common.data.api.model.casts.*
import dev.muyiwa.common.data.api.model.categorised_movie.*
import dev.muyiwa.common.data.api.model.details.*
import dev.muyiwa.common.data.api.model.genre.*
import dev.muyiwa.common.data.api.model.reviews.*
import dev.muyiwa.common.data.api.model.search.*
import dev.muyiwa.common.data.api.model.videos.*
import dev.muyiwa.common.data.api.utils.*
import retrofit2.http.*

interface NoxxyApi {
	@GET(POPULAR_MOVIE_ENDPOINT)
	suspend fun fetchPopularMovies(
		@Query(LANG) language: String,
		@Query(PAGE) pageToLoad: Int,
	): ApiCategorisedMovieResponse

	@GET(NOW_PLAYING_ENDPOINT)
	suspend fun fetchNowPlayingMovies(
		@Query(LANG) language: String,
		@Query(PAGE) pageToLoad: Int,
	): ApiCategorisedMovieResponse

	@GET(TOP_RATED_ENDPOINT)
	suspend fun fetchTopRatedMovies(
		@Query(LANG) language: String,
		@Query(PAGE) pageToLoad: Int,
	): ApiCategorisedMovieResponse

	@GET(UPCOMING_ENDPOINT)
	suspend fun fetchUpcomingMovies(
		@Query(LANG) language: String,
		@Query(PAGE) pageToLoad: Int,
	): ApiCategorisedMovieResponse

	@GET(MOVIE_INFO)
	suspend fun fetchMovieDetailsById(
		@Path(MOVIE_ID) id: Long,
	): ApiMovieDetails


	@GET(CREDITS_ENDPOINT)
	suspend fun fetchCastsByMovieId(
		@Path(MOVIE_ID) id: Long,
	): ApiCasts

	@GET(GENRE_ENDPOINT)
	suspend fun fetchGenres(
		@Query(LANG) language: String,
	): ApiGenres

	@GET(SEARCH_ENDPOINT)
	suspend fun searchForMovies(
		@Query(LANG) language: String,
		@Query(QUERY) searchQuery: String,
		@Query(PAGE) pageToLoad: Int,
	): ApiSearchResponse

	@GET(REVIEW_ENDPOINT)
	suspend fun getReviews(
		@Path(MOVIE_ID) id: Long,
		@Query(LANG) language: String,
		@Query(PAGE) pageToLoad: Int
	): ApiReviewResponse

	@GET(VIDEO_INFO)
	suspend fun getVideos(
		@Path(MOVIE_ID) id: Long,
		@Query(LANG) language: String
	): ApiVideosResponse
}