package dev.muyiwa.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import dev.muyiwa.search.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : Fragment() {
	private var _binding: FragmentSearchBinding? = null
	private val binding get() = _binding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentSearchBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}