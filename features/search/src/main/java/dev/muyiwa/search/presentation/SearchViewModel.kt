package dev.muyiwa.search.presentation

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.model.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.logging.*
import dev.muyiwa.search.domain.usecases.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class SearchViewModel @Inject constructor(
	private val searchMovies: SearchMovies,
	private val searchMoviesRemotely: SearchMoviesRemotely
) : ViewModel() {

	private var remoteSearchJob: Job = Job()
	private var currentPage = 0
	val pageSize = Pagination.DEFAULT_PAGE_SIZE
	var isLoadingMoreMovies = false
	var isLastPage = false

	private val _state = MutableStateFlow(SearchViewState())
	private val queryStateFlow = MutableStateFlow("")

	val state: StateFlow<SearchViewState> = _state.asStateFlow()

	fun onEvent(event: SearchEvent) {
		when (event) {
			is SearchEvent.PrepareForSearch -> setupSearchSubscription()
			else -> onSearchParametersUpdate(event)
		}
	}

	private fun setupSearchSubscription() {
		Logger.d("Setting up search subscription.")
		val message = "Unable to search."
		viewModelScope.launch(createExceptionHandler(message)) {
			searchMovies(queryStateFlow)
				.map { movies ->
					movies.map { it.toCategorisedMovie().toFullUiModel() }
				}
				.catch { onFailure(it) }
				.collect { onSearchResults(it) }
		}
	}

	private fun onSearchResults(result: List<UiCategorisedMovieComplete>) {
		if (result.isEmpty()) {
			onEmptyCacheResults(queryStateFlow.value)
		} else {
			onAnimalList(result)
		}
	}

	private fun onEmptyCacheResults(searchQuery: String) {
		_state.update { oldState -> oldState.updateToSearchingRemotely() }
		searchRemotely(searchQuery)
	}


	private fun onSearchParametersUpdate(event: SearchEvent) {
		remoteSearchJob.cancel(
			CancellationException("New search parameters incoming!")
		)
		when (event) {
			is SearchEvent.QueryInput -> updateQuery(event.input)
			is SearchEvent.RequestForSearchResult -> onEmptyCacheResults(queryStateFlow.value)
			else -> Logger.d("Wrong SearchEvent in onSearchParametersUpdate!")
		}
	}

	private fun updateQuery(input: String) {
		resetPagination()
		queryStateFlow.value = input
		Logger.i("Search query updated.")

		if (input.isEmpty()) {
			setNoSearchQueryState()
		} else {
			setSearchingState()
		}
	}

	private fun createExceptionHandler(message: String): CoroutineExceptionHandler {
		return viewModelScope.createExceptionHandler(message) {
			onFailure(it)
		}
	}

	private fun searchRemotely(query: String) {
		val exceptionHandler = createExceptionHandler(message = "Failed to search remotely.")
		isLoadingMoreMovies = true
		remoteSearchJob = viewModelScope.launch(exceptionHandler) {
			Logger.d("Searching remotely...")
			val pagination = searchMoviesRemotely(++ currentPage, query)
			onPaginationInfoObtained(pagination)
			isLoadingMoreMovies = false
		}
		remoteSearchJob.invokeOnCompletion { it?.printStackTrace() }
	}

	private fun onPaginationInfoObtained(pagination: Pagination) {
		Logger.i("Pagination info obtained.")
		currentPage = pagination.currentPage
		isLastPage = pagination.canLoadMore.not()
	}

	private fun setSearchingState() {
		_state.update { oldState -> oldState.updateToSearching() }
	}

	private fun setNoSearchQueryState() {
		_state.update { oldState -> oldState.updateToNoSearchQuery() }
	}

	private fun onAnimalList(movies: List<UiCategorisedMovieComplete>) {
		_state.update { oldState ->
			oldState.updateToHasSearchResults(movies)
		}
	}

	private fun resetPagination() {
		currentPage = 0
	}

	private fun onFailure(throwable: Throwable) {
		_state.update { oldState ->
			if (throwable is NoMoreMoviesException) {
				oldState.updateToNoResultsAvailable()
			} else {
				oldState.updateToHasFailure(throwable)
//				throw throwable // uncomment this to crash the app. However, you get to see the type of
//				exception in the logcat.
			}
		}
	}
}