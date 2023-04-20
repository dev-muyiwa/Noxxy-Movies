package dev.muyiwa.common.data.api.model.videos


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiVideosResponse(
    @field:Json(name = "id") val id: Int? = null,
    @field:Json(name = "results") val apiVideos: List<ApiVideo?>? = null
)