package dev.muyiwa.common.data.api.model.reviews


import com.squareup.moshi.*

@JsonClass(generateAdapter = true)
data class AuthorDetails(
	@Json(name = "name") val name: String? = null,
	@Json(name = "username") val username: String? = null,
	@Json(name = "avatar_path") val avatarPath: String? = null,
	@Json(name = "rating") val rating: Int? = null
)