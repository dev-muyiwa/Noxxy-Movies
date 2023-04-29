package dev.muyiwa.home.presentation.all

import android.os.*
import android.view.*
import android.widget.*
import androidx.core.view.*
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.navigation.fragment.*
import androidx.recyclerview.widget.*
import dagger.hilt.android.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.*
import dev.muyiwa.common.utils.*
import dev.muyiwa.home.databinding.*
import dev.muyiwa.logging.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemClickListener {
	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding
	private val viewModel: HomeMoviesViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding?.homeRv?.isNestedScrollingEnabled = false
		val allMoviesAdapter = AllMoviesAdapter(requireContext(), this@HomeFragment)
		binding?.homeRv?.apply {
			layoutManager =
				LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
			hasFixedSize()
			adapter = allMoviesAdapter
		}
		lifecycleScope.launch {
			viewModel.state.collect {
				binding?.searchBarHome?.isVisible = it.isLoading
				binding?.homeRv?.isVisible = it.isLoading.not()
				allMoviesAdapter.setData(it.allMovies)
				handleFailure(it.failure)
			}
		}
	}

	private fun handleFailure(failure: Event<Throwable>?) {
		val unhandledFailure = failure?.getContentIfNotHandled() ?: return
		val message = "An error occurred, try again later!"
		val toast = unhandledFailure.message ?: message
		Toast.makeText(requireContext(), toast, Toast.LENGTH_LONG).show()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun navigateToMoreMovies(category: Category) {
		super.navigateToMoreMovies(category)
		val action = HomeFragmentDirections
			.actionHomeFragmentToMoreMoviesFragment(category.name, category.title)
		findNavController().navigate(action)
	}

}