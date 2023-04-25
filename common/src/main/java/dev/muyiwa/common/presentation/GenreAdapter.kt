package dev.muyiwa.common.presentation

import android.view.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.databinding.*
import dev.muyiwa.common.domain.model.*

class GenreAdapter : ListAdapter<Genre, GenreAdapter.ViewHolder>(ITEM_COMPARATOR) {
	inner class ViewHolder(
		private val binding: LayoutGenreItemBinding,
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(item: Genre) {
			binding.genre.text = item.name
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutGenreItemBinding.inflate(
			LayoutInflater.from(parent.context), parent, false
		)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item: Genre = getItem(position)
		holder.bind(item)
	}
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Genre>() {
	override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
		return oldItem.name == newItem.name
	}

	override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
		return oldItem == newItem
	}
}
