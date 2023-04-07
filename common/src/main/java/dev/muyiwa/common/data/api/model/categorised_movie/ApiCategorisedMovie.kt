package dev.muyiwa.common.data.api.model.categorised_movie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.muyiwa.common.data.api.utils.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.utils.*

@JsonClass(generateAdapter = true)
data class ApiCategorisedMovie(
    @field:Json(name = "adult") val isAdult: Boolean? ,
    @field:Json(name = "backdrop_path") val backdropPath: String? ,
    @field:Json(name = "genre_ids") val genreIds: List<Int?>? ,
    @field:Json(name = "id") val movieId: Int? ,
    @field:Json(name = "original_language") val originalLanguage: String? ,
    @field:Json(name = "original_title") val originalTitle: String? ,
    @field:Json(name = "overview") val overview: String? ,
    @field:Json(name = "popularity") val popularity: Double? ,
    @field:Json(name = "poster_path") val posterPath: String? ,
    @field:Json(name = "release_date") val releaseDate: String? ,
    @field:Json(name = "title") val title: String? ,
    @field:Json(name = "video") val video: Boolean? ,
    @field:Json(name = "vote_average") val voteAverage: Double? ,
    @field:Json(name = "vote_count") val voteCount: Int?
)

