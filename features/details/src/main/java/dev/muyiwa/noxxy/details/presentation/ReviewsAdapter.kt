package dev.muyiwa.noxxy.details.presentation

import android.view.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.domain.model.detail.*
import dev.muyiwa.noxxy.details.databinding.*

internal class ReviewsAdapter : ListAdapter<Review, ReviewsAdapter.ViewHolder>(ITEM_COMPARATOR) {
	inner class ViewHolder(private val binding: LayoutReviewItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
			fun bind(item: Review) {
				binding.author.text = "~ " + item.author
				binding.comment.text = item.comment
			}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val review = getItem(position)
		holder.bind(review)
	}
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Review>() {
	override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
		return oldItem.author == newItem.author && oldItem.comment == newItem.comment
	}

	override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
		return oldItem == newItem
	}
}