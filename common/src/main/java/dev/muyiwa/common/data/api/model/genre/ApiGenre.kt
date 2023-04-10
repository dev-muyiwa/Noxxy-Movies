package dev.muyiwa.common.data.api.model.genre


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiGenre(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "name") val name: String?
)