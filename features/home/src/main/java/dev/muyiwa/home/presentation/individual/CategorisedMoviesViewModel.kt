package dev.muyiwa.home.presentation.individual

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
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
	private val getCategorisedMovieFlow: GetCategorisedMovieFlow,
	private val getCategorisedMovies: GetCategorisedMovies,
	private val requestMoreCategorisedMovies: RequestMoreCategorisedMovies
) : ViewModel() {
	private val _state = MutableStateFlow(CategorisedMoviesState())
	private var currentPage = 1
	private var testPage = 1
	val pageSize = Pagination.DEFAULT_PAGE_SIZE
	var category = Category.POPULAR
	val state = _state.asStateFlow()
	var isLoadingMoreMovies = false
	var isLastPage = false

	private val errorMessage = "Failed to load more movies."
	private val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) {
		onFailed(it)
	}

	init {
//		subscribeToMoviesUpdate(currentPage)
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
				.map { movies -> movies.map { it.toFullUiModel() } }
				.filter { it.isNotEmpty() }
				.flowOn(Dispatchers.Default)
				.catch { onFailed(it) }
				.collect{ onNewMoviesList(it) }

			/**
			getCategorisedMovies(category)
//				.flatMapLatest {
//					if (hasNoMoviesStoredButCanLoadMore(it)) loadNextPageOfMovies()
//				}
				.map { movies -> movies.map { it.toFullUiModel() } }
				.onEach {
//					if (hasNoMoviesStoredButCanLoadMore(it)) loadNextPageOfMovies()
					onNewMoviesList(it)
				}
				.filter { it.isNotEmpty() }
//				.flowOn(this)
//				.subscribe { onNewMoviesList(it) }
//				.onCompletion {
//					onNewMoviesList()
////					onFailed(it)
//				}
				.catch { onFailed(it) }
				.launchIn(this)
			*/
		}
	}

	private fun subscribeToMoviesUpdate(page: Int) {
		viewModelScope.launch(exceptionHandler) {
//			pageSize =
			getCategorisedMovieFlow(category, page).onEach { resource ->
				when (resource) {
					is Resource.Loading -> onLoading(resource)
					is Resource.Success -> onSuccess(resource)
					is Resource.Error -> onFailure(resource)
				}
//				pageSize += resource.data.orEmpty().size
			}.onCompletion {
//				if (isLoadingMoreMovies.not()) {
//					Logger.i("Completed!")
//					subscribeToMoviesUpdate(currentPage++)
//					isLoadingMoreMovies = true
//				}
			}.launchIn(this)

		}
	}

	private fun hasNoMoviesStoredButCanLoadMore(movies: List<CategorisedMovie>): Boolean {
		return movies.isEmpty() && ! state.value.noMoreMovies
	}

	private fun onNewMoviesList(movies: List<UiCategorisedMovieComplete>) {
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
			val pagination = requestMoreCategorisedMovies(++testPage, category)
			onPaginationObtained(pagination)
//			val resource = getCategorisedMovieFlow(category, currentPage ++).first()
//
//			subscribeToMoviesUpdate(currentPage++)
//			Logger.d("New list = $new")
			isLoadingMoreMovies = false
		}
	}

	private fun onPaginationObtained(pagination: Pagination){
		testPage = pagination.currentPage
		isLastPage = pagination.canLoadMore.not()
	}

	private fun onLoading(resource: Resource.Loading<List<CategorisedMovie>>) {
		_state.update { oldState ->
			oldState.copy(
				isLoading = true,
				categorisedMovies = (state.value.categorisedMovies + resource.data.orEmpty()
					.map { it.toFullUiModel() }).toSet().toList()
//					.distinct()
			)
		}
	}

	private fun onSuccess(resource: Resource.Success<List<CategorisedMovie>>) {
		_state.update { oldState ->
			oldState.copy(
				isLoading = false,
				categorisedMovies = (state.value.categorisedMovies + resource.data.orEmpty()
					.map { it.toFullUiModel() }).toSet().toList()

			)
		}
	}

	private fun onFailure(resource: Resource.Error<List<CategorisedMovie>>) {
		_state.update { oldState ->
			oldState.copy(
				isLoading = false,
				categorisedMovies = (state.value.categorisedMovies + resource.data.orEmpty()
					.map { it.toFullUiModel() }).toSet().toList(),
				failure = Event(Throwable(resource.message))
			)
		}
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

//	override fun onCleared() {
//		super.onCleared()
//		currentPage = 1
//	}
}