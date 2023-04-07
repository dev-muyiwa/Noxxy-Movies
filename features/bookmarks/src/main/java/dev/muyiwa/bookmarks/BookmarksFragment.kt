package dev.muyiwa.bookmarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import dev.muyiwa.bookmarks.databinding.FragmentBookmarksBinding

@AndroidEntryPoint
class BookmarksFragment : Fragment() {
	private var _binding: FragmentBookmarksBinding? = null
	private val binding get() = _binding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentBookmarksBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}