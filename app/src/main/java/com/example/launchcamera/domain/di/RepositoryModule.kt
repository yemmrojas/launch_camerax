package com.example.launchcamera.domain.di

import com.example.launchcamera.domain.repository.OcrRepository
import com.example.launchcamera.data.datasource.OcrRepositoryImpl
import com.example.launchcamera.data.datasource.LoginLocalDataSource
import com.example.launchcamera.domain.repository.UserRepository
import com.example.launchcamera.data.datasource.UserRepositoryImpl
import com.example.launchcamera.domain.repository.LoginLocalDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOcrRepository(
        ocrRepositoryImpl: OcrRepositoryImpl
    ): OcrRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindSaveLoginRepository(
        loginLocalDataSource: LoginLocalDataSource
    ) : LoginLocalDataRepository
}