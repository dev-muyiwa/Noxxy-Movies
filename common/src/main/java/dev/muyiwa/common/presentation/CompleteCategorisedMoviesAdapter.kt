package dev.muyiwa.common.presentation

import android.content.*
import android.view.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.databinding.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.utils.*

class CompleteCategorisedMoviesAdapter(
	private val context: Context,
	private val listener: ItemClickListener
) :
	ListAdapter<MovieWithGenres, RecyclerView.ViewHolder>(
		ITEM_COMPARATOR
	) {
	private var item_view  = 0
	private val item_list = 0
	private val item_grid = 1
//	private val loading = 2

	fun setLayoutType(value: Int){
		item_view = value
	}

	inner class LinearMoviesViewHolder(
		private val binding: LayoutCategorisedItemCompleteBinding
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(item: MovieWithGenres) {
			binding.title.text = item.movie.title
			binding.poster.loadImage(item.movie.posterPath)
			"${item.movie.voteAverage}/10 IMDb".also {
				binding.averageVote.text = it
			}
			binding.overview.text = item.movie.overview
			setupGenreRv(item)
		}

		fun handleClicks(item: MovieWithGenres) {
			binding.recyclerViewItemContainer.setOnClickListener {
				listener.navigateToDetails(it, item.movie.movieId)
			}
		}

		private fun setupGenreRv(item: MovieWithGenres) {
			val genreAdapter = GenreAdapter()
			binding.genreRv.apply {
				layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
				hasFixedSize()
				adapter = genreAdapter
			}
			genreAdapter.submitList(item.genres)
		}
	}

	inner class GridMoviesViewHolder(
		private val binding: LayoutCategoryItemGridBinding
	): RecyclerView.ViewHolder(binding.root) {
		fun bind(item: MovieWithGenres) {
			binding.title.text = item.movie.title
			binding.poster.loadImage(item.movie.posterPath)
			"${item.movie.voteAverage}/10 IMDb".also { binding.averageVote.text = it }
			binding.recyclerViewItemContainer.setOnClickListener {
				listener.navigateToDetails(it, item.movie.movieId)
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
		val movie: MovieWithGenres = getItem(position)
		if (holder is LinearMoviesViewHolder) {
			holder.bind(movie)
			holder.handleClicks(movie)
		} else if (holder is GridMoviesViewHolder) {
			holder.bind(movie)
		}
	}
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<MovieWithGenres>() {
	override fun areItemsTheSame(
		oldItem: MovieWithGenres,
		newItem: MovieWithGenres
	): Boolean {
		return oldItem.movie == newItem.movie
	}

	override fun areContentsTheSame(
		oldItem: MovieWithGenres,
		newItem: MovieWithGenres
	): Boolean {
		return oldItem == newItem
	}
}