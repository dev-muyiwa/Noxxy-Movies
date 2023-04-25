package dev.muyiwa.noxxy.details.presentation

import android.view.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.noxxy.details.databinding.LayoutCastItemBinding

internal class CastsAdapter: ListAdapter<Cast, CastsAdapter.ViewHolder>(ITEM_COMPARATOR) {

	inner class ViewHolder(private val binding: LayoutCastItemBinding): RecyclerView.ViewHolder(binding.root) {
		fun bind(item: Cast) {
			binding.profileImage.loadImage(item.profilePath)
			binding.name.text = item.name
			binding.character.text = item.character
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutCastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val cast: Cast = getItem(position)
		holder.bind(cast)
	}


}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Cast>() {
	override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
		return oldItem.character == newItem.character && oldItem.name == newItem.name
	}

	override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
		return oldItem == newItem
	}
}