package com.piapps.flashcardpro.features.editor.adapter.cells

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.appCompatImageView
import com.piapps.flashcardpro.core.extension.circleImageView
import com.piapps.flashcardpro.core.extension.setRippleEffectBorderless
import org.jetbrains.anko.*

/**
 * Created by abduaziz on 2019-10-06 at 20:02.
 */

class ColorItemUI : AnkoComponent<ViewGroup> {

    companion object {
        val ivId = 19
        val ivSelectedId = 20
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        frameLayout {
            lparams(matchParent, wrapContent)
            setRippleEffectBorderless()

            circleImageView {
                id = ivId
                layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                    gravity = Gravity.CENTER
                    margin = dip(8)
                }
                imageResource = R.color.c1
            }

            view {
                layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                    gravity = Gravity.CENTER
                    margin = dip(8)
                }
                backgroundResource = R.drawable.color_divider
            }

            appCompatImageView {
                id = ivSelectedId
                layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                    gravity = Gravity.CENTER
                    margin = dip(8)
                }
                padding = dip(12)
                imageResource = R.drawable.ic_check
            }
        }
    }

}