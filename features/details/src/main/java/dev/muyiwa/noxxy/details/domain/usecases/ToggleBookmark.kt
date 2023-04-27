package dev.muyiwa.noxxy.details.domain.usecases

import dev.muyiwa.common.domain.repositories.*
import javax.inject.*

class ToggleBookmark @Inject constructor(
	private val repository: AppRepository,
) {
//	suspend operator fun invoke(id: Int): Boolean =
//		repository.toggleBookmarkedMovie(id)

}