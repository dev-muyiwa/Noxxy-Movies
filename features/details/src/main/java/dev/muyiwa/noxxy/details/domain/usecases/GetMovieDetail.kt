package dev.muyiwa.noxxy.details.domain.usecases

import dev.muyiwa.common.domain.repositories.*
import dev.muyiwa.common.domain.utils.*
import java.util.*
import javax.inject.Inject

class GetMovieDetail @Inject constructor(private val repository: AppRepository) {
	suspend operator fun invoke(movieId: Int) =
		repository.getMovieDetail(movieId)
}