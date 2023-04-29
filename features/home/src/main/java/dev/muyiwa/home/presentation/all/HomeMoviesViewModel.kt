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
	private val getAllCategoriesOfMovies: GetAllCategoriesOfMovies,
	private val getCategorisedMovies: GetCategorisedMovies,
	private val requestMoreCategorisedMovies: RequestMoreCategorisedMovies,
) : ViewModel() {
	private val _state = MutableStateFlow(HomeMoviesState())
	val state = _state.asStateFlow()
	private var isLoadingMoreMovies = false

	private val errorMessage = "Failed to load movies."
	private val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
		onFailed(it)
	}

	init {
		subscribeToCategoriesOfMoviesUpdate()
	}

	private fun subscribeToCategoriesOfMoviesUpdate() {
		viewModelScope.launch(exceptionHandler) {
			combine(
				getCategorisedMovies(Category.POPULAR),
				getCategorisedMovies(Category.UPCOMING),
				getCategorisedMovies(Category.TOP_RATED),
				getCategorisedMovies(Category.NOW_PLAYING)
			) { popular, upcoming, topRated, nowPlaying ->
				val popularPair = Category.POPULAR to popular
				val upcomingPair = Category.UPCOMING to upcoming
				val topRatedPair = Category.TOP_RATED to topRated
				val nowPlayingPair = Category.NOW_PLAYING to nowPlaying
				listOf(popularPair, upcomingPair, topRatedPair, nowPlayingPair)
			}.onEach { it.forEach { pair -> if (pair.second.isEmpty()) loadNextPageOfMovies(pair.first) } }
				.onCompletion { Logger.d("End of flow..") }
				.catch { onFailed(it) }
				.collect { onNewMoviePair(it) }
		}
	}

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

	private fun loadNextPageOfMovies(category: Category) {
		isLoadingMoreMovies = true
		viewModelScope.launch(exceptionHandler) {
			Logger.d("Requesting more movies.")
			val pagination = requestMoreCategorisedMovies(category = category)
//			onPaginationObtained(pagination)
			isLoadingMoreMovies = false
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
		}
		_state.update { it.updateToHasCompletedLoading() }
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