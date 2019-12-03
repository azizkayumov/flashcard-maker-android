package com.piapps.flashcardpro.features.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.core.platform.BaseFragment
import com.piapps.flashcardpro.features.MainActivity
import org.jetbrains.anko.AnkoContext

/**
 * Created by abduaziz on 2019-11-06 at 00:33.
 */

class AboutFragment : BaseFragment() {

    override fun create() {
        enterAnimation = ENTER_FROM_RIGHT
        exitAnimation = EXIT_TO_RIGHT
        canSwipe = true
        super.create()
    }

    override fun createView(context: Context): View? {
        return AboutUI().createView(AnkoContext.Companion.create(context, this))
    }

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
    }

    fun openEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, ctx.getLocalizedString(R.string.app_name))
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello")
        (activity as MainActivity).startActivity(Intent.createChooser(emailIntent, ctx.getLocalizedString(R.string.send_email)))
    }

    fun openLink(link: String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        (activity as MainActivity).startActivity(browserIntent)
    }

}