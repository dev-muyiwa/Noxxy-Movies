package dev.muyiwa.noxxy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.*
import androidx.navigation.*
import androidx.navigation.fragment.*
import androidx.navigation.ui.*
import dagger.hilt.android.AndroidEntryPoint
import dev.muyiwa.noxxy.databinding.ActivityMainBinding
import dev.muyiwa.bookmarks.R as bookmarksR
import dev.muyiwa.home.R as homeR
import dev.muyiwa.search.R as searchR

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private lateinit var navController: NavController
	private val appBarConfiguration by lazy {
		AppBarConfiguration(setOf(homeR.id.homeFragment, searchR.id.searchFragment, bookmarksR.id.bookmarksFragment))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		navController = navHostFragment.navController
		setSupportActionBar(binding.toolbar)
		setupActionBarWithNavController(navController, appBarConfiguration)
		binding.bottomNavView.setupWithNavController(navController)

		navController.addOnDestinationChangedListener{_, nd, _ ->
			if (nd.id == dev.muyiwa.noxxy.details.R.id.movieDetailsFragment) {
				binding.toolbar.isVisible = false
				binding.appbarLayout.isVisible = false
				binding.bottomNavView.isVisible = false
			} else {
				binding.toolbar.isVisible = true
				binding.appbarLayout.isVisible = true
				binding.bottomNavView.isVisible = true

			}
			if (nd.id == dev.muyiwa.noxxy.details.R.id.videosFragment){
				binding.bottomNavView.isVisible = false
			}
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}
}