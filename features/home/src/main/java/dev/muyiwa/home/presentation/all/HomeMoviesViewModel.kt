package dev.muyiwa.home.presentation.all

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.home.domain.usecases.*
import dev.muyiwa.logging.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class HomeMoviesViewModel @Inject constructor(
	private val getAllCategoriesOfMovies: GetAllCategoriesOfMovies
) : ViewModel() {
	private val _state = MutableStateFlow(HomeMoviesState())
	val state = _state.asStateFlow()

	private val errorMessage = "Failed to load movies."
	private val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
		onFailed(it)
	}

	init {
		subscribeToAllMoviesUpdate()
	}

//	fun onEvent(event: AllCategorisedMoviesEvent){
//		when (event){
//			AllCategorisedMoviesEvent.SeeMoreMovies() -> {
//
//			}
//		}
//	}

	private fun subscribeToAllMoviesUpdate() {
		viewModelScope.launch(exceptionHandler) {
			getAllCategoriesOfMovies()
				.filter { it.isNotEmpty() }
				.catch { onFailed(it) }
				.flowOn(Dispatchers.Main)
				.onCompletion { _state.update { it.updateToHasCompletedLoading() } }
				.collect { onNewMoviePair(it) }
		}
	}

	private fun onNewMoviePair(moviePairs: List<Pair<Category, List<MovieWithGenres>>>) {
		moviePairs.onEach { item ->
			when (item.first) {
				Category.POPULAR -> _state.update { it.updateToHasPopularMovies(item.second) }
				Category.UPCOMING -> _state.update { it.updateToHasUpcomingMovies(item.second) }
				Category.TOP_RATED -> _state.update { it.updateToHasTopRatedMovies(item.second) }
				Category.NOW_PLAYING -> _state.update { it.updateToHasNowPlayingMovies(item.second) }
			}
//			Logger.i("Got ${item.first.title} movies => ${item.second}")
		}
	}

	private fun onFailed(failure: Throwable) {
//		when (failure) {
//			is NetworkUnavailableException -> {
		_state.update { oldState ->
			oldState.updateToHasFailures(failure)
		}
//			}
//		}
	}
}