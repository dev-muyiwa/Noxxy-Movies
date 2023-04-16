package dev.muyiwa.common.utils

import android.content.*
import android.net.*
import android.os.Build.VERSION.*
import android.widget.*
import androidx.navigation.*
import coil.*
import coil.decode.*
import dev.muyiwa.common.R
import dev.muyiwa.logging.*
import kotlinx.coroutines.*
import java.net.*
import java.text.*
import java.util.*

/** This extension function returns true if the device is a tablet otherwise false. */
fun Context.isTablet(): Boolean = resources.getBoolean(R.bool.is_tablet)

fun Boolean.gridSize(): Int = if (this) 3 else 2

fun Context.spanCount(): Int {
	return if (isTablet()) 3 else 2
}

/** This extension function shortens the loading of images into an ImageView. */
fun ImageView.loadImage(url: String) {
	val imageLoader = ImageLoader.Builder(context)
		.components {
			if (SDK_INT >= 28) {
				add(ImageDecoderDecoder.Factory())
			} else {
				add(GifDecoder.Factory())
			}
		}
		.build()
//	Coil.setImageLoader(imageLoader)
	this.load(url) {
		crossfade(true)
		placeholder(dev.muyiwa.common.R.drawable.loading)
		error(dev.muyiwa.common.R.drawable.error)
		// Change these later.
	}
//	imageLoader.shutdown()
}

/** This extension function creates a DeepLinkRequest that is used to navigate between feature modules. */
fun NavController.navigateToDetailsScreen(id: Int) {
	val encodedId = URLEncoder.encode("$id", "utf-8")
	val request = NavDeepLinkRequest.Builder
		.fromUri(Uri.parse("noxxy://details/$encodedId"))
		.build()
	navigate(request)
}

fun String.toDate(): String {
	if (this.isEmpty()) return ""
	val (year, month, day) = this.split("-")
	val calendar = Calendar.getInstance()
	val monthFormat = SimpleDateFormat("MM", Locale.getDefault())
	calendar[Calendar.MONTH] = month.toInt() - 1
	return "$day " + monthFormat.format(calendar.time) + ", $year"
}

/** This function converts the whitespaces in a search query to %20 to make it usable by the API. */
//fun String.toSearchQuery(): String {
//	return this.split(" ")
//		.joinToString("%20")
//}

fun Boolean.toLayoutInt(): Int {
	return if (this) 1 else 0
}

/** This function returns the icon for a grid view if true else the icon for a list view. */
fun Boolean.getIcon(): Int {
	return if (this) R.drawable.ic_round_grid_view_24 else R.drawable.ic_round_view_list_24
}

fun String?.asUnknown(): String {
	return this?.ifEmpty { "unknown" }.orEmpty()
}

/** This extension function displays a Toast to the screen. */
fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT){
	Toast.makeText(this, message, length).show()
}

inline fun CoroutineScope.createExceptionHandler(
	message: String,
	crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
	Logger.e(message, throwable)
	throwable.printStackTrace()
	launch {
		action(throwable)
	}
}

fun String.createExceptionHandler(
	scope: CoroutineScope,
	block: () -> Unit
): CoroutineExceptionHandler {
	return scope.createExceptionHandler(this) {
		block()
	}
}

//inline fun CoroutineScope.customLaunch(
//	block: () -> Unit,
//	message: String
//) {
//	this.launch(message.createExceptionHandler(this)){
//		blo
//	}
//}

