package com.lalit.devtrackr.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lalit.devtrackr.data.local.Dao.DsaProblemDao
import com.lalit.devtrackr.data.local.Entity.DsaProblem

@Database(
    entities = [DsaProblem::class],
    version = 1,
    exportSchema = false
)
abstract class DevTrackrDatabase: RoomDatabase() {

    abstract fun DsaProblemDao(): DsaProblemDao
}