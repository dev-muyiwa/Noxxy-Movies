package dev.muyiwa.noxxy.details.presentation

import android.os.*
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

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
	private val viewModel: MovieDetailViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val binding = FragmentMovieDetailsBinding.bind(view)

		var isBookmarked = false

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
							Toast.makeText(requireContext(), "Added to Bookmarks!", Toast.LENGTH_SHORT).show()
						} else {
							it.setIcon(R.drawable.ic_round_bookmark_add_24)
							Toast.makeText(requireContext(), "Removed from Bookmarks!", Toast.LENGTH_SHORT).show()
						}
						isBookmarked = isBookmarked.not()
						true
					}
					else -> false
				}
			}

		}
		val genreAdapter = GenreAdapter()
		val castsAdapter = CastsAdapter()
		val reviewsAdapter = ReviewsAdapter()
		binding.body.genreRv.apply {
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
			adapter = genreAdapter
		}
		binding.body.castsRv.apply {
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
			adapter = castsAdapter
		}
		binding.body.reviewsRv.apply {
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
			adapter = reviewsAdapter
		}

		binding.fabPlay.setOnClickListener {
			val action = MovieDetailsFragmentDirections
				.actionMovieDetailsFragmentToVideosFragment(viewModel.movieId)
			findNavController().navigate(action)
		}

		binding.body.btnShowGenre.setOnClickListener {
			requireContext().showToast("Showing genres.")
		}

		lifecycleScope.launch {
			viewModel.state.collect { detail ->
				val isMovieBookmarked =
					detail.movieDetail?.isBookmarked
				isMovieBookmarked?.setBookmarkIcon()
					?.let { binding.toolbar.menu.findItem(R.id.bookmark_icon).setIcon(it) }
				if (detail.movieDetail != null) {
					detail.movieDetail.let { movieDetail ->
						binding.backdropImage.loadImage(movieDetail.movie.backdropPath)
						binding.body.posterImage.loadImage(movieDetail.movie.posterPath)
						"${movieDetail.movie.title} (${
							movieDetail.movie.releaseDate.split("-")[0]
						})".also { binding.body.movieTitle.text = it }
						binding.body.movieRating.rating =
							movieDetail.movie.voteAverage.div(2).toFloat()
						binding.body.ratingText.text =
							"${movieDetail.movie.voteAverage}"
						binding.body.runtime.text = "Runtime: ${movieDetail.detail.runtime} mins"
						genreAdapter.submitList(movieDetail.genres)
						binding.body.movieDescription.text = movieDetail.movie.overview
						castsAdapter.submitList(movieDetail.casts)
						reviewsAdapter.submitList(movieDetail.reviews)
						binding.body.noReviews.isVisible = movieDetail.reviews.isEmpty()
					}
				}
				handleFailure(detail.failure)
//				requireContext().showToast(detail.failure.toString())
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