package dev.muyiwa.common.utils

import android.content.*
import android.net.*
import android.os.Build.VERSION.*
import android.widget.*
import androidx.navigation.*
import coil.*
import coil.decode.*
import dev.muyiwa.common.R
import dev.muyiwa.common.data.api.model.categorised_movie.*
import dev.muyiwa.common.data.api.model.details.*
import dev.muyiwa.common.data.api.utils.*
import dev.muyiwa.common.data.cache.entities.*
import dev.muyiwa.common.domain.model.category.*
import dev.muyiwa.common.domain.model.detail.*
import dev.muyiwa.common.domain.utils.*
import dev.muyiwa.common.presentation.model.*
import dev.muyiwa.logging.*
import kotlinx.coroutines.*
import java.net.*
import java.text.SimpleDateFormat
import java.util.*

fun Context.isTablet(): Boolean = resources.getBoolean(R.bool.is_tablet)

fun Boolean.gridSize(): Int = if (this) 3 else 2

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

fun Boolean.toLayoutInt(): Int {
	return if (this) 1 else 0
}

fun Boolean.getIcon(): Int {
	return if (this) R.drawable.ic_round_grid_view_24 else R.drawable.ic_round_view_list_24
}

fun String?.asUnknown(): String {
	return this?.ifEmpty { "unknown" }.orEmpty()
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

