package com.lalit.devtrackr.data.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dsa_problems")
data class DsaProblem (
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
    val title: String,
    val platform: String,
    val difficulty: String,
    val timecomplexity: String,
    val datesolved: Long
)