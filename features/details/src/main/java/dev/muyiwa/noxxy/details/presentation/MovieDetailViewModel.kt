package dev.muyiwa.noxxy.details.presentation

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import dev.muyiwa.common.data.api.utils.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.noxxy.details.domain.usecases.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
	private val getMovieDetail: GetMovieDetail,
	savedStateHandle: SavedStateHandle
) : ViewModel() {
	private val _state = MutableStateFlow(MovieDetailState())
	val state = _state.asStateFlow()

	private val errorMessage = "Failed to load movie detail"
	private val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
		onFailed(it)
	}

	init {
		savedStateHandle.get<Int>(MOVIE_ID)?.let { id ->
			getDetailsOfMovie(id)
		}
	}

	private fun getDetailsOfMovie(id: Int) {
		viewModelScope.launch(exceptionHandler) {
			getMovieDetail(id).onEach { resource ->
				when (resource) {
					is Resource.Loading -> onLoading(resource)
					is Resource.Success -> onSuccess(resource)
					is Resource.Error -> onFailure(resource)
				}
			}.launchIn(this)
		}
	}

	private fun onLoading(resource: Resource.Loading<MovieDetail>) {
		_state.update { oldState ->
			oldState.copy(
				isLoading = true,
				movieDetail = resource.data?.toUiModel()
			)
		}
	}

	private fun onSuccess(resource: Resource.Success<MovieDetail>) {
		_state.update { oldState ->
			oldState.copy(
				isLoading = false,
				movieDetail = resource.data?.toUiModel()
			)
		}
	}

	private fun onFailure(resource: Resource.Error<MovieDetail>) {
		_state.update { oldState ->
			oldState.copy(
				isLoading = false,
				movieDetail = resource.data?.toUiModel(),
				failure = Event(Throwable(resource.message))
			)
		}
	}

	private fun onFailed(failure: Throwable) {
		when (failure) {
			is NetworkUnavailableException -> {
				_state.update { oldState ->
					oldState.copy(isLoading = false, failure = Event(failure))
				}
			}
		}
	}
}