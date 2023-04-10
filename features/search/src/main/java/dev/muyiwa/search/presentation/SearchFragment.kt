package dev.muyiwa.search.presentation

import android.os.*
import android.view.*
import androidx.fragment.app.*
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.search.R
import dev.muyiwa.search.databinding.*

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), ItemClickListener {

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val binding = FragmentSearchBinding.bind(view)

		binding.searchTrigger.setOnClickListener {
			requireContext().showToast("Search Event fired!")
		}

		val listOfCategories = listOf(
			"Adventure",
			"Fantasy",
			"Animation",
			"Drama",
			"Horror",
			"Action",
			"Comedy",
			"History",
			"Western",
			"Thriller",
			"Crime",
			"Documentary",
			"Science Fiction",
			"Mystery",
			"Music",
			"Romance",
			"Family",
			"War",
			"TV Movie",
			"Just Released",
			"Unknown"
		).sorted()
		val searchCategoryAdapter = SearchCategoryAdapter(requireContext(), this)
		binding.searchCategoryRv.apply {
			layoutManager = GridLayoutManager(requireContext(), 2)
			adapter = searchCategoryAdapter
			hasFixedSize()
		}
		searchCategoryAdapter.submitList(listOfCategories)
	}

}