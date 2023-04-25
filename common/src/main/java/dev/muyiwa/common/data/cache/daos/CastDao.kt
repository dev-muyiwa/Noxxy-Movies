package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.cast.*

@Dao
interface CastDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertCast(cast: CachedCast)
}