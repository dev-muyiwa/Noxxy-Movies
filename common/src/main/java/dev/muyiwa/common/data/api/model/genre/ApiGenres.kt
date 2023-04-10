package dev.muyiwa.common.data.api.model.genre


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiGenres(
    @field:Json(name = "genres") val genres: List<ApiGenre?>?
)