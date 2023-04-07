package dev.muyiwa.common.presentation

import android.view.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.databinding.*

class GenreAdapter : ListAdapter<String, GenreAdapter.ViewHolder>(ITEM_COMPARATOR) {
	inner class ViewHolder(
		private val binding: LayoutGenreItemBinding,
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(item: String) {
			binding.genre.text = item
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutGenreItemBinding.inflate(
			LayoutInflater.from(parent.context), parent, false
		)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item: String = getItem(position)
		holder.bind(item)
	}
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
	override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
		return oldItem == newItem
	}

	override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
		return oldItem == newItem
	}
}
