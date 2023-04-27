package dev.muyiwa.noxxy.details.presentation.videos

import android.os.*
import android.view.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.*
import dagger.hilt.android.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.noxxy.details.R
import dev.muyiwa.noxxy.details.databinding.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class VideosFragment : Fragment(R.layout.fragment_videos) {
	private val viewModel: MovieVideosViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val binding = FragmentVideosBinding.bind(view)
//		lifecycle.addObserver(binding.youtubePlayer)

//		binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
//			override fun onReady(youTubePlayer: YouTubePlayer) {
//				val videoId = "Sw9hdSjzZjc"
//				youTubePlayer.cueVideo(videoId, 0f)
//			}
//		})
		val videosAdapter = VideosAdapter(lifecycle)
		binding.videosRv.apply {
			layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
			hasFixedSize()
			adapter = videosAdapter
		}
		lifecycleScope.launch {
			viewModel.state.collect{
				videosAdapter.submitList(it.videos)
				handleFailure(it.failure)
			}
		}

	}

	private fun handleFailure(failure: Event<Throwable>?) {
		val unhandledFailure = failure?.getContentIfNotHandled() ?: return
		val fallbackMessage = "An error occurred"
		val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
			fallbackMessage
		}
		else {
			unhandledFailure.message!!
		}
		if (snackbarMessage.isNotEmpty()) {
			Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
		}
	}

}