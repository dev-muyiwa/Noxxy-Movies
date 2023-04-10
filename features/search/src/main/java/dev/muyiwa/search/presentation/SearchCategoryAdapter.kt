package dev.muyiwa.search.presentation

import android.content.*
import android.view.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.search.databinding.*

class SearchCategoryAdapter(private val context: Context, private val listener: ItemClickListener) :
	ListAdapter<String, SearchCategoryAdapter.StringViewHolder>(ITEM_COMPARATOR) {
	inner class StringViewHolder(
		private val binding: LayoutSearchItemBinding
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(category: String) {
			binding.searchCategory.text = category
		}

		fun handleClickEvents(category: String) {
			binding.itemBackground.setOnClickListener {
				listener.navigateToSearchScreen(
					context,
					category
				)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
		val view = LayoutSearchItemBinding
			.inflate(LayoutInflater.from(context), parent, false)
		return StringViewHolder(view)
	}

	override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
		val category: String = getItem(position)
		holder.bind(category)
		holder.handleClickEvents(category)
	}


}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
	override fun areItemsTheSame(
		oldItem: String,
		newItem: String
	): Boolean {
		return oldItem == newItem
	}

	override fun areContentsTheSame(
		oldItem: String,
		newItem: String
	): Boolean {
		return oldItem == newItem
	}
}