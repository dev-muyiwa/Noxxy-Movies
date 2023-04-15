package dev.muyiwa.search.presentation

import android.content.*
import android.os.*
import android.view.*
import androidx.appcompat.widget.*
import androidx.core.view.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.*
import dagger.hilt.android.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.search.R
import dev.muyiwa.search.databinding.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), ItemClickListener {
	private val viewModel: SearchViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val binding = FragmentSearchBinding.bind(view)
		setupSearchViewListener(binding)
		viewModel.onEvent(SearchEvent.PrepareForSearch)
		val searches = listOf("Antman", "Akeelah and the bee", "Pinnochio", "John wick")
		val searchCategoryAdapter = SearchCategoryAdapter(requireContext(), this)
		val resultsAdapter = CompleteCategorisedMoviesAdapter(requireContext(), this)
		binding.recentSearchRv.apply {
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
			adapter = searchCategoryAdapter
			hasFixedSize()
		}
		binding.searchResultsRv.apply {
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
			addOnScrollListener(createInfiniteScrollListener(layoutManager as LinearLayoutManager))
			adapter = resultsAdapter
			hasFixedSize()
		}
		searchCategoryAdapter.submitList(searches)
		lifecycleScope.launch {
			viewModel.state.collect {
				resultsAdapter.submitList(it.searchResults)
				binding.recentSearchRv.isVisible = it.noSearchQuery
				binding.loadingSearch.isVisible =
					it.searchResults.isEmpty() && it.noSearchQuery.not()
//							&& it.noRemoteResults
				handleFailures(it.failure)
			}
		}
	}

	private fun createInfiniteScrollListener(layoutManager: LinearLayoutManager):
			RecyclerView.OnScrollListener {

		return object : InfiniteScrollListener(layoutManager, viewModel.pageSize) {
			override fun isLoading(): Boolean = viewModel.isLoadingMoreMovies

			override fun isLastPage(): Boolean = viewModel.isLastPage

			override fun loadMoreMovies() = viewModel.onEvent(SearchEvent.RequestForSearchResult)
		}
	}

	private fun setupSearchViewListener(binding: FragmentSearchBinding) {
		binding.searchBar.setOnQueryTextListener(
			object : SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String?): Boolean {
					viewModel.onEvent(SearchEvent.QueryInput(query.orEmpty()))
					requireContext().showToast("Query is $query")
					binding.searchBar.clearFocus()
					return true
				}

				override fun onQueryTextChange(newText: String?): Boolean {
					viewModel.onEvent(SearchEvent.QueryInput(newText.orEmpty()))
					return true
				}
			}
		)
	}

	private fun handleFailures(failure: Event<Throwable>?) {
		val unhandledFailure = failure?.getContentIfNotHandled() ?: return
		val fallbackMessage = "An error occurred"
		val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
			fallbackMessage
		}
		else {
			unhandledFailure.message!!
		}
		if (snackbarMessage.isNotEmpty()) {
			Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
		}
	}

}