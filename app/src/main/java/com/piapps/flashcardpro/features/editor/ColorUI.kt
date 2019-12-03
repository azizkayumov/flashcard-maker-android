package com.piapps.flashcardpro.features.editor

import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.getLocalizedString
import com.piapps.flashcardpro.features.editor.adapter.ColorAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by abduaziz on 2019-10-06 at 19:51.
 */

class ColorUI : AnkoComponent<ColorFragment> {
    override fun createView(ui: AnkoContext<ColorFragment>) = with(ui) {
        frameLayout {
            lparams(matchParent, matchParent)

            verticalLayout {
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                    gravity = Gravity.BOTTOM
                }
                backgroundColorResource = owner.theme.colorPrimary

                ui.owner.tv = textView {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                    horizontalPadding = dip(16)
                    topPadding = dip(16)
                    text = ctx.getLocalizedString(R.string.color)
                    textSize = 14F
                    textColorResource = owner.theme.colorPrimaryText
                }

                ui.owner.rv = recyclerView {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                    padding = dip(8)
                    layoutManager = GridLayoutManager(ctx, 5)
                    itemAnimator = null
                    clipToPadding = false
                    adapter = ui.owner.adapter
                }
            }
        }
    }
}