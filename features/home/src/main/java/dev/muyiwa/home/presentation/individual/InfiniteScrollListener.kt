package dev.muyiwa.home.presentation.individual

import androidx.recyclerview.widget.*

abstract class InfiniteScrollListener(
	private val layoutManager: LinearLayoutManager,
	private val pageSize: Int
) : RecyclerView.OnScrollListener() {

	override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
		super.onScrolled(recyclerView, dx, dy)
		val visibleItemCount = layoutManager.childCount
		val totalItemCount = layoutManager.itemCount
		val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//		val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()

		if (isLoading().not() && isLastPage().not()) {
			// Loads data when you get to the end of the recyclerview
//			if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && lastVisibleItemPosition >= totalItemCount - 1){
			// Loads data when you get to the some rows before the end of the recyclerview
			if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= pageSize) {
				loadMoreMovies()
			}
		}
	}

	abstract fun isLoading(): Boolean

	abstract fun isLastPage(): Boolean

	abstract fun loadMoreMovies()
}