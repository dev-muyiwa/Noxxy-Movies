package dev.muyiwa.home.presentation.all

import android.view.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.home.databinding.*

class CategorisedMoviesAdapter(private val listener: ItemClickListener) :
	ListAdapter<MovieWithGenres, CategorisedMoviesAdapter.MoviesViewHolder>(
		ITEM_COMPARATOR
	) {
	inner class MoviesViewHolder(
		private val binding: LayoutCategoryItemBinding
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(item: MovieWithGenres) {
			binding.title.text = item.movie.title
			binding.poster.loadImage(item.movie.posterPath)
			"${item.movie.voteAverage}/10 IMDb".also { binding.averageVote.text = it }
			binding.recyclerViewItemContainer.setOnClickListener {
//				val id = item.movieId
//				(listener::navigateToDetails)(id)
				listener.navigateToDetails(it, item.movie.movieId)
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
		val movie = getItem(position) as MovieWithGenres
		holder.bind(movie)
	}

}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<MovieWithGenres>() {
	override fun areItemsTheSame(
		oldItem: MovieWithGenres,
		newItem: MovieWithGenres
	): Boolean {
		return oldItem.movie.movieId == newItem.movie.movieId
	}

	override fun areContentsTheSame(
		oldItem: MovieWithGenres,
		newItem: MovieWithGenres
	): Boolean {
		return oldItem == newItem
	}
}