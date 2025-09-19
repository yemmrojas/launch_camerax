package com.example.launchcamera.data.di

import com.example.launchcamera.domain.repository.OcrDataRepository
import com.example.launchcamera.data.datasource.MlKitOcrDatasource
import com.example.launchcamera.data.parser.ExtractedDataValidatorImpl
import com.example.launchcamera.data.parser.IdentityCardExtractor
import com.example.launchcamera.data.parser.MLKitTextParserImpl
import com.example.launchcamera.data.parser.TextUtilImpl
import com.example.launchcamera.data.parser.UserDataUpdaterImpl
import com.example.launchcamera.domain.parser.DocumentFieldExtractor
import com.example.launchcamera.domain.parser.ExtractedDataValidator
import com.example.launchcamera.domain.parser.MLKitTextParser
import com.example.launchcamera.domain.parser.TextUtils
import com.example.launchcamera.domain.parser.UserDataUpdater
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindOcrDatasource(
        mlKitOcrDatasource: MlKitOcrDatasource
    ) : OcrDataRepository

    @Binds
    @Singleton
    abstract fun bindTextUtil(
        textUtilsImpl: TextUtilImpl
    ): TextUtils

    @Binds
    @Singleton
    abstract fun bindExtractedDataValidator(
        extractedDataValidatorImpl: ExtractedDataValidatorImpl
    ): ExtractedDataValidator

    @Binds
    @Singleton
    abstract fun bindIdentityCardExtractor(
        identityCardExtractor: IdentityCardExtractor
    ): DocumentFieldExtractor

    @Binds
    @Singleton
    abstract fun bindUserDataUpdate(
        userDataUpdaterImpl: UserDataUpdaterImpl
    ): UserDataUpdater

    @Binds
    @Singleton
    abstract fun bindMlKitParser(
        mlKitTextParser: MLKitTextParserImpl
    ): MLKitTextParser
}