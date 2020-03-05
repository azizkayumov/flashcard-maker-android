package com.piapps.flashcardpro.features.main.adapter.cells

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.horizontalLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.core.extension.appTheme
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.features.main.adapter.cells.NavMenuUI.Companion.tvId

/**
 * Created by abduaziz on 2019-09-26 at 12:34.
 */

class NavGroupUI {

    fun createView(ctx: Context) = with(ctx) {
        horizontalLayout {
            lparams(matchParent, dip(36))

            textView {
                id = tvId
                layoutParams = LinearLayout.LayoutParams(matchParent, dip(36))
                gravity = Gravity.CENTER_VERTICAL
                horizontalPadding = dip(16)
                text = ctx.getLocalizedString(R.string.app_name)
                textColorResource = ctx.appTheme().colorSecondaryText
                textSize = 14F
                ellipsize(1)
            }
        }
    }
}