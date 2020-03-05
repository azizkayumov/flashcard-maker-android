package com.piapps.flashcardpro.features.main.adapter.cells

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.horizontalLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appTheme
import com.piapps.flashcardpro.core.extension.getLocalizedString

/**
 * Created by abduaziz on 2019-09-26 at 12:34.
 */

class NavMenuUI {

    companion object {
        val dividerId = 10
        val ivId = 11
        val tvId = 12
    }

    fun createView(ctx: Context) = with(ctx) {
        horizontalLayout {
            lparams(matchParent, dip(48))
            setRippleEffect()

            imageView {
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