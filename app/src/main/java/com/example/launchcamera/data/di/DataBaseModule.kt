package com.example.launchcamera.data.di

import android.content.Context
import androidx.room.Room
import com.example.launchcamera.data.datasource.dao.UserDao
import com.example.launchcamera.data.datasource.database.AppUserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesUserDatabase(
        @ApplicationContext context: Context
    ): AppUserDatabase =
        Room.databaseBuilder(
            context = context,
            klass = AppUserDatabase::class.java,
            name = "app_user.bd"
        ).build()

    @Provides
    @Singleton
    fun providesUserDao(
        appUserDatabase: AppUserDatabase
    ): UserDao = appUserDatabase.userDataDao()
}