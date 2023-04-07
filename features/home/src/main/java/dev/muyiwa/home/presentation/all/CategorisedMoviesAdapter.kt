package dev.muyiwa.home.presentation.all

import android.graphics.*
import android.view.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.presentation.model.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.home.databinding.*

class CategorisedMoviesAdapter(private val listener: ItemClickListener) :
	ListAdapter<UiCategorisedMovie, CategorisedMoviesAdapter.MoviesViewHolder>(
		ITEM_COMPARATOR
	) {

	inner class MoviesViewHolder(
		private val binding: LayoutCategoryItemBinding
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(item: UiCategorisedMovie) {
			binding.title.text = item.title
			binding.poster.loadImage(item.posterPath)
			"${item.voteAverage}/10 IMDb".also { binding.averageVote.text = it }
			binding.recyclerViewItemContainer.setOnClickListener {
//				val id = item.movieId
//				(listener::navigateToDetails)(id)
				listener.navigateToDetails(it, item.movieId)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
		val view = LayoutCategoryItemBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return MoviesViewHolder(view)
	}

	override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
		val movie = getItem(position) as UiCategorisedMovie
		holder.bind(movie)
	}

}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UiCategorisedMovie>() {
	override fun areItemsTheSame(
		oldItem: UiCategorisedMovie,
		newItem: UiCategorisedMovie
	): Boolean {
		return oldItem.movieId == newItem.movieId
	}

	override fun areContentsTheSame(
		oldItem: UiCategorisedMovie,
		newItem: UiCategorisedMovie
	): Boolean {
		return oldItem == newItem
	}
}