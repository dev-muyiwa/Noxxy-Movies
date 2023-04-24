package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.detail.*

@Dao
interface DetailDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertDetail(detail: CachedDetail): Int
}