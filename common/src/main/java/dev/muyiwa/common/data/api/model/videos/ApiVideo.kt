package dev.muyiwa.common.data.api.model.videos


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiVideo(
    @field:Json(name = "iso_639_1") val iso6391: String? = null,
    @field:Json(name = "iso_3166_1") val iso31661: String? = null,
    @field:Json(name = "name") val name: String? = null,
    @field:Json(name = "key") val key: String? = null,
    @field:Json(name = "site") val site: String? = null,
    @field:Json(name = "size") val size: Int? = null,
    @field:Json(name = "type") val type: String? = null,
    @field:Json(name = "official") val official: Boolean? = null,
    @field:Json(name = "published_at") val publishedAt: String? = null,
    @field:Json(name = "id") val id: String? = null
)