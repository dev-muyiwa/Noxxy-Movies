package dev.muyiwa.bookmarks.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import dagger.hilt.android.AndroidEntryPoint
import dev.muyiwa.bookmarks.*
import dev.muyiwa.bookmarks.databinding.FragmentBookmarksBinding
import dev.muyiwa.common.presentation.*

@AndroidEntryPoint
class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {
	private val viewModel: BookmarksViewModel by viewModels()
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val binding = FragmentBookmarksBinding.bind(view)
//		val bookmarksRecyclerView = CompleteCategorisedMoviesAdapter()
	}
}