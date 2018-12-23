package com.piapps.flashcardpro.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.piapps.flashcardpro.R
import kotlinx.android.synthetic.main.activity_about.*


/**
 * Created by abduaziz on 8/8/18.
 */

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        tvVersion.text = "Version ${packageManager.getPackageInfo(packageName, 0).versionName}"

        llEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf("kayumovabduaziz@gmail.com"))
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Flashcards Maker")
            startActivity(emailIntent)
        }

        llGithub.setOnClickListener {
            val url = "https://github.com/AbduazizKayumov/Flashcard-Maker-Android"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        llPlayStore.setOnClickListener {
            val appPackageName = packageName
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
            }
        }
    }
}