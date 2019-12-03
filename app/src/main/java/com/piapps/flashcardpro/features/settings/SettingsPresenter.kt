package com.piapps.flashcardpro.features.settings

import com.piapps.flashcardpro.core.platform.BasePresenter
import com.piapps.flashcardpro.core.settings.Settings
import javax.inject.Inject

/**
 * Created by abduaziz on 2019-10-27 at 00:17.
 */

class SettingsPresenter(var view: SettingsView?) : BasePresenter(view) {

    @Inject
    lateinit var settings: Settings

}