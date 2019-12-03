package com.piapps.flashcardpro.core.platform.component.bottom

import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.ellipsize
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class BottomMenuUI : AnkoComponent<BottomMenuFragment> {

    override fun createView(ui: AnkoContext<BottomMenuFragment>): View = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)
            isClickable = true

            verticalLayout {
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                    gravity = Gravity.BOTTOM
                }
                backgroundResource = owner.theme.white

                ui.owner.tvTitle = textView {
                    layoutParams = LinearLayout.LayoutParams(matchParent, dip(40)).apply {
                        leftPadding = dip(16)
                    }
                    gravity = Gravity.CENTER_VERTICAL
                    textColorResource = owner.theme.colorSecondaryText
                    singleLine = true
                    maxLines = 1
                    ellipsize(1)
                }

                ui.owner.recyclerView = recyclerView {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }
}