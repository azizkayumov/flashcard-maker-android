package com.piapps.flashcardpro.core.platform.component.bottom

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.piapps.flashcardpro.core.extension.appCompatImageView
import com.piapps.flashcardpro.core.extension.appTheme
import com.piapps.flashcardpro.core.extension.setIconColor
import com.piapps.flashcardpro.core.extension.setRippleEffect
import org.jetbrains.anko.*

class BottomMenuItemUI : AnkoComponent<ViewGroup> {
    companion object {
        var ivId = 32
        var tvId = 980
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(matchParent, wrapContent)
            setRippleEffect()
            appCompatImageView {
                id = ivId
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56)).apply {
                    topPadding = dip(16)
                    leftPadding = dip(16)
                    bottomPadding = dip(16)
                }
                setIconColor(ctx, ctx.appTheme().colorIconActive)
            }
            textView {
                layoutParams = LinearLayout.LayoutParams(matchParent, dip(56)).apply {
                    gravity = Gravity.CENTER_VERTICAL
                    leftPadding = dip(16)
                }
                gravity = Gravity.CENTER_VERTICAL
                textColorResource = ctx.appTheme().colorPrimaryText
                textSize = 16f
                id = tvId
            }
        }
    }
}