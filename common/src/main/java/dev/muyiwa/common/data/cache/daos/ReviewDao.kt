package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.review.*

@Dao
interface ReviewDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertReview(review: CacheReview)
}