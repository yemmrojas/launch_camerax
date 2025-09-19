package com.example.launchcamera.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.launchcamera.data.datasource.dao.UserDao
import com.example.launchcamera.data.model.UserDataEntity

@Database(
    entities = [UserDataEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppUserDatabase: RoomDatabase() {
    abstract fun userDataDao(): UserDao
}