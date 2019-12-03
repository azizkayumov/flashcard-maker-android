package com.piapps.flashcardpro.features.files.controller

import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.piapps.flashcardpro.core.extension.appCompatImageView
import com.piapps.flashcardpro.core.extension.appTheme
import com.piapps.flashcardpro.core.extension.setRippleEffect
import org.jetbrains.anko.*

class FilesItemUI : AnkoComponent<ViewGroup> {
    companion object {
        var ivIconId = 1
        var tvNameId = 2
        var tvDescriptionId = 3
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(matchParent, wrapContent).apply {
                rightPadding = dip(12)
            }
            backgroundResource = ctx.appTheme().colorBackground
            setRippleEffect()

            appCompatImageView {
                layoutParams = LinearLayout.LayoutParams(dip(56), dip(56)).apply {
                    padding = dip(12)
                }
                id = ivIconId
                //setIconColor(ctx, ctx.appTheme().colorAccent)
            }

            verticalLayout {
                layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                    gravity = Gravity.CENTER_VERTICAL
                }
                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
                    textColorResource = ctx.appTheme().colorPrimaryText
                    id = tvNameId
                    maxLines = 1
                    ellipsize = TextUtils.TruncateAt.END
                }
                textView {
                    layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
                    textColorResource = ctx.appTheme().colorSecondaryText
                    id = tvDescriptionId
                    maxLines = 1
                    ellipsize = TextUtils.TruncateAt.END
                }
            }
        }
    }
}