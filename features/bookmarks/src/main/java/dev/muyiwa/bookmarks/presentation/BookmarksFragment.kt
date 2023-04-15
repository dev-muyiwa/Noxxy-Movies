package dev.muyiwa.bookmarks.presentation

import android.os.*
import android.view.*
import android.widget.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import dagger.hilt.android.*
import dev.muyiwa.bookmarks.R
import dev.muyiwa.bookmarks.databinding.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.utils.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class BookmarksFragment : Fragment(R.layout.fragment_bookmarks), ItemClickListener {
	private val viewModel: BookmarksViewModel by viewModels()
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val binding = FragmentBookmarksBinding.bind(view)
		val bookmarksAdapter =
			CompleteCategorisedMoviesAdapter(requireContext(), this)
		binding.bookmarksRv.apply {
			layoutManager =
				GridLayoutManager(requireContext(), requireContext().isTablet().gridSize())
			adapter = bookmarksAdapter
			hasFixedSize()
		}
		lifecycleScope.launch {
			viewModel.state.collect {
				bookmarksAdapter.submitList(it.bookmarkedMovies)
				handleFailure(it.failure)
			}
		}
	}

	private fun handleFailure(failure: Event<Throwable>?) {
		val unhandledFailure = failure?.getContentIfNotHandled() ?: return
		val message = "An error occurred, try again later!"
		val toast = unhandledFailure.message ?: message
		Toast.makeText(requireContext(), toast, Toast.LENGTH_LONG).show()
	}
}