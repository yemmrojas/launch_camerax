package com.example.launchcamera.screen.di

import com.example.launchcamera.screen.camera.provider.CameraScannerContentProvider
import com.example.launchcamera.screen.camera.provider.UserDataValidator
import com.example.launchcamera.screen.camera.provider.impl.CameraScannerContentProviderImpl
import com.example.launchcamera.screen.camera.provider.impl.UserDataValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CameraScreenModule {
    @Binds
    @Singleton
    abstract fun bindUserDataValidator(
        userDataValidatorImpl: UserDataValidatorImpl
    ): UserDataValidator

    @Binds
    @Singleton
    abstract fun bndCameraScannerContainerProvider(
        cameraScannerContentProviderImpl: CameraScannerContentProviderImpl
    ): CameraScannerContentProvider

}