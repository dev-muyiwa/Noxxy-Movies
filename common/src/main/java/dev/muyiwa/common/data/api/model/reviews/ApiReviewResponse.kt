package dev.muyiwa.common.data.api.model.reviews


import com.squareup.moshi.*

@JsonClass(generateAdapter = true)
data class ApiReviewResponse(
//    @Json(name = "id") val id: Int? = null,
    @field:Json(name = "page") val page: Int? = null,
    @field:Json(name = "results") val apiReviews: List<ApiReview?>? = null,
    @field:Json(name = "total_pages") val totalPages: Int? = null,
    @field:Json(name = "total_results") val totalResults: Int? = null
)