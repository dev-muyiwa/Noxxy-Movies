package dev.muyiwa.common.presentation

import android.content.*
import android.view.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.databinding.*
import dev.muyiwa.common.presentation.model.*
import dev.muyiwa.common.utils.*
import kotlin.properties.*

class CompleteCategorisedMoviesAdapter(
	private val context: Context,
	private val listener: ItemClickListener
) :
	ListAdapter<UiCategorisedMovieComplete, RecyclerView.ViewHolder>(
		ITEM_COMPARATOR
	) {
	private var item_view by Delegates.notNull<Int>()
	private val item_list = 0
	private val item_grid = 1
//	private val loading = 2

	fun setLayoutType(value: Int){
		item_view = value
	}

	inner class LinearMoviesViewHolder(
		private val binding: LayoutCategorisedItemCompleteBinding
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(item: UiCategorisedMovieComplete) {
			binding.title.text = item.basicCategorisedMovie.title
			binding.poster.loadImage(item.basicCategorisedMovie.posterPath)
			"${item.basicCategorisedMovie.voteAverage}/10 IMDb".also {
				binding.averageVote.text = it
			}
			binding.overview.text = item.overview
			setupGenreRv(item)
		}

		fun handleClicks(item: UiCategorisedMovieComplete) {
			binding.recyclerViewItemContainer.setOnClickListener {
				listener.navigateToDetails(it, item.basicCategorisedMovie.movieId)
			}
		}

		private fun setupGenreRv(item: UiCategorisedMovieComplete) {
			val genreAdapter = GenreAdapter()
			binding.genreRv.apply {
				layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
				hasFixedSize()
				adapter = genreAdapter
			}
			genreAdapter.submitList(item.genreIds)
		}
	}

	inner class GridMoviesViewHolder(
		private val binding: LayoutCategoryItemGridBinding
	): RecyclerView.ViewHolder(binding.root) {
		fun bind(item: UiCategorisedMovieComplete) {
			binding.title.text = item.basicCategorisedMovie.title
			binding.poster.loadImage(item.basicCategorisedMovie.posterPath)
			"${item.basicCategorisedMovie.voteAverage}/10 IMDb".also { binding.averageVote.text = it }
			binding.recyclerViewItemContainer.setOnClickListener {
				listener.navigateToDetails(it, item.basicCategorisedMovie.movieId)
			}
		}
	}

	inner class LoadingViewHolder(private val binding: LayoutLoadingBarBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun handleLoading() {

		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return when (item_view) {
			item_list -> {
				LinearMoviesViewHolder(
					LayoutCategorisedItemCompleteBinding.inflate(
						LayoutInflater.from(context),
						parent,
						false
					)
				)
			}
			else -> {
				GridMoviesViewHolder(
					LayoutCategoryItemGridBinding.inflate(
						LayoutInflater.from(context),
						parent,
						false
					)
				)
			}
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val movie: UiCategorisedMovieComplete = getItem(position)
		if (holder is LinearMoviesViewHolder) {
			holder.bind(movie)
			holder.handleClicks(movie)
		} else if (holder is GridMoviesViewHolder) {
			holder.bind(movie)
		}
	}

//	override fun getItemViewType(position: Int): Int {
//		return when (getItem(position)) {
//			null -> loading
//			else -> item_list
//		}
//	}

}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UiCategorisedMovieComplete>() {
	override fun areItemsTheSame(
		oldItem: UiCategorisedMovieComplete,
		newItem: UiCategorisedMovieComplete
	): Boolean {
		return oldItem.basicCategorisedMovie.movieId == newItem.basicCategorisedMovie.movieId
	}

	override fun areContentsTheSame(
		oldItem: UiCategorisedMovieComplete,
		newItem: UiCategorisedMovieComplete
	): Boolean {
		return oldItem == newItem
	}
}