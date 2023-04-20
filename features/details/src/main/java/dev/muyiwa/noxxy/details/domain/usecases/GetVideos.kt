package dev.muyiwa.noxxy.details.domain.usecases

import dev.muyiwa.common.domain.model.detail.*
import dev.muyiwa.common.domain.repositories.*
import javax.inject.Inject

class GetVideos @Inject constructor(private val repository: MovieRepository) {
	suspend operator fun invoke(id: Int): List<Video> =
		repository.getVideosBy(id)
}