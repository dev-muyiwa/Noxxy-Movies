package dev.muyiwa.common.data.api.model.categorised_movie


import com.squareup.moshi.*

@JsonClass(generateAdapter = true)
data class ApiCategorisedMovieResponse(
	@field:Json(name = "page") val currentPage: Int?,
	@field:Json(name = "results") val movies: List<ApiMovie?>?,
	@field:Json(name = "total_pages") val totalPages: Int?,
	@field:Json(name = "total_results") val totalResults: Int?
)