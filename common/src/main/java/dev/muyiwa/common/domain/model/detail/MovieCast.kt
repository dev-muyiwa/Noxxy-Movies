package dev.muyiwa.common.domain.model.detail

data class MovieCast(
	val movieId: Int,
	val casts: List<Cast>
)

data class Cast(
	val originalName: String,
	val profilePath: String,
	val character: String
)
