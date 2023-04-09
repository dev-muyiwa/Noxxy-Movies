package dev.muyiwa.common.data.api.model.casts

import com.squareup.moshi.*

@JsonClass(generateAdapter = true)
data class ApiCast(
	@field:Json(name = "original_name") val originalName: String?,
	@field:Json(name = "profile_path") val profilePath: String?,
	@field:Json(name = "character") val character: String?
)
