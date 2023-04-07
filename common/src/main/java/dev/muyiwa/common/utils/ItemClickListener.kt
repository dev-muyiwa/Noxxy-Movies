package dev.muyiwa.common.utils

import android.view.*
import androidx.navigation.Navigation.findNavController
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.logging.*

interface ItemClickListener {
	fun navigateToDetails(view: View, id: Int) {
		Logger.i("Navigating to Details screen.")
		findNavController(view).navigateToDetailsScreen(id)
	}

	fun navigateToMoreMovies(category: Category) {
		Logger.i("Navigating to ${category.title} screen.")
	}

	fun addToBookmarks(movieId: Int){}

	fun removeFromBookmarks(movieId: Int){}
}