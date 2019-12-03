package com.piapps.flashcardpro.features.main.adapter.cells

import android.view.ViewGroup
import android.widget.LinearLayout
import com.piapps.flashcardpro.core.extension.appTheme
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-09-26 at 12:34.
 */

class NavSmallDividerUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
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