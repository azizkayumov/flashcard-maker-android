package com.piapps.flashcardpro.features.main.adapter.cells

import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.*
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-09-26 at 12:34.
 */

class NavMenuUI : AnkoComponent<ViewGroup> {

    companion object {
        val dividerId = 10
        val ivId = 11
        val tvId = 12
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            lparams(matchParent, dip(48))
            setRippleEffect()

            appCompatImageView {
                id = ivId
                layoutParams = LinearLayout.LayoutParams(dip(48), dip(48))
                padding = dip(12)
                setIconColor(ctx, ctx.appTheme().colorIconActive)
            }

            textView {
                id = tvId
                layoutParams = LinearLayout.LayoutParams(matchParent, dip(48))
                gravity = Gravity.CENTER_VERTICAL
                rightPadding = dip(16)
                text = ctx.getLocalizedString(R.string.app_internal_error)
                textColorResource = ctx.appTheme().colorPrimaryText
                textSize = 14F
                ellipsize(1)
            }
        }
    }
}