package dev.muyiwa.home.presentation.all

import android.content.*
import android.view.*
import androidx.navigation.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.model.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.home.databinding.*
import dev.muyiwa.logging.*

class AllMoviesAdapter(private val context: Context, private val listener: ItemClickListener) :
	RecyclerView.Adapter<AllMoviesAdapter.AllMoviesViewHolder>() {
	private var listOfMovies: List<List<UiCategorisedMovie>> = emptyList()

	fun setData(list: List<List<UiCategorisedMovie>>) {
		listOfMovies = list
		notifyDataSetChanged()
	}

	inner class AllMoviesViewHolder(private val binding: LayoutHomeRvBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bindItems(movies: List<UiCategorisedMovie>) {
			if (movies.isEmpty()) {
				return
			}
			val sample = movies[0]
			binding.heading.text = sample.category.title
			binding.btnSeeMore.setOnClickListener {
				listener.navigateToMoreMovies(sample.category)
//				handleNavigation(it, sample.category)
			}
//			val snapHelper = LinearSnapHelper()
			val categorisedMovieAdapter = CategorisedMoviesAdapter(listener)
			binding.categorisedRv.apply {
				layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
				hasFixedSize()
				adapter = categorisedMovieAdapter
			}
//			snapHelper.attachToRecyclerView(binding.categorisedRv)
			categorisedMovieAdapter.submitList(movies)
		}

		private fun handleNavigation(button: View, category: Category) {
			Logger.i("Navigating to ${category.title} screen.")
			val action = HomeFragmentDirections
					.actionHomeFragmentToMoreMoviesFragment(category.name, category.title)
			button.findNavController().navigate(action)

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