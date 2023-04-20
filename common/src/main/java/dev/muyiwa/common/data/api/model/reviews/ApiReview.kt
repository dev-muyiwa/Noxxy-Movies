package dev.muyiwa.common.data.api.model.reviews


import com.squareup.moshi.*

@JsonClass(generateAdapter = true)
data class ApiReview(
	@field:Json(name = "author") val author: String? = null,
//    @Json(name = "author_details") val authorDetails: AuthorDetails? = null,
	@field:Json(name = "content") val content: String? = null,
//    @Json(name = "created_at") val createdAt: String? = null,
//    @Json(name = "id") val id: String? = null,
//    @Json(name = "updated_at") val updatedAt: String? = null,
//    @Json(name = "url") val url: String? = null
)