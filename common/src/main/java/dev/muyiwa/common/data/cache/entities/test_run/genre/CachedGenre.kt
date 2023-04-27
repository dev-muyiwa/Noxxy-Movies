package dev.muyiwa.common.data.cache.entities.test_run.genre

import androidx.room.*
import dev.muyiwa.common.domain.utils.*

@Entity(tableName = CachedGenre.tableName)
/** Has a many-to-many relationship with the Movie table.*/
data class CachedGenre(
	@PrimaryKey val genreId: Int,
	val name: String = GenreType.fromApi(genreId)
) {
	companion object {
		const val tableName = "genre"
	}
}