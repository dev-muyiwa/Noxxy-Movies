package dev.muyiwa.search.domain.usecases

import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.repositories.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*
import kotlin.time.Duration.Companion.milliseconds

class SearchMovies @Inject constructor(private val repository: MovieRepository) {
	@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
	operator fun invoke(queryFlow: Flow<String>): Flow<List<Movie>> = queryFlow
		.debounce(500.milliseconds)
		.filter { it.length >= 2 }
		.distinctUntilChanged()
		.flatMapLatest { query ->
			repository.searchCachedMoviesBy(query)
		}
}