package dev.muyiwa.noxxy.details.presentation.videos

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import dev.muyiwa.common.data.api.utils.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.noxxy.details.domain.usecases.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class MovieVideosViewModel @Inject constructor(
	private val getVideos: GetVideos,
	savedStateHandle: SavedStateHandle
) : ViewModel() {
	private val _state = MutableStateFlow(MovieVideoState())
	val state = _state.asStateFlow()
	private val errorMessage = "Failed to load videos."
	private val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
		onFailed(it)
	}

	init {
		savedStateHandle.get<Int>(MOVIE_ID)?.let { id ->
			getMovieVideos(id)
		}
	}

	private fun getMovieVideos(id: Int) {
		viewModelScope.launch(exceptionHandler) {
			val videos = getVideos(id)
			if (videos.isNotEmpty()) {
				_state.update { oldState ->
					oldState.copy(isLoading = false, videos = videos)
				}
			}
		}
	}

	private fun onFailed(failure: Throwable) {
		when (failure) {
			is NetworkUnavailableException -> {
				_state.update { oldState ->
					oldState.copy(isLoading = false, failure = Event(failure))
				}
			}
			else -> {
				_state.update { oldState ->
					oldState.copy(isLoading = false, failure = Event(failure))
				}
			}
		}
	}
}