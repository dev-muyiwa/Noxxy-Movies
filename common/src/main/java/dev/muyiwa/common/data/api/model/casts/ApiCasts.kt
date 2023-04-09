package dev.muyiwa.common.data.api.model.casts

import com.squareup.moshi.*

@JsonClass(generateAdapter = true)
data class ApiCasts(
	@field:Json(name = "id") val id: Long,
	@field:Json(name = "cast") val cast: List<ApiCast>,
)
