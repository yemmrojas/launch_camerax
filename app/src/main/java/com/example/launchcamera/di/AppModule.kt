package com.example.launchcamera.di

import com.example.launchcamera.data.datasource.OcrDatasource
import com.example.launchcamera.data.impl.MlKitOcrDatasource
import com.example.launchcamera.data.parser.MLKitTextParserImpl
import com.example.launchcamera.domain.parser.MLKitTextParser
import com.example.launchcamera.repository.OcrRepository
import com.example.launchcamera.repository.OcrRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindOcrDatasource(
        mlKitOcrDatasource: MlKitOcrDatasource
    ) : OcrDatasource

    @Binds
    @Singleton
    abstract fun bindMlKitParser(
        mlKitTextParser: MLKitTextParserImpl
    ): MLKitTextParser

    @Binds
    @Singleton
    abstract fun bindOcrRepository(
        ocrRepositoryImpl: OcrRepositoryImpl
    ): OcrRepository
}