package com.piapps.flashcardpro.core.platform.component.bottom

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.kent.layouts.viewgroup.verticalLayout
import com.piapps.flashcardpro.core.extension.ellipsize

class BottomMenuUI {
    fun createView(ui: BottomMenuFragment): View = with(ui.ctx) {
        frameLayout {
            lparams(matchParent, matchParent)
            isClickable = true

            verticalLayout {
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                    gravity = Gravity.BOTTOM
                }
                backgroundColorResource = ui.theme.white

                ui.tvTitle = textView {
                    layoutParams = LinearLayout.LayoutParams(matchParent, dip(40)).apply {
                        leftPadding = dip(16)
                    }
                    gravity = Gravity.CENTER_VERTICAL
                    textColorResource = ui.theme.colorSecondaryText
                    singleLine = true
                    maxLines = 1
                    ellipsize(1)
                }

                ui.recyclerView = recyclerView {
                    layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                    layoutManager = LinearLayoutManager(ui.ctx)
                }
            }
        }
    }
}