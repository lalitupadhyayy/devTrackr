package com.lalit.devtrackr.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lalit.devtrackr.data.local.Dao.DsaProblemDao
import com.lalit.devtrackr.data.local.Entity.DsaProblem

@Database(
    entities = [DsaProblem::class],
    version = 1,
    exportSchema = false
)
abstract class DevTrackrDatabase: RoomDatabase() {

    abstract fun dsaProblemDao(): DsaProblemDao

    companion object{
        @Volatile
        private var INSTANCE: DevTrackrDatabase?= null

        fun getDatabase(context: Context): DevTrackrDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DevTrackrDatabase::class.java,
                    "devtrackr_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}