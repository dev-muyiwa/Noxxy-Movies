package dev.muyiwa.home.presentation.individual

import android.hardware.camera2.CaptureFailure
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.presentation.model.*
import dev.muyiwa.home.domain.usecases.*

data class CategorisedMoviesState(
	val isLoading: Boolean = true,
	val categorisedMovies: List<MovieWithGenres> = emptyList(),
	val noMoreMovies: Boolean = false,
	val failure: Event<Throwable>? = null
)