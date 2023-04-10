package dev.muyiwa.common.data.api.model.search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiSearchResponse(
    @field:Json(name = "page") val page: Int?,
    @field:Json(name = "results") val results: List<Result?>?,
    @field:Json(name = "total_pages") val totalPages: Int?,
    @field:Json(name = "total_results") val totalResults: Int?
)