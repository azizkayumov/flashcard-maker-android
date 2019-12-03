package com.piapps.flashcardpro.core.di

import android.content.Context
import com.piapps.flashcardpro.AndroidApplication
import com.piapps.flashcardpro.core.db.DatabaseRepository
import com.piapps.flashcardpro.core.db.tables.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

/**
 * Created by abduaziz on 2019-09-22 at 00:05.
 */

@Module
class ApplicationModule(private val androidApplication: AndroidApplication) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = androidApplication

    @Provides
    @Singleton
    fun provideDatabase(): BoxStore {
        return MyObjectBox.builder().androidContext(androidApplication).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(source: DatabaseRepository.Database): DatabaseRepository {
        return source
    }
}