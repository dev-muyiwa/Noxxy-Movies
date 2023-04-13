package dev.muyiwa.search.presentation

sealed class SearchEvent {
	data class QueryInput(val input: String): SearchEvent()
	object PrepareForSearch : SearchEvent()
	object RequestForSearchResult: SearchEvent()
}