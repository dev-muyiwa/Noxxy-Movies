package dev.muyiwa.noxxy.details.presentation.videos

import android.view.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.*
import dev.muyiwa.common.domain.model.detail.*
import dev.muyiwa.noxxy.details.databinding.*

internal class VideosAdapter(private val lifecycle: Lifecycle) :
	ListAdapter<Video, VideosAdapter.ViewHolder>(ITEM_COMPARATOR) {

	inner class ViewHolder(private val binding: LayoutVideoItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(item: Video) {
			binding.title.text = item.name
			lifecycle.addObserver(binding.youtubePlayer)
			binding.youtubePlayer.addYouTubePlayerListener(object :
				AbstractYouTubePlayerListener() {
				override fun onReady(youTubePlayer: YouTubePlayer) {
					youTubePlayer.cueVideo(item.videoKey, 0f)
				}
			})
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view =
			LayoutVideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val video: Video = getItem(position)
		holder.bind(video)
	}


}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Video>() {
	override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
		return oldItem.name == newItem.name && oldItem.videoId == newItem.videoId
	}

	override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
		return oldItem == newItem
	}
}