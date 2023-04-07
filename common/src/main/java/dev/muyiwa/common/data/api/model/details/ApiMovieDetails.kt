package dev.muyiwa.common.data.api.model.details

import com.squareup.moshi.*

@JsonClass(generateAdapter = true)
data class ApiMovieDetails(
	@field:Json(name = "budget") val budget: Long?,
	@field:Json(name = "homepage") val homepage: String?,
	@field:Json(name = "id") val movieId: Long?,
	@field:Json(name = "revenue") val revenue: Long?,
	@field:Json(name = "runtime") val runtime: Int?,
	@field:Json(name = "status") val status: String?,
	@field:Json(name = "tagline") val tagline: String?,
)