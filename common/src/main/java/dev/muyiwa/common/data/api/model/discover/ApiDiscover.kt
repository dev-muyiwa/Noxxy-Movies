package dev.muyiwa.common.data.api.model.discover


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiDiscover(
    @field:Json(name = "page") val page: Int?,
    @field:Json(name = "results") val results: List<Result?>?,
    @field:Json(name = "total_results") val totalResults: Int?,
    @field:Json(name = "total_pages") val totalPages: Int?
)