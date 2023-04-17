package dev.muyiwa.noxxy.details.presentation

import android.os.*
import android.text.method.*
import android.view.*
import android.widget.*
import androidx.core.view.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.navigation.fragment.*
import androidx.recyclerview.widget.*
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
		binding.appbarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
			binding.posterLayout.isVisible =
				abs(verticalOffset) - appBarLayout.totalScrollRange != 0
			binding.body.posterFrame.isVisible =
				abs(verticalOffset) - appBarLayout.totalScrollRange != 0
		}
		binding.toolbar.apply {
			setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
			setNavigationIconTint(resources.getColor(R.color.toolbar_color))
			setNavigationOnClickListener { findNavController().navigateUp() }
			inflateMenu(R.menu.toolbar_menu_detail)
			setOnMenuItemClickListener {
				when(it.itemId) {
					R.id.bookmark_icon -> {
						viewModel.onEvent(MovieDetailEvent.ToggleBookmarkOption)
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
		val genreAdapter = GenreAdapter()
		binding.body.genreRv.apply {
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
			adapter = genreAdapter
		}
		binding.body.homepage.movementMethod = LinkMovementMethod.getInstance()

		lifecycleScope.launch {
			viewModel.state.collect { detail ->
				val isBookmarked = detail.movieDetail?.movie?.basicCategorisedMovie?.let { it.isBookmarked }
				detail.movieDetail?.let { movieDetail ->
//					binding.toolbar.menu.findItem(R.id.bookmark_icon).setIcon()
					binding.backdropImage.loadImage(movieDetail.movie.backdropPath)
					binding.poster.loadImage(movieDetail.movie.basicCategorisedMovie.posterPath)
					"${movieDetail.movie.basicCategorisedMovie.title} (${
						movieDetail.movie.releaseDate.split("-")[0]
					})".also { binding.body.movieTitle.text = it }
					binding.body.movieRating.rating =
						movieDetail.movie.basicCategorisedMovie.voteAverage.div(2).toFloat()
					binding.body.ratingText.text =
						"${movieDetail.movie.basicCategorisedMovie.voteAverage}"
					binding.body.runtime.text = movieDetail.runtime
					genreAdapter.submitList(movieDetail.movie.genreIds)
					binding.body.movieDescription.text = movieDetail.movie.overview
					binding.body.releaseDate.text = movieDetail.movie.releaseDate
					binding.body.homepage.text = movieDetail.homepage
				}
				handleFailure(detail.failure)
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