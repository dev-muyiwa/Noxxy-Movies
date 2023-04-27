package dev.muyiwa.noxxy.details.presentation

import android.view.*
import androidx.recyclerview.widget.*
import dev.muyiwa.common.domain.model.*
import dev.muyiwa.noxxy.details.databinding.*

internal class ReviewsAdapter : ListAdapter<dev.muyiwa.common.domain.model.Review, ReviewsAdapter.ViewHolder>(ITEM_COMPARATOR) {
	inner class ViewHolder(private val binding: LayoutReviewItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
			fun bind(item: Review) {
				binding.author.text = "~ " + item.author
				binding.comment.text = item.content
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
		return oldItem.author == newItem.author && oldItem.content == newItem.content
	}

	override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
		return oldItem == newItem
	}
}