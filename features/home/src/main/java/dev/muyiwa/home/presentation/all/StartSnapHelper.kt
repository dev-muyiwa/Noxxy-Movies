package dev.muyiwa.home.presentation.all

import android.view.*
import androidx.recyclerview.widget.*

class StartSnapHelper : SnapHelper() {

	override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray {
		val out = IntArray(2)
		layoutManager as LinearLayoutManager

		val targetPosition = layoutManager.getPosition(targetView)

		val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

		if (targetPosition <= firstVisibleItemPosition) {
			out[0] = 0
		} else {
			val targetViewLeft = layoutManager.getDecoratedLeft(targetView)
			out[0] = targetViewLeft
		}

		out[1] = 0
		return out
	}

	override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
		return layoutManager.getChildAt(0)
	}

	override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int): Int {
		return 0
	}
}

