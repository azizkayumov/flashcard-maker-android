package com.piapps.flashcardpro.features

import android.os.Bundle
import com.piapps.flashcardpro.AndroidApplication
import com.piapps.flashcardpro.core.di.ApplicationComponent
import com.piapps.flashcardpro.core.extension.appTheme
import com.piapps.flashcardpro.core.platform.BaseActivity
import com.piapps.flashcardpro.core.settings.Settings
import com.piapps.flashcardpro.features.editor.SetFragment
import com.piapps.flashcardpro.features.main.MainFragment
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-09-22 at 00:34.
 */

class MainActivity : BaseActivity() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as AndroidApplication).appComponent
    }

    @Inject
    lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        changeStatusBarColor(appTheme().colorPrimaryDark)

        if (settings.userLoggedIn())
            openFragment(MainFragment())
        else
            openFragment(MainFragment())
    }

}