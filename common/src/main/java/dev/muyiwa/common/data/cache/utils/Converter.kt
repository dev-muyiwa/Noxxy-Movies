package dev.muyiwa.common.data.cache.utils

import androidx.room.*
import dev.muyiwa.common.domain.utils.*


class Converter {

	@TypeConverter
	fun fromString(ids: String): List<String> {
		return ids.substring(1, ids.length - 1)
			.split(", ")
			.map { it.toString() }
	}

	@TypeConverter
	fun toString(list: List<String>): String {
		return "$list"
	}

	@TypeConverter
	fun fromCategory(category: Category): String {
		return category.name
	}

	@TypeConverter
	fun toCategory(name: String): Category {
		return Category.valueOf(name)
	}
}