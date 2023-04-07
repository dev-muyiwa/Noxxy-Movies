package dev.muyiwa.home.presentation.all

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.home.domain.usecases.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class HomeMoviesViewModel @Inject constructor(
	private val getAllCategorisedMovies: GetAllCategorisedMovies
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
			getAllCategorisedMovies().onEach { resource ->
				when (resource) {
					is Resource.Loading -> onLoading(resource)
					is Resource.Success -> onSuccess(resource)
					is Resource.Error -> onFailure(resource)
				}
			}.launchIn(this)
		}
	}

	private fun onLoading(resource: Resource.Loading<List<List<CategorisedMovie>>>) {
		_state.update { oldState ->
			oldState.copy(
				isLoading = true,
				allMovies = resource.data.orEmpty().map { it.map { movie -> movie.toUiModel() } }
			)
		}
	}

	private fun onSuccess(resource: Resource.Success<List<List<CategorisedMovie>>>) {
		_state.update { oldState ->
			oldState.copy(
				isLoading = false,
				allMovies = resource.data.orEmpty().map { it.map { movie -> movie.toUiModel() } }
			)
		}
	}

	private fun onFailure(resource: Resource.Error<List<List<CategorisedMovie>>>) {
		_state.update { oldState ->
			oldState.copy(
				isLoading = false,
				allMovies = resource.data.orEmpty().map { it.map { movie -> movie.toUiModel() } },
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