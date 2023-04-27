package dev.muyiwa.home.presentation.all

import android.content.*
import android.view.*
import androidx.navigation.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.model.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.home.databinding.*
import dev.muyiwa.logging.*

class AllMoviesAdapter(private val context: Context, private val listener: ItemClickListener) :
	RecyclerView.Adapter<AllMoviesAdapter.AllMoviesViewHolder>() {
	private var listOfMovies: List<Pair<Category, List<MovieWithGenres>>> = emptyList()

	fun setData(list: List<Pair<Category, List<MovieWithGenres>>>) {
		listOfMovies = list
		notifyDataSetChanged()
	}

	inner class AllMoviesViewHolder(private val binding: LayoutHomeRvBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bindItems(moviePairs: Pair<Category, List<MovieWithGenres>>) {
			if (moviePairs.second.isEmpty()) {
				return
			}
			binding.heading.text = moviePairs.first.title
			binding.btnSeeMore.setOnClickListener {
				listener.navigateToMoreMovies(moviePairs.first)
			}
			val snapHelper = StartSnapHelper()
			val categorisedMovieAdapter = CategorisedMoviesAdapter(listener)
			binding.categorisedRv.apply {
				layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
				hasFixedSize()
				adapter = categorisedMovieAdapter
			}
			categorisedMovieAdapter.submitList(moviePairs.second)
//			snapHelper.attachToRecyclerView(binding.categorisedRv)
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMoviesViewHolder {
		val view = LayoutHomeRvBinding.inflate(LayoutInflater.from(context), parent, false)
		return AllMoviesViewHolder(view)
	}

	override fun onBindViewHolder(holder: AllMoviesViewHolder, position: Int) {
		val categorisedMovies = listOfMovies[position]
		holder.bindItems(categorisedMovies)
	}

	override fun getItemCount(): Int = listOfMovies.size
}