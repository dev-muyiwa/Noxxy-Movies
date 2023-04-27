package dev.muyiwa.noxxy.details.domain.usecases

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.repositories.*
import javax.inject.*

class GetVideos @Inject constructor(private val repository: AppRepository) {
	suspend operator fun invoke(id: Int): List<Video> =
		repository.getVideosBy(id)
}