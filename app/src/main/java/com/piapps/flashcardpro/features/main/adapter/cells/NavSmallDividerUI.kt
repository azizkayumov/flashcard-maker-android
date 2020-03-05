package com.piapps.flashcardpro.features.main.adapter.cells

import android.content.Context
import android.widget.LinearLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.core.extension.appTheme

/**
 * Created by abduaziz on 2019-09-26 at 12:34.
 */

class NavSmallDividerUI {

    fun createView(ctx: Context) = with(ctx) {
        verticalLayout {
            layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
/*
            view {
                layoutParams = LinearLayout.LayoutParams(matchParent, 1)
                backgroundColorResource = ctx.appTheme().colorAccent
            }*/

            view {
                layoutParams = LinearLayout.LayoutParams(matchParent, dip(1))
                backgroundColorResource = ctx.appTheme().colorDividerLight
            }

/*            view {
                layoutParams = LinearLayout.LayoutParams(matchParent, 1)
                backgroundColorResource = ctx.appTheme().colorAccent
            }*/
        }
    }
}