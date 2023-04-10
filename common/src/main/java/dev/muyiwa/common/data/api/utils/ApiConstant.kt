package dev.muyiwa.common.data.api.utils

/** API Endpoints.*/
const val BASE_ENDPOINT = "https://api.themoviedb.org/3/"
const val MOVIE_ID = "movieId"
const val POPULAR_MOVIE_ENDPOINT = "movie/popular"
const val NOW_PLAYING_ENDPOINT = "movie/now_playing"
const val TOP_RATED_ENDPOINT = "movie/top_rated"
const val UPCOMING_ENDPOINT = "movie/upcoming"
const val CREDITS_ENDPOINT = "movie/{$MOVIE_ID}/credits"
const val GENRE_ENDPOINT = "genre/movie/list"
const val SEARCH_ENDPOINT = "search/movie"
const val IMAGE_BASE_ENDPOINT = "https://image.tmdb.org/t/p/"
const val MOVIE_INFO = "movie/{$MOVIE_ID}"
const val VIDEO_INFO = "$MOVIE_INFO/videos"
const val KEY_NAME = "api_key"

/** Image Sizes. */
const val LOGO_SIZE = "w154"
const val PROFILE_SIZE = "w185"
const val POSTER_SIZE = "w185"
const val BACKDROP_SIZE = "w780"

/**API Parameters.*/
const val PAGE = "page"
const val LANG = "language"
const val QUERY = "query"
