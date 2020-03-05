package com.piapps.flashcardpro.features.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import com.piapps.flashcardpro.core.extension.toast
import com.piapps.flashcardpro.core.platform.BaseFragment

/**
 * Created by abduaziz on 2019-10-27 at 00:11.
 */

class SettingsFragment : BaseFragment(), SettingsView {

    override fun create() {
        enterAnimation = ENTER_FROM_RIGHT
        exitAnimation = EXIT_TO_RIGHT
        canSwipe = true
        super.create()
    }

    lateinit var presenter: SettingsPresenter

    override fun createView(context: Context) = UI()

    override fun viewCreated(view: View?, args: Bundle?) {
        super.viewCreated(view, args)
        presenter = SettingsPresenter(this)
        appComponent.inject(presenter)
    }

    override fun showToast(res: Int) {
        toast(res)
    }

    override fun removed() {
        presenter.clear()
        super.removed()
    }

}