package dev.muyiwa.bookmarks.presentation

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import dev.muyiwa.bookmarks.domain.usecases.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.presentation.model.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.logging.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class BookmarksViewModel @Inject constructor(
//	private val getBookmarkedMovies: GetBookmarkedMovies
) : ViewModel() {
	private val _state = MutableStateFlow(BookmarksViewState())
	val state get() = _state.asStateFlow()

	init {
		subscribeToBookmarkedMovies()
	}

	private fun subscribeToBookmarkedMovies() {
		val message = "Unable to get bookmarked movies."
		viewModelScope.launch(message.createExceptionHandler()) {
			Logger.d("Getting bookmarked movies.")
//			getBookmarkedMovies()
//				.map { movies -> movies.map { it.toFullUiModel() } }
//				.filter { it.isNotEmpty() }
//				.catch { onFailure(it) }
//				.collect { onMoviesList(it) }
		}

//		viewModelScope.launch(message.createExceptionHandler())

	}

	private fun onMoviesList(movies: List<MovieWithGenres>) {
		_state.update { oldState ->
			oldState.copy(bookmarkedMovies = movies)
		}
	}

	private fun String.createExceptionHandler(): CoroutineExceptionHandler {
		return viewModelScope.createExceptionHandler(this) {
			onFailure(it)
		}
	}

	private fun onFailure(throwable: Throwable) {
		_state.update { oldState ->
			oldState.copy(failure = Event(throwable))
		}
	}
}