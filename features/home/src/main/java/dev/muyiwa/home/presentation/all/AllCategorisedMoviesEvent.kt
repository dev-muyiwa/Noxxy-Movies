package dev.muyiwa.home.presentation.all

import dev.muyiwa.common.domain.utils.*

sealed class AllCategorisedMoviesEvent {
	object RequestMoreMovies: AllCategorisedMoviesEvent()
	data class SeeMoreMovies(val category: Category): AllCategorisedMoviesEvent()
}