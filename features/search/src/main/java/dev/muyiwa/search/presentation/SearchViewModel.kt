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

	private val _state = MutableStateFlow(SearchViewState())
	private val queryStateFlow = MutableStateFlow("")

	val state: StateFlow<SearchViewState> = _state.asStateFlow()

//	init {
//		setupSearchSubscription()
//	}

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
			else -> Logger.d("Wrong SearchEvent in onSearchParametersUpdate!")
		}
	}

	private fun updateQuery(input: String) {
		resetPagination()
		queryStateFlow.value = input

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

//	private fun setupSearchSubscription() {
//		val message = "Unable to search."
//		viewModelScope.launch(createExceptionHandler(message)) {
//			searchMovies(queryStateFlow)
//				.map { movies ->
//					movies.map { it.toCategorisedMovie().toFullUiModel() }
//				}
//				.onStart { setSearchingState() }
//				.onEmpty { setNoSearchQueryState() }
//				.catch { onFailure(it) }
//				.collect { onAnimalList(it) }
//		}
//	}

//	private fun updateQuery(input: String) {
//		resetPagination()
//		queryStateFlow.value = input
//	}

	private fun searchRemotely(query: String) {
		val exceptionHandler = createExceptionHandler(message = "Failed to search remotely.")
		remoteSearchJob = viewModelScope.launch(exceptionHandler) {
			Logger.d("Searching remotely...")
			val pagination = searchMoviesRemotely(++ currentPage, query)
			onPaginationInfoObtained(pagination)
		}
		remoteSearchJob.invokeOnCompletion { it?.printStackTrace() }
	}

	private fun onPaginationInfoObtained(pagination: Pagination) {
		currentPage = pagination.currentPage
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


//	-----------------------------------------------------------------------------------
//	private var remoteSearchJob: Job = Job()
//	private val _state = MutableStateFlow(SearchViewState())
//	private var currentPage = 0
//	private val querySubject = MutableStateFlow("")


//	fun onEvent(event: SearchEvent) {
//		when (event) {
//			is SearchEvent.PrepareForSearch -> subscribeToSearch()
//			else -> onSearchParametersUpdate(event)
//		}
//	}
//
//	private fun createExceptionHandler(message: String): CoroutineExceptionHandler {
//		return viewModelScope.createExceptionHandler(message) {
//			onFailure(it)
//		}
//	}
//
//	private fun subscribeToSearch() {
//		searchMovies(querySubject)
//			.onEach { onSearchResults(querySubject.value, it) }
//			.flowOn(Dispatchers.IO)
//			.catch { onFailure(it) }
//			.launchIn(viewModelScope)
//	}
//
//	private fun onSearchParametersUpdate(event: SearchEvent) {
//		remoteSearchJob.cancel(
//			CancellationException("New search parameters incoming!")
//		)
//
//		when (event) {
//			is SearchEvent.QueryInput -> updateQuery(event.input)
//			else -> Logger.d("Wrong SearchEvent in onSearchParametersUpdate!")
//		}
//	}
//
//	private fun onSearchResults(query: String, movies: List<Movie>) {
//		if (movies.isEmpty()) {
//			onEmptyCacheResults(query)
//		} else {
//			onMovieList(movies)
//		}
//	}
//
//	private fun onMovieList(movies: List<Movie>) {
////		_state.update { oldState ->
//////			oldState.updateToHasSearchResults(movies.map { uiAnimalMapper.mapToView(it) })
////		}
//	}
//
//	private fun onEmptyCacheResults(query: String) {
//		_state.update { oldState ->
//			oldState.updateToSearchingRemotely()
//		}
//		searchRemotely(query)
//	}
//
//	private fun searchRemotely(searchQuery: String) {
//		val exceptionHandler = createExceptionHandler(message = "Failed to search remotely.")
//		remoteSearchJob = viewModelScope.launch(exceptionHandler) {
//			Logger.d("Searching remotely...")
//			val pagination = searchMoviesRemotely(++ currentPage, searchQuery)
//			onPaginationInfoObtained(pagination)
//		}
//		remoteSearchJob.invokeOnCompletion { it?.printStackTrace() }
//	}
//
//	private fun updateQuery(input: String) {
//		resetPagination()
//		querySubject.onEach { }
//
//		if (input.isEmpty()) {
//			setNoSearchQueryState()
//		} else {
//			setSearchingState()
//		}
//	}
//
//	private fun onNext(input: String) {
//		querySubject.value = input
//	}
//
//	fun subscribe(onNext: (String) -> Unit): Job {
//		val job = Job()
//		querySubject.onEach { onNext(it) }.distinctUntilChanged().launchIn(remoteSearchJob)
//		onNext(querySubject.value) // Emit the current value immediately
//		return job
//	}
//
//	fun unsubscribe(job: Job) {
//		job.cancel()
//	}
//
//	private fun setSearchingState() {
//		_state.update { oldState -> oldState.updateToSearching() }
//	}
//
//	private fun setNoSearchQueryState() {
//		_state.update { oldState -> oldState.updateToNoSearchQuery() }
//	}
//
//	private fun resetPagination() {
//		currentPage = 0
//	}
//
//	private fun onPaginationInfoObtained(pagination: Pagination) {
//		currentPage = pagination.currentPage
//	}
//
//	private fun onFailure(throwable: Throwable) {
//		_state.update { oldState ->
//			if (throwable is NoMoreMoviesException) {
//				oldState.updateToNoResultsAvailable()
//			} else {
//				oldState.updateToHasFailure(throwable)
//			}
//		}
//	}
}