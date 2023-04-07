package dev.muyiwa.noxxy.details.presentation

import android.os.*
import android.view.*
import android.widget.*
import androidx.core.view.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.navigation.fragment.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.noxxy.details.R
import dev.muyiwa.noxxy.details.databinding.*
import kotlinx.coroutines.*
import kotlin.math.*

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
	private val viewModel: MovieDetailViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val binding = FragmentMovieDetailsBinding.bind(view)
		var isBookmarked = false
//		var scrollRange = -1
		binding.appbarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
//			if (scrollRange == -1){
//				scrollRange = appBarLayout.totalScrollRange
//			}
			binding.posterLayout.isVisible =
				abs(verticalOffset) - appBarLayout.totalScrollRange != 0
//			binding.posterLayout.isVisible = scrollRange + verticalOffset != 0
		}
		binding.toolbar.apply {
			setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
			setNavigationIconTint(resources.getColor(R.color.toolbar_color))
			setNavigationOnClickListener { findNavController().navigateUp() }
			inflateMenu(R.menu.toolbar_menu_detail)
			setOnMenuItemClickListener {
				when(it.itemId) {
					R.id.bookmark_icon -> {
						//
						if (isBookmarked){
							it.setIcon(R.drawable.ic_round_bookmark_remove_24)
//							Snackbar.make(
//								binding.rootLayout,
//								"Added to Bookmarks!",
//								Snackbar.LENGTH_LONG
//							).show()
							Toast.makeText(requireContext(), "Added to Bookmarks!", Toast.LENGTH_SHORT).show()
						} else {
							it.setIcon(R.drawable.ic_round_bookmark_add_24)
//							Snackbar.make(
//								binding.rootLayout,
//								"Removed from Bookmarks!",
//								Snackbar.LENGTH_LONG
//							).show()
							Toast.makeText(requireContext(), "Removed from Bookmarks!", Toast.LENGTH_SHORT).show()
						}
						isBookmarked = isBookmarked.not()
//						Toast.makeText(requireContext(), "Added to Bookmarks!", Toast.LENGTH_SHORT).show()
						true
					}
					else -> false
				}
			}

		}

		lifecycleScope.launch {
			viewModel.state.collect {
//				val isBookmarked = it.movieDetail?.movie?.basicCategorisedMovie?.isFavorite
				binding.detailsText.text = "${it.movieDetail}"
				binding.poster.loadImage(it.movieDetail?.movie?.basicCategorisedMovie?.posterPath.orEmpty())
				binding.backdropImage.loadImage(it.movieDetail?.movie?.backdropPath.orEmpty())
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