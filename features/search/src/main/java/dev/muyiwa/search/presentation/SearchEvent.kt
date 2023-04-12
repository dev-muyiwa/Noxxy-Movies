package dev.muyiwa.search.presentation

sealed class SearchEvent {
	object PrepareForSearch : SearchEvent()
	data class QueryInput(val input: String): SearchEvent()
}