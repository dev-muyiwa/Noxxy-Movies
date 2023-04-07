package dev.muyiwa.home.presentation.all

import android.hardware.camera2.CaptureFailure
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.presentation.model.*
import dev.muyiwa.home.domain.usecases.*

data class HomeMoviesState(
	val isLoading: Boolean = true,
	val allMovies: List<List<UiCategorisedMovie>> = emptyList(),
	val failure: Event<Throwable>? = null
)