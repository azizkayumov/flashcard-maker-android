package com.piapps.flashcard.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.piapps.flashcard.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.*

/**
 * Created by abduaziz on 8/8/18.
 */

class AboutActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val aboutPage = AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.art)
                .setDescription(getString(R.string.description))
                .addItem(Element().setTitle("Version ${packageManager.getPackageInfo(packageName,0).versionName}"))
                .addGroup("Connect with us")
                .addEmail("kayumovabduaziz@gmail.com")
                .addWebsite("http://inha.uz")
                .addFacebook("abduaziz.kayumov")
                .addYoutube("UCnQ3vq3LNsxrYGlGdziTrhA")
                .addPlayStore("com.piapps.flashcard")
                .addItem(getCopyRightsElement())
                .create()
        setContentView(aboutPage)
    }

    internal fun getCopyRightsElement(): Element {
        val copyRightsElement = Element()
        val copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR))
        copyRightsElement.setTitle(copyrights)
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color)
        copyRightsElement.iconNightTint = android.R.color.white
        copyRightsElement.gravity = Gravity.CENTER
        copyRightsElement.onClickListener = View.OnClickListener { Toast.makeText(this@AboutActivity, copyrights, Toast.LENGTH_SHORT).show() }
        return copyRightsElement
    }

}