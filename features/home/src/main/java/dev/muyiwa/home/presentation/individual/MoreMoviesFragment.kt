package dev.muyiwa.home.presentation.individual

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
import dev.muyiwa.home.R
import dev.muyiwa.home.databinding.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class MoreMoviesFragment : Fragment(), ItemClickListener {
	private var _binding: FragmentMoreMoviesBinding? = null
	private val binding get() = _binding
	private val viewModel: CategorisedMoviesViewModel by viewModels()
	private val args: MoreMoviesFragmentArgs by navArgs()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentMoreMoviesBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val category = Category.valueOf(args.category)
		val isGrid = true
		val menuHost: MenuHost = requireActivity()
		menuHost.addMenuProvider(object : MenuProvider{
			override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
				menuInflater.inflate(R.menu.toolbar_more_movies, menu)
			}

			override fun onPrepareMenu(menu: Menu) {
				super.onPrepareMenu(menu)
				val layoutItem: MenuItem = menu.findItem(R.id.layout_type)
//				if ()
			}

			override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
				return when(menuItem.itemId){
					R.id.layout_type -> {
						Toast.makeText(requireContext(), "Layout changed!", Toast
							.LENGTH_SHORT).show()
						true
					}else -> false
				}
			}
		}, viewLifecycleOwner, Lifecycle.State.RESUMED)
		lifecycleScope.launch {
			viewModel.category = category
			viewModel.state.collect {
				val moreMoviesAdapter =
					CompleteCategorisedMoviesAdapter(requireContext(), this@MoreMoviesFragment)
				moreMoviesAdapter.setLayoutType(1)
				binding?.fullCategoryRv?.apply {
					layoutManager = selectLayoutManager(isGrid)
					hasFixedSize()
					addOnScrollListener(createInfiniteScrollListener(layoutManager as LinearLayoutManager))
					adapter = moreMoviesAdapter
				}
				moreMoviesAdapter.submitList(it.categorisedMovies)
				handleFailure(it.failure)
			}
		}
	}

	private fun selectLayoutManager(isGridLayout: Boolean): RecyclerView.LayoutManager {
		return if (isGridLayout){
			GridLayoutManager(requireContext(), requireContext().isTablet().gridSize())
		} else {
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		}
	}

	private fun createInfiniteScrollListener(layoutManager: LinearLayoutManager):
			RecyclerView.OnScrollListener {
		return object : InfiniteScrollListener(layoutManager, viewModel.pageSize) {
			override fun isLoading(): Boolean = viewModel.isLoadingMoreMovies

			override fun isLastPage(): Boolean = viewModel.isLastPage

			override fun loadMoreMovies() = requestMoreMovies()
		}
	}

	private fun requestMoreMovies() {
		viewModel.onEvent(CategorisedMoviesEvent.RequestMoreMovies)
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

}