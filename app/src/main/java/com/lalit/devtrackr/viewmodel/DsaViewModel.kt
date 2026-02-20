package com.lalit.devtrackr.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lalit.devtrackr.data.local.Entity.DsaProblem
import com.lalit.devtrackr.data.local.database.DevTrackrDatabase
import com.lalit.devtrackr.repository.DsaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DsaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DsaRepository

    val allProblems: Flow<List<DsaProblem>>
    val problemCount: Flow<Int>

    init {
        val dao = DevTrackrDatabase
            .getDatabase(application)
            .dsaProblemDao()

        repository = DsaRepository(dao)

        allProblems = repository.allProblems
        problemCount = repository.problemCount
    }

    fun insert(problem: DsaProblem) = viewModelScope.launch {
        repository.insert(problem)
    }

    fun delete(problem: DsaProblem) = viewModelScope.launch {
        repository.delete(problem)
    }
}
