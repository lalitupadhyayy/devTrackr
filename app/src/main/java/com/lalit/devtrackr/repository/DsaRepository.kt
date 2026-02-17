package com.lalit.devtrackr.repository

import com.lalit.devtrackr.data.local.Dao.DsaProblemDao
import com.lalit.devtrackr.data.local.Entity.DsaProblem
import kotlinx.coroutines.flow.Flow

class DsaRepository(private val dao:DsaProblemDao) {

    val allProblems: Flow<List<DsaProblem>> = dao.getAllProblems()

    val problemCount : Flow<Int> = dao.getProblemCount()

    suspend fun insert(problem: DsaProblem) {
        dao.insertProblem(problem)
    }

    suspend fun delete(problem: DsaProblem) {
        dao.deleteProblem(problem)
    }

}