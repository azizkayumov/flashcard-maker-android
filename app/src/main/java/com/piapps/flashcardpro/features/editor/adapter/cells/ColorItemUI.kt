package com.piapps.flashcardpro.features.editor.adapter.cells

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import com.kent.layouts.*
import com.kent.layouts.viewgroup.frameLayout
import com.kent.layouts.viewgroup.lparams
import com.piapps.flashcardpro.R
import com.piapps.flashcardpro.core.extension.circleImageView

/**
 * Created by abduaziz on 2019-10-06 at 20:02.
 */

class ColorItemUI {

    companion object {
        val ivId = 19
        val ivSelectedId = 20
    }

    fun createView(ctx: Context) = with(ctx) {
        frameLayout {
            lparams(matchParent, wrapContent)
            setRippleEffectBorderless()

            circleImageView {
                id = ivId
                layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                    gravity = Gravity.CENTER
                    margin = dip(8)
                }
            }

            view {
                layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                    gravity = Gravity.CENTER
                    margin = dip(8)
                }
                setBackgroundResource(R.drawable.color_divider)
            }

            imageView {
                id = ivSelectedId
                layoutParams = FrameLayout.LayoutParams(dip(48), dip(48)).apply {
                    gravity = Gravity.CENTER
                    margin = dip(8)
                }
                padding = dip(12)
                setImageResource(R.drawable.ic_check)
            }
        }
    }

}