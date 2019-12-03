package com.piapps.flashcardpro

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.piapps.flashcardpro.core.di.ApplicationComponent
import com.piapps.flashcardpro.core.di.ApplicationModule
import com.piapps.flashcardpro.core.di.DaggerApplicationComponent

/**
 * Created by abduaziz on 2019-09-21 at 23:56.
 */

class AndroidApplication : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}