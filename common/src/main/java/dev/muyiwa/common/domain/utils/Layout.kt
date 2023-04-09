package dev.muyiwa.common.domain.utils

enum class Layout(val isGrid: Boolean, val viewType: Int) {
	LINEAR(false, 0),
	GRID(true, 1),
}