package dev.muyiwa.search.presentation

import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.presentation.model.*

data class SearchViewState(
	val noSearchQuery: Boolean = true,
	val searchResults: List<MovieWithGenres> = emptyList(),
	val searchingRemotely: Boolean = false,
	val noRemoteResults: Boolean = false,
	val failure: Event<Throwable>? = null
) {
	fun updateToNoSearchQuery(): SearchViewState {
		return copy(
			noSearchQuery = true,
			searchResults = emptyList(),
			noRemoteResults = false
		)
	}

	fun updateToSearching(): SearchViewState {
		return copy(
			noSearchQuery = false,
			searchingRemotely = false,
			noRemoteResults = false
		)
	}

	fun updateToSearchingRemotely(): SearchViewState {
		return copy(
			searchingRemotely = true,
		)
	}

	fun updateToHasSearchResults(movies: List<MovieWithGenres>): SearchViewState {
		return copy(
			noSearchQuery = false,
			searchResults = (searchResults + movies).toSet().toList(),
			searchingRemotely = false,
			noRemoteResults = false
		)
	}

	fun updateToNoResultsAvailable(): SearchViewState {
		return copy(searchingRemotely = false, noRemoteResults = true)
	}

	fun updateToHasFailure(throwable: Throwable): SearchViewState {
		return copy(failure = Event(throwable))
	}
}