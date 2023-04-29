package dev.muyiwa.home.presentation.individual

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import dev.muyiwa.common.data.preferences.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.presentation.model.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.home.domain.usecases.*
import dev.muyiwa.logging.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class CategorisedMoviesViewModel @Inject constructor(
	private val getCategorisedMovies: GetCategorisedMovies,
	private val requestMoreCategorisedMovies: RequestMoreCategorisedMovies,
	private val preferences: Preferences,
) : ViewModel() {
	private val _state = MutableStateFlow(CategorisedMoviesState())
	private var currentPage = 1
	val pageSize = dev.muyiwa.common.domain.model.Pagination.DEFAULT_PAGE_SIZE
	var category = Category.POPULAR
	val prefs = preferences
	val state = _state.asStateFlow()
	var isLoadingMoreMovies = false
	var isLastPage = false

	private val errorMessage = "Failed to load more movies."
	private val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
		onFailed(it)
	}

	init {
		subscribeToMoviesUpdate()
	}

	fun onEvent(event: CategorisedMoviesEvent) {
		when (event) {
			is CategorisedMoviesEvent.RequestMoreMovies -> loadNextPageOfMovies()
		}
	}

	private fun subscribeToMoviesUpdate() {
		viewModelScope.launch(exceptionHandler) {
			getCategorisedMovies(category)
				.onEach { if (hasNoMoviesStoredButCanLoadMore(it)) loadNextPageOfMovies() }
				.filter { it.isNotEmpty() }
				.flowOn(Dispatchers.Default)
				.catch { onFailed(it) }
				.collect { onNewMoviesList(it) }
		}
	}

	private fun hasNoMoviesStoredButCanLoadMore(movies: List<MovieWithGenres>): Boolean {
		return movies.isEmpty() && ! state.value.noMoreMovies
	}

	private fun onNewMoviesList(movies: List<MovieWithGenres>) {
		Logger.d("Got more movies.")
		val updatedMoviesSet = (state.value.categorisedMovies + movies).toSet()
		_state.update { oldState ->
			oldState.copy(isLoading = false, categorisedMovies = updatedMoviesSet.toList())
		}
	}

	private fun loadNextPageOfMovies() {
		isLoadingMoreMovies = true
		viewModelScope.launch(exceptionHandler) {
			Logger.d("Requesting more movies.")
			val pagination = requestMoreCategorisedMovies(++currentPage, category)
			onPaginationObtained(pagination)
			isLoadingMoreMovies = false
		}
	}

	private fun onPaginationObtained(pagination: dev.muyiwa.common.domain.model.Pagination) {
		currentPage = pagination.currentPage
		isLastPage = pagination.canLoadMore.not()
	}

	private fun onFailed(failure: Throwable?) {
		when (failure) {
			is NetworkUnavailableException,
			is NetworkException -> {
				_state.update { oldState ->
					oldState.copy(isLoading = false, failure = Event(failure))
				}
			}
			is NoMoreMoviesException -> {
				_state.update { oldState ->
					oldState.copy(noMoreMovies = true, failure = Event(failure))
				}
			}
		}
	}
}