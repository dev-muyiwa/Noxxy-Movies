package dev.muyiwa.common.domain.model

data class Pagination(
	val currentPage: Int,
	val totalPage: Int
) {
	companion object {
		const val UNKNOWN_TOTAL = 1
		const val DEFAULT_PAGE_SIZE = 20
	}

	val canLoadMore: Boolean
		get() = totalPage == UNKNOWN_TOTAL || currentPage < totalPage
}
