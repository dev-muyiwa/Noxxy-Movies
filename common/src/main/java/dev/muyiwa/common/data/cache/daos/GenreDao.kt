package dev.muyiwa.common.data.cache.daos

import androidx.room.*
import dev.muyiwa.common.data.cache.entities.test_run.cross_ref.*
import dev.muyiwa.common.data.cache.entities.test_run.genre.*

@Dao
interface GenreDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertGenre(genre: CachedGenre)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertMovieGenreCrossRef(ref: MovieGenreCrossRef)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertGenres(genres: List<CachedGenre>)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertMovieGenreCrossRefs(refs: List<MovieGenreCrossRef>)
}