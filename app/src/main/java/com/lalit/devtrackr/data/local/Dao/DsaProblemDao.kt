package com.lalit.devtrackr.data.local.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lalit.devtrackr.data.local.Entity.DsaProblem
import kotlinx.coroutines.flow.Flow

@Dao
interface DsaProblemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProblem(problem: DsaProblem)

    @Delete
    suspend fun deleteProblem(problem: DsaProblem)

    @Query("SELECT * FROM dsa_problems ORDER BY datesolved DESC")
    fun getAllProblems(): Flow<List<DsaProblem>>

    @Query("SELECT COUNT(*) FROM dsa_problems")
    fun getProblemCount(): Flow<Int>
}