package com.piapps.flashcardpro.features

import android.os.Bundle
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.piapps.flashcardpro.AndroidApplication
import com.piapps.flashcardpro.core.di.ApplicationComponent
import com.piapps.flashcardpro.core.extension.appTheme
import com.piapps.flashcardpro.core.extension.log
import com.piapps.flashcardpro.core.platform.BaseActivity
import com.piapps.flashcardpro.core.settings.Settings
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

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.RUSSIAN)
            .build()
        val englishTranslator = Translation.getClient(options)
        englishTranslator.downloadModelIfNeeded().addOnFailureListener {
            log("Download Failure = ${it.localizedMessage}")
            englishTranslator.close()
        }.addOnSuccessListener {
            log("Download Success")
            englishTranslator.translate("ephemeral").addOnFailureListener {
                log("Translation Failure = ${it.localizedMessage}")
                englishTranslator.close()
            }.addOnSuccessListener { translatedText ->
                log("Translation Success = $translatedText")
                englishTranslator.close()
            }
        }
    }
}